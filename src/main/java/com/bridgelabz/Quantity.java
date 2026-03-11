package com.bridgelabz;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double convertToBase() {
        return unit.convertToBaseUnit(value);
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = convertToBase();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(converted, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category addition not allowed");

        double baseSum = this.convertToBase() + other.convertToBase();

        double result = unit.convertFromBaseUnit(baseSum);

        return new Quantity<>(result, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category addition not allowed");

        double baseSum = this.convertToBase() + other.convertToBase();

        double result = targetUnit.convertFromBaseUnit(baseSum);

        return new Quantity<>(result, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category subtraction not allowed");

        double baseResult = this.convertToBase() - other.convertToBase();

        double result = unit.convertFromBaseUnit(baseResult);

        result = Math.round(result * 100.0) / 100.0;

        return new Quantity<>(result, unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category subtraction not allowed");

        double baseResult = this.convertToBase() - other.convertToBase();

        double result = targetUnit.convertFromBaseUnit(baseResult);

        result = Math.round(result * 100.0) / 100.0;

        return new Quantity<>(result, targetUnit);
    }

    public double divide(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category division not allowed");

        double baseOther = other.convertToBase();

        if (baseOther == 0)
            throw new ArithmeticException("Division by zero");

        return this.convertToBase() / baseOther;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Quantity<?> other))
            return false;

        if (unit.getClass() != other.unit.getClass())
            return false;

        return Double.compare(this.convertToBase(), other.convertToBase()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBase());
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}