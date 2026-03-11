package com.bridgelabz;

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {

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

        public LengthUnit getUnit() {
            return unit;
        }

        private double convertToBaseUnit() {
            return unit.convertToBaseUnit(value);
        }

        public Length convertTo(LengthUnit targetUnit) {

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseValue = convertToBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(baseValue);

            return new Length(converted, targetUnit);
        }

        public Length add(Length other) {

            if (other == null)
                throw new IllegalArgumentException("Other length cannot be null");

            double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();
            double result = this.unit.convertFromBaseUnit(baseSum);

            return new Length(result, this.unit);
        }

        public Length add(Length other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Other length cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();
            double result = targetUnit.convertFromBaseUnit(baseSum);

            return new Length(result, targetUnit);
        }

        private boolean compare(Length other) {
            return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Length other = (Length) obj;

            return compare(other);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }
}