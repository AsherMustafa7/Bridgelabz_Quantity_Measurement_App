
package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    // ---------------- FEET CLASS ----------------

    public class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // ---------------- INCHES CLASS ----------------

    public class Inches {

        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // ---------------- STATIC COMPARISON METHODS ----------------

    public static boolean areFeetEqual(Feet first, Feet second) {
        return first != null && first.equals(second);
    }

    public static boolean areInchesEqual(Inches first, Inches second) {
        return first != null && first.equals(second);
    }

    // ---------------- MAIN METHOD ----------------

    public static void main(String[] args) {

        QuantityMeasurementApp app = new QuantityMeasurementApp();

        Feet feet1 = app.new Feet(1.0);
        Feet feet2 = app.new Feet(1.0);

        Inches inches1 = app.new Inches(1.0);
        Inches inches2 = app.new Inches(1.0);

        System.out.println(
                "Are feet measurements equal? "
                        + areFeetEqual(feet1, feet2)
        );

        System.out.println(
                "Are inches measurements equal? "
                        + areInchesEqual(inches1, inches2)
        );
    }
}
