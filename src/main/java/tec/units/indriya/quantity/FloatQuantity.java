/*
 * Next Generation Units of Measurement Implementation
 * Copyright (c) 2005-2017, Jean-Marie Dautelle, Werner Keil, Otavio Santana.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363, Indriya nor the names of their contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.units.indriya.quantity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.units.indriya.AbstractQuantity;
import tec.units.indriya.ComparableQuantity;

/**
 * An amount of quantity, consisting of a float and a Unit. FloatQuantity objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Otavio de Santana
 * @param <Q>
 *          The type of the quantity.
 * @version 0.4, $Date: 2017-05-28 $
 * @since 1.0.7
 */
final class FloatQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> {

  /**
     * 
     */
  private static final long serialVersionUID = 5992028803791009345L;

  final float value;

  public FloatQuantity(float value, Unit<Q> unit) {
    super(unit);
    this.value = value;
  }

  @Override
  public Float getValue() {
    return value;
  }

  // Implements AbstractQuantity
  public double doubleValue(Unit<Q> unit) {
    return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
  }

  public long longValue(Unit<Q> unit) {
    double result = doubleValue(unit);
    if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
      throw new ArithmeticException("Overflow (" + result + ")");
    }
    return (long) result;
  }

  public Quantity<Q> add(ComparableQuantity<Q> that) {
    final Quantity<Q> converted = that.to(getUnit());
    return NumberQuantity.of(value + converted.getValue().floatValue(), getUnit());
  }

  public ComparableQuantity<Q> subtract(ComparableQuantity<Q> that) {
    final Quantity<Q> converted = that.to(getUnit());
    return NumberQuantity.of(value - converted.getValue().floatValue(), getUnit());
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ComparableQuantity<?> multiply(ComparableQuantity<?> that) {
    return new FloatQuantity(value * that.getValue().floatValue(), getUnit().multiply(that.getUnit()));
  }

  public ComparableQuantity<Q> multiply(Number that) {
    return NumberQuantity.of(value * that.floatValue(), getUnit().multiply(that.doubleValue()));
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ComparableQuantity<?> divide(ComparableQuantity<?> that) {
    return new FloatQuantity(value / that.getValue().floatValue(), getUnit().divide(that.getUnit()));
  }

  @SuppressWarnings("unchecked")
  public ComparableQuantity<Q> inverse() {
    return (AbstractQuantity<Q>) NumberQuantity.of(1f / value, getUnit().inverse());
  }

  public ComparableQuantity<Q> divide(Number that) {
    return NumberQuantity.of(value / that.floatValue(), getUnit());
  }

  /*
   * (non-Javadoc)
   * 
   * @see AbstractQuantity#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (obj instanceof Quantity<?>) {
      Quantity<?> that = (Quantity<?>) obj;
      return Objects.equals(getUnit(), that.getUnit()) && Equalizer.hasEquality(value, that.getValue());
    }
    return false;
  }

  @Override
  public ComparableQuantity<Q> add(Quantity<Q> that) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ComparableQuantity<Q> subtract(Quantity<Q> that) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ComparableQuantity<?> divide(Quantity<?> that) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ComparableQuantity<?> multiply(Quantity<?> multiplier) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isBig() {
    return false;
  }

  @Override
  public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx) throws ArithmeticException {
    return BigDecimal.valueOf(value);
  }
}
