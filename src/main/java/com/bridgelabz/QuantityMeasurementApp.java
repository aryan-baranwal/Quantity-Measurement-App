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

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
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

        // UC6 addition (result in unit of first operand)
        public Length add(Length other) {

            if (other == null)
                throw new IllegalArgumentException("Other length cannot be null");

            double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();
            double result = convertFromBaseUnit(baseSum, this.unit);

            return new Length(result, this.unit);
        }

        // UC7 addition with explicit target unit
        public Length add(Length other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Other length cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();
            double result = convertFromBaseUnit(baseSum, targetUnit);

            return new Length(result, targetUnit);
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
            return String.format("%.3f %s", value, unit);
        }
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        System.out.println(l1.add(l2));
        System.out.println(l1.add(l2, Length.LengthUnit.FEET));
        System.out.println(l1.add(l2, Length.LengthUnit.INCHES));
        System.out.println(l1.add(l2, Length.LengthUnit.YARDS));
    }
}