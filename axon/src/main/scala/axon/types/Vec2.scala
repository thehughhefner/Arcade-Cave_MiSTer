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

package axon.types

import chisel3._
import chisel3.internal.firrtl.Width

/**
 * Represents a 2D vector.
 *
 * @param gen The type of the elements.
 */
sealed class Vec2[T <: Bits with Num[T]] private[axon](private val gen: T) extends Bundle {
  /** Horizontal position */
  val x: T = gen.cloneType
  /** Vertical position */
  val y: T = gen.cloneType

  /** Addition operator. */
  def +(that: Vec2[T]): Vec2[T] = Vec2(this.x + that.x, this.y + that.y)

  /** Subtraction operator. */
  def -(that: Vec2[T]): Vec2[T] = Vec2(this.x - that.x, this.y - that.y)

  /** Scalar multiplication operator. */
  def *(n: T): Vec2[T] = Vec2(this.x * n, this.y * n)

  /** Static left shift operator. */
  def <<(n: Int): Vec2[T] = Vec2((this.x << n).asTypeOf(this.x), (this.y << n).asTypeOf(this.y))

  /** Static right shift operator. */
  def >>(n: Int): Vec2[T] = Vec2((this.x >> n).asTypeOf(this.x), (this.y >> n).asTypeOf(this.y))
}

object Vec2 {
  /**
   * Creates a vector from X and Y values.
   *
   * @param x The horizontal position.
   * @param y The vertical position.
   * @return A vector.
   */
  def apply[T <: Bits with Num[T]](x: T, y: T): Vec2[T] = {
    val vec = Wire(new Vec2(x))
    vec.x := x
    vec.y := y
    vec
  }
}

object UVec2 {
  /**
   * Creates an unsigned vector bundle.
   *
   * @param width The data width.
   * @return A bundle.
   */
  def apply(width: Width): UVec2 = new Vec2(UInt(width))

  /**
   * Creates an unsigned vector from X and Y values.
   *
   * @param x The horizontal position.
   * @param y The vertical position.
   * @return An unsigned vector.
   */
  def apply(x: Bits, y: Bits): UVec2 = {
    val vec = Wire(new Vec2(UInt(x.getWidth.W)))
    vec.x := x.asUInt
    vec.y := y.asUInt
    vec
  }
}

object SVec2 {
  /**
   * Creates a signed vector bundle.
   *
   * @param width The data width.
   * @return A bundle.
   */
  def apply(width: Width): SVec2 = new Vec2(SInt(width))

  /**
   * Creates a signed vector from X and Y values.
   *
   * @param x The horizontal position.
   * @param y The vertical position.
   * @return A signed vector.
   */
  def apply(x: Bits, y: Bits): SVec2 = {
    val vec = Wire(new Vec2(SInt(x.getWidth.W)))
    vec.x := x.asSInt
    vec.y := y.asSInt
    vec
  }
}
