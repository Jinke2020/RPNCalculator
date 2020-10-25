package com.airwallex.interview;

import org.junit.Test;
import static org.junit.Assert.*;

public class RPNCalculatorTest {

    @Test
    public void testAdd() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 +";
        String expected = "stack: 3.0000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testSubtract() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 -";
        String expected = "stack: -1.0000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testMultiply() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 *";
        String expected = "stack: 2.0000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testDivide() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 /";
        String expected = "stack: 0.5000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testSquared() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "4 sqrt";
        String expected = "stack: 2.0000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testClear() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 * clear";
        String expected = "stack: ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testUndo() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "5 4 3 2 undo undo * 5 * undo";
        String expected = "stack: 20.0000000000 5.0000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testInsufficientParam() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 3 * 5 + * * 6 5";
        String expected = "operator * (position: 15): insufficient parameters\n" +
                            "stack: 11.0000000000 \n" +
                            "(the 6, 5 were not pushed on to the stack due to the previous error)";
        assertEquals(expected, calculator.execute(commandLine));
    }

    @Test
    public void testInvalidInput() {
        RPNCalculator calculator = new RPNCalculator();
        String commandLine = "1 2 @";
        String expected = "operator @ (position: 5): invalid input\n" +
                            "stack: 1.0000000000 2.0000000000 ";
        assertEquals(expected, calculator.execute(commandLine));
    }
}
