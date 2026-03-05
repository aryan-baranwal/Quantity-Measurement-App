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

            private final double conversionFactor;

            LengthUnit(double conversionFactor) {
                this.conversionFactor = conversionFactor;
            }

            public double getConversionFactor() {
                return conversionFactor;
            }
        }

        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double convertToBaseUnit() {
            return value * unit.getConversionFactor();
        }

        public Length convertTo(LengthUnit targetUnit) {

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseValue = convertToBaseUnit();
            double convertedValue = baseValue / targetUnit.getConversionFactor();

            return new Length(convertedValue, targetUnit);
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

    // Static conversion API
    public static double convert(double value, Length.LengthUnit source, Length.LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        double baseValue = value * source.getConversionFactor();
        return baseValue / target.getConversionFactor();
    }

    public static void main(String[] args) {

        System.out.println(convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
        System.out.println(convert(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET));
        System.out.println(convert(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS));
        System.out.println(convert(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES));
    }
}