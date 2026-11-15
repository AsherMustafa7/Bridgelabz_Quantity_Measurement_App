
package com.asher.quantitymeasurement;

public class QuantityMeasurementApp {

    /**
     * Represents a measurement in feet.
     * This is an immutable inner class.
     */
    public class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        /**
         * Compares two Feet objects based on their values.
         */
        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) {
                return true;
            }

            // Null or different class
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Safe type casting
            Feet other = (Feet) obj;

            // Compare measurement values
            return Double.compare(this.value, other.value) == 0;
        }

        /**
         * Maintains the equals() and hashCode() contract.
         */
        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    public static void main(String[] args) {

        QuantityMeasurementApp app = new QuantityMeasurementApp();

        Feet feet1 = app.new Feet(1.0);
        Feet feet2 = app.new Feet(1.0);

        System.out.println("Are measurements equal? "
                + feet1.equals(feet2));
    }
}
