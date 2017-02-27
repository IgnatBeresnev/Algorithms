package me.beresnev.algorithms;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 27.02.17.
 */
public class HashCode {

    private HashCode() {
    }

    public static class SomeObject {
        private int x;
        private int y;

        public SomeObject(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * A very classical approach to generating hashcode.
         */
        public int hashCode() {
            final int prime = 31;
            int result = prime;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }
    }
}
