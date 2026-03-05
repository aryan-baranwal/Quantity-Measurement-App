package com.bridgelabz;

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;

        public enum LengthUnit {
            FEET(12.0),
            INCHES(1.0),
            YARDS(36.0),
            CENTIMETERS(0.393701);

            private final double factor;

            LengthUnit(double factor) {
                this.factor = factor;
            }

            public double getFactor() {
                return factor;
            }
        }

        public Length(double value, LengthUnit unit) {
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        private double convertToBaseUnit() {
            return value * unit.getFactor();
        }

        private double convertFromBaseUnit(double baseValue, LengthUnit targetUnit) {
            return baseValue / targetUnit.getFactor();
        }

        public Length convertTo(LengthUnit targetUnit) {

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = convertToBaseUnit();
            double converted = convertFromBaseUnit(base, targetUnit);

            return new Length(converted, targetUnit);
        }

        public Length add(Length other) {

            if (other == null)
                throw new IllegalArgumentException("Other length cannot be null");

            double base1 = this.convertToBaseUnit();
            double base2 = other.convertToBaseUnit();

            double sumBase = base1 + base2;

            double resultValue = convertFromBaseUnit(sumBase, this.unit);

            return new Length(resultValue, this.unit);
        }

        public boolean compare(Length other) {
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
            return String.format("%.2f %s", value, unit);
        }
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2);

        System.out.println(result);
    }
}