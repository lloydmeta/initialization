// Copyright (c) 2013 Aleksander Bielawski. All rights reserved.
// Use of this source code is governed by a BSD-style license
// that can be found in the LICENSE file.

package com.github.pabzdzdzwiagief.initialization

import annotation.StaticAnnotation

/** Represents something that happens during initialization procedure. */
private[this] sealed abstract class Trace
  extends StaticAnnotation with Product {
  /** Object that identifies relevant class member. */
  def member: AnyRef

  /** Point in relevant source file. */
  def point: Int

  /** For comparing with other annotations attached to the same symbol.
    * Instruction happens before those for which this value is greater.
    */
  def ordinal: Int
}
private[this] final case class Access(member: AnyRef, point: Int, ordinal: Int)
  extends Trace
private[this] final case class Assign(member: AnyRef, point: Int, ordinal: Int)
  extends Trace
private[this] sealed case class Invoke(member: AnyRef, point: Int, ordinal: Int)
  extends Trace
private[this] final class Special(member: AnyRef, point: Int, ordinal: Int)
  extends Invoke(member, point, ordinal)