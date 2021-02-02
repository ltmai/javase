package com.example.java.interview;

/**
 * Outer class with default access control (package private) is only invisible 
 * to the classes in the same package (folder).
 */
class Outer {
    /**
     * static inner class can be instantiated without an instance of enclosing class
     */
    static class StaticInner {
        public static final String name="Private Inner class";

		public void doSomething() {
		}
    }

    /**
     * non-static inner class can only instantiated with an instance of an enclosing class
     */
    class Inner {

		public void doSomething() {
		}
    }

    /**
     * private inner class can only be used internally
     */
    private class PrivateInner {
    }

    Outer() {
        new PrivateInner();
    }
}
