package com.bridgelabz;

import java.util.function.DoubleBinaryOperator;

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

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Quantity<?> other))
            return false;

        if (unit.getClass() != other.unit.getClass())
            return false;

        return Double.compare(this.convertToBase(),
                other.convertToBase()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBase());
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }

    /* -------------------------
       Arithmetic Operations
       ------------------------- */

    public Quantity<U> add(Quantity<U> other) {

        unit.validateOperationSupport("addition");

        validateArithmeticOperands(other, unit, false);

        double baseResult = performBaseArithmetic(other,
                ArithmeticOperation.ADD);

        double result = unit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        unit.validateOperationSupport("addition");

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other,
                ArithmeticOperation.ADD);

        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(result), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {

        unit.validateOperationSupport("subtraction");

        validateArithmeticOperands(other, unit, false);

        double baseResult = performBaseArithmetic(other,
                ArithmeticOperation.SUBTRACT);

        double result = unit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        unit.validateOperationSupport("subtraction");

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other,
                ArithmeticOperation.SUBTRACT);

        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(result), targetUnit);
    }

    public double divide(Quantity<U> other) {

        unit.validateOperationSupport("division");

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    /* -------------------------
       Centralized Helpers
       ------------------------- */

    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category operation not allowed");

        if (!Double.isFinite(value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");

        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }

    private double performBaseArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation) {

        double baseThis = this.convertToBase();
        double baseOther = other.convertToBase();

        return operation.compute(baseThis, baseOther);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /* -------------------------
       Arithmetic Enum
       ------------------------- */

    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (b == 0)
                throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator operation;

        ArithmeticOperation(DoubleBinaryOperator operation) {
            this.operation = operation;
        }

        public double compute(double a, double b) {
            return operation.applyAsDouble(a, b);
        }
    }
}