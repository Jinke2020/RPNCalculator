package com.airwallex.interview;

/**
 * Enum for all available operators in RPN calculator.
 * Now support: ADD(+), MINUS(-), MULTIPLY(*), DIVIDE(/), SQUARED(sqrt)
 * Add more operators here:  n!, COS, ATAN, swap, redo, etc
 * Don't add functionality like undo/clear here, it's already defined in RPNCalculator
 */
public enum RPNOperators {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    SQUARED("sqrt");

    String pattern;
    RPNOperators(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }

}
