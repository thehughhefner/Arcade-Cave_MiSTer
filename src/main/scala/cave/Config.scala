/*
 *   __   __     __  __     __         __
 *  /\ "-.\ \   /\ \/\ \   /\ \       /\ \
 *  \ \ \-.  \  \ \ \_\ \  \ \ \____  \ \ \____
 *   \ \_\\"\_\  \ \_____\  \ \_____\  \ \_____\
 *    \/_/ \/_/   \/_____/   \/_____/   \/_____/
 *   ______     ______       __     ______     ______     ______
 *  /\  __ \   /\  == \     /\ \   /\  ___\   /\  ___\   /\__  _\
 *  \ \ \/\ \  \ \  __<    _\_\ \  \ \  __\   \ \ \____  \/_/\ \/
 *   \ \_____\  \ \_____\ /\_____\  \ \_____\  \ \_____\    \ \_\
 *    \/_____/   \/_____/ \/_____/   \/_____/   \/_____/     \/_/
 *
 * https://joshbassett.info
 * https://twitter.com/nullobject
 * https://github.com/nullobject
 *
 * Copyright (c) 2022 Josh Bassett
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cave

import axon.dma.DMAConfig
import axon.gfx.VideoTimingConfig
import axon.mem.{DDRConfig, SDRAMConfig}
import axon.snd.YMZ280BConfig
import chisel3.util.log2Ceil

object Config {
  /**
   * System clock frequency (Hz)
   *
   * This is the clock domain used by the memory, GPU, etc.
   */
  val CLOCK_FREQ = 96000000D

  /**
   * CPU clock frequency (Hz)
   *
   * The CPU clock as measured on the PCB is 16MHz, but the M68k CPU module requires a clock that is
   * twice the desired clock speed.
   */
  val CPU_CLOCK_FREQ = 32000000D
  /** CPU clock period (ns) */
  val CPU_CLOCK_PERIOD = 1 / CPU_CLOCK_FREQ * 1000000000

  /** Video clock frequency (Hz) */
  val VIDEO_CLOCK_FREQ = 28000000D
  /** Video clock divider */
  val VIDEO_CLOCK_DIV = 4

  /** The screen width in pixels */
  val SCREEN_WIDTH = 320
  /** The screen height in pixels */
  val SCREEN_HEIGHT = 240

  /** The width of the pulse generated when a player presses the coin button */
  val PLAYER_COIN_PULSE_WIDTH = (100000000 / CPU_CLOCK_PERIOD).ceil.toInt // 100ms

  /** The byte offset of the IOCTL data stored in DDR. */
  val IOCTL_DOWNLOAD_DDR_OFFSET = 0x30000000
  /** The byte offset of the MiSTer system frame buffer in DDR. */
  val SYSTEM_FRAME_BUFFER_DDR_OFFSET = 0x24000000

  val PROG_ROM_ADDR_WIDTH = 20 // 1MB
  val PROG_ROM_DATA_WIDTH = 16

  val SOUND_ROM_ADDR_WIDTH = 25
  val SOUND_ROM_DATA_WIDTH = 8

  val EEPROM_ADDR_WIDTH = 7 // 256B
  val EEPROM_DATA_WIDTH = 16

  val TILE_ROM_ADDR_WIDTH = 32
  val TILE_ROM_DATA_WIDTH = 64

  val MAIN_RAM_ADDR_WIDTH = 15
  val MAIN_RAM_DATA_WIDTH = 16

  val SPRITE_RAM_ADDR_WIDTH = 15
  val SPRITE_RAM_DATA_WIDTH = 16
  val SPRITE_RAM_GPU_ADDR_WIDTH = 12
  val SPRITE_RAM_GPU_DATA_WIDTH = 128

  val LAYER_8x8_RAM_ADDR_WIDTH = 13
  val LAYER_8x8_RAM_GPU_ADDR_WIDTH = 12
  val LAYER_16x16_RAM_ADDR_WIDTH = 11
  val LAYER_16x16_RAM_GPU_ADDR_WIDTH = 10
  val LAYER_RAM_DATA_WIDTH = 16
  val LAYER_RAM_GPU_DATA_WIDTH = 32

  val LINE_RAM_ADDR_WIDTH = 10
  val LINE_RAM_DATA_WIDTH = 16
  val LINE_RAM_GPU_ADDR_WIDTH = 9
  val LINE_RAM_GPU_DATA_WIDTH = 32

  val PALETTE_RAM_ADDR_WIDTH = 15
  val PALETTE_RAM_DATA_WIDTH = 16
  val PALETTE_RAM_GPU_ADDR_WIDTH = 15
  val PALETTE_RAM_GPU_DATA_WIDTH = 16

  /** The number of tilemap layers */
  val LAYER_COUNT = 3
  val LAYER_REGS_COUNT = 3
  val LAYER_REGS_GPU_DATA_WIDTH = 48

  val VIDEO_REGS_COUNT = 8
  val VIDEO_REGS_GPU_DATA_WIDTH = 128

  /** The number of bits per color channel for the output RGB signal */
  val RGB_OUTPUT_BPP = 8

  /** The depth of the frame buffer in words */
  val FRAME_BUFFER_DEPTH = SCREEN_WIDTH * SCREEN_HEIGHT
  /** The width of the frame buffer X address */
  val FRAME_BUFFER_ADDR_WIDTH_X = log2Ceil(SCREEN_WIDTH)
  /** The width of the frame buffer Y address */
  val FRAME_BUFFER_ADDR_WIDTH_Y = log2Ceil(SCREEN_HEIGHT)

  /** The width of the output frame buffer address bus */
  val OUTPUT_FRAME_BUFFER_ADDR_WIDTH = log2Ceil(FRAME_BUFFER_DEPTH)
  /** The width of the output frame buffer data bus */
  val OUTPUT_FRAME_BUFFER_DATA_WIDTH = 15

  /** The depth of the layer FIFOs */
  val FIFO_DEPTH = 64

  /** The width of a priority value */
  val PRIO_WIDTH = 2
  /** The width of a color code value */
  val PALETTE_WIDTH = 6
  /** The width of palette index (256 colors) */
  val COLOR_WIDTH = 8
  /** The width of the layer index value */
  val LAYER_INDEX_WIDTH = 2
  /** The width of the layer scroll value */
  val LAYER_SCROLL_WIDTH = 9

  /** The width of the graphics format value */
  val GFX_FORMAT_WIDTH = 2
  /** Unknown graphics format */
  val GFX_FORMAT_UNKNOWN = 0
  /** 4BPP graphics format */
  val GFX_FORMAT_4BPP = 1
  /** 4BPP MSB graphics format */
  val GFX_FORMAT_4BPP_MSB = 2
  /** 8BPP graphics format */
  val GFX_FORMAT_8BPP = 3

  /** The maximum bit depth of a tile */
  val TILE_MAX_BPP = 8

  /** The size of a small tile in pixels */
  val SMALL_TILE_SIZE = 8
  /** The size of a large tile in pixels */
  val LARGE_TILE_SIZE = 16
  /** The size of a sprite tile in pixels */
  val SPRITE_TILE_SIZE = 16

  /** YMZ280B configuration */
  val ymzConfig = YMZ280BConfig(
    clockFreq = CPU_CLOCK_FREQ,
    sampleFreq = 88200
  )

  /** DDR configuration */
  val ddrConfig = DDRConfig()

  /** SDRAM configuration */
  val sdramConfig = SDRAMConfig(
    clockFreq = CLOCK_FREQ,
    colWidth = 10,
    burstLength = 4
  )

  /** Original (57Hz) video timing configuration */
  val originalVideoTimingConfig = VideoTimingConfig(
    clockFreq = VIDEO_CLOCK_FREQ,
    clockDiv = VIDEO_CLOCK_DIV,
    hFreq = 15625,
    vFreq = 57.44,
    hDisplay = SCREEN_WIDTH,
    vDisplay = SCREEN_HEIGHT,
    hFrontPorch = 36,
    vFrontPorch = 12,
    hRetrace = 20,
    vRetrace = 2,
  )

  /** Compatibility (60Hz) video timing configuration */
  val compatibilityVideoTimingConfig = VideoTimingConfig(
    clockFreq = VIDEO_CLOCK_FREQ,
    clockDiv = VIDEO_CLOCK_DIV,
    hFreq = 15625,
    vFreq = 60,
    hDisplay = SCREEN_WIDTH,
    vDisplay = SCREEN_HEIGHT,
    hFrontPorch = 30,
    vFrontPorch = 12,
    hRetrace = 20,
    vRetrace = 2,
  )
}
