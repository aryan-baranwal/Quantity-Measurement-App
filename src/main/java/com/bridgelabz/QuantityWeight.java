package com.bridgelabz;

public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {

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

    public WeightUnit getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = convertToBaseUnit();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    public QuantityWeight add(QuantityWeight other) {

        if (other == null)
            throw new IllegalArgumentException("Other weight cannot be null");

        double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();

        double result = this.unit.convertFromBaseUnit(baseSum);

        return new QuantityWeight(result, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other weight cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();

        double result = targetUnit.convertFromBaseUnit(baseSum);

        return new QuantityWeight(result, targetUnit);
    }

    private boolean compare(QuantityWeight other) {
        return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;

        return compare(other);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}