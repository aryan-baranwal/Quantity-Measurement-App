package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.bridgelabz.QuantityMeasurementApp.Feet;
import com.bridgelabz.QuantityMeasurementApp.Inches;

public class QuantityMeasurementAppTest {

    // Feet Tests

    @Test
    void testFeetEquality_SameValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        assertTrue(f1.equals(f2));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(2.0);

        assertFalse(f1.equals(f2));
    }

    @Test
    void testFeetEquality_NullComparison() {
        Feet f1 = new Feet(1.0);

        assertFalse(f1.equals(null));
    }

    @Test
    void testFeetEquality_DifferentClass() {
        Feet f1 = new Feet(1.0);
        String str = "test";

        assertFalse(f1.equals(str));
    }

    @Test
    void testFeetEquality_SameReference() {
        Feet f1 = new Feet(1.0);

        assertTrue(f1.equals(f1));
    }

    // Inches Tests

    @Test
    void testInchesEquality_SameValue() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);

        assertTrue(i1.equals(i2));
    }

    @Test
    void testInchesEquality_DifferentValue() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(2.0);

        assertFalse(i1.equals(i2));
    }

    @Test
    void testInchesEquality_NullComparison() {
        Inches i1 = new Inches(1.0);

        assertFalse(i1.equals(null));
    }

    @Test
    void testInchesEquality_DifferentClass() {
        Inches i1 = new Inches(1.0);
        String str = "test";

        assertFalse(i1.equals(str));
    }

    @Test
    void testInchesEquality_SameReference() {
        Inches i1 = new Inches(1.0);

        assertTrue(i1.equals(i1));
    }
}