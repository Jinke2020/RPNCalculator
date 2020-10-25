package com.airwallex.interview;

import java.math.BigDecimal;
import java.util.*;

import static com.airwallex.interview.RPNOperators.*;
/**
 * Calculator for RPN(Reverse Polish Notation)
 */
public class RPNCalculator {

    // The calculator has a stack that can contain real numbers
    private Stack<BigDecimal> stack = new Stack<BigDecimal>();

    // Record all the numbers/operators
    private Stack<String> history = new Stack<String>();

    // When calculate, we need to pop out some numbers from stack and push new items, save the items when stack pop out something
    private Stack<BigDecimal> popRecords = new Stack<BigDecimal>();

    // Unary Operators
    private final RPNOperators[] unaryOperators = new RPNOperators[] {SQUARED};
    // Binary Operators
    private final RPNOperators[] binaryOperators = new RPNOperators[] {ADD, SUBTRACT, MULTIPLY, DIVIDE};

    // Functional operation
    private static final String UNDO = "undo";
    private static final String CLEAR = "clear";

    // Warning message
    private static final String insufficientParamWarningMsg = "operator %s (position: %d): insufficient parameters";
    private static final String warningSuffixMsg = "(the %s were not pushed on to the stack due to the previous error)";
    private static final String invalidInputWarningMsg = "operator %s (position: %d): invalid input";

    // When print stack content, we need to add the prefix
    private static final String printStackPrefix = "stack: ";

    // Numbers should be stored on the stack to at least 15 decimal places of precision,
    // but displayed to 10 decimal places (or less if it causes no loss of precision).
    private static final int internalScale = 15;
    private static final int displayScale = 10;

    /**
     * Execute each command line from input
     * @param commandLine
     * @return display string of the current stack content after execute the command line
     */
    public String execute(String commandLine) {
        // Record the position of each number/operator
        int position = 0;

        String[] items = commandLine.split("\\s+");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            int index = commandLine.indexOf(item);
            // Update the position
            if (i == 0) {
                position = index + 1;
            } else {
                position = position + items[i-1].length() + index;
            }
            commandLine = commandLine.substring(index + item.length());

            // Check whether it's functional operation
            if (CLEAR.equalsIgnoreCase(item)) {
                clear();
                continue;
            }
            if (UNDO.equalsIgnoreCase(item)) {
                undo();
                continue;
            }
            // It's number or rpn operators
            // Save to history stack
            history.push(item);
            // Handle number or rpn operations
            if (isNumeric(item)) {
                // Numbers should be stored on the stack to at least 15 decimal places of precision
                stack.push(new BigDecimal(item).setScale(internalScale, BigDecimal.ROUND_HALF_UP));
            } else {
                RPNOperators opt = validateOperator(item);
                if (null == opt) {
                    // Invalid input
                    StringBuffer errMsg = new StringBuffer("");
                    errMsg.append(String.format(invalidInputWarningMsg, item, position));
                    errMsg.append("\n");
                    errMsg.append(printStackContent());
                    return errMsg.toString();
                } else {
                    // Check whether it's unary/binary operator, and there's enough numbers in stack
                    // Add ternary operator or other checks here
                    if (Arrays.asList(unaryOperators).contains(opt) && stack.size() < 1
                            || Arrays.asList(binaryOperators).contains(opt) && stack.size() < 2) {
                        StringBuffer errMsg = new StringBuffer("");
                        errMsg.append(String.format(insufficientParamWarningMsg, item, position));
                        errMsg.append("\n");
                        errMsg.append(printStackContent());
                        errMsg.append("\n");
                        StringBuffer bf = new StringBuffer("");
                        for (int j = i + 1; j < items.length - 1; j++) {
                            bf.append(items[j]);
                            bf.append(", ");
                        }
                        bf.append(items[items.length - 1]);
                        errMsg.append(String.format(warningSuffixMsg, bf.toString()));
                        // Stop and return the error msg
                        return errMsg.toString();
                    }
                    switch (opt) {
                        case ADD:
                            add();
                            break;
                        case SUBTRACT:
                            subtract();
                            break;
                        case MULTIPLY:
                            multiply();
                            break;
                        case DIVIDE:
                            divide();
                            break;
                        case SQUARED:
                            squared();
                            break;
                    }
                }
            }
        }

        return printStackContent();
    }

    /**
     * Print the current stack content
     * @return display string composed by space-separated list
     */
    private String printStackContent() {
        StringBuffer stringBuffer = new StringBuffer(printStackPrefix);
        Iterator<BigDecimal> iterator = stack.iterator();
        while (iterator.hasNext()) {
            // Displayed to 10 decimal places, also apply round half up here
            BigDecimal formattedVal = new BigDecimal(iterator.next().toPlainString()).setScale(displayScale, BigDecimal.ROUND_HALF_UP );
            // All numbers should be formatted as plain decimal strings
            stringBuffer.append(formattedVal.toPlainString()).append(" ");
        }
        // Remove the last whitespace

        return stringBuffer.toString();
    }

    /**
     * Check whether a given string is numeric
     * @param strNum, the given string
     * @return true if given string is numeric, else false
     */
    private boolean isNumeric(String strNum) {
        if (null == strNum) {
            return false;
        }
        try {
            double num = Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Check whether a given string is supported RPN operator
     * @param operator, the given operator string
     * @return RPNOperator if the given operator string is supported, else null
     */
    private RPNOperators validateOperator(String operator) {
        for (RPNOperators opt: RPNOperators.values()) {
            if (opt.toString().equalsIgnoreCase(operator)) {
                return opt;
            }
        }
        return null;
    }

    /**
     * Add operation
     */
    private void add() {
        BigDecimal num1 = stack.pop();
        BigDecimal num2 = stack.pop();
        BigDecimal tmp = num2.add(num1).setScale(internalScale, BigDecimal.ROUND_HALF_UP);
        stack.push(tmp);
        // Do remember to save the popped item
        // Keep the order when save into popRecords
        popRecords.push(num1);
        popRecords.push(num2);
    }

    /**
     * Subtract operation
     */
    private void subtract() {
        BigDecimal num1 = stack.pop();
        BigDecimal num2 = stack.pop();
        BigDecimal tmp = num2.subtract(num1).setScale(internalScale, BigDecimal.ROUND_HALF_UP);
        stack.push(tmp);
        popRecords.push(num1);
        popRecords.push(num2);
    }

    /**
     * Multiply operation
     */
    private void multiply() {
        BigDecimal num1 = stack.pop();
        BigDecimal num2 = stack.pop();
        BigDecimal tmp = num2.multiply(num1).setScale(internalScale, BigDecimal.ROUND_HALF_UP);
        stack.push(tmp);
        popRecords.push(num1);
        popRecords.push(num2);
    }

    /**
     * Divide operation
     */
    private void divide() {
        BigDecimal num1 = stack.pop();
        BigDecimal num2 = stack.pop();
        BigDecimal tmp = num2.divide(num1).setScale(internalScale, BigDecimal.ROUND_HALF_UP);
        stack.push(tmp);
        popRecords.push(num1);
        popRecords.push(num2);
    }

    /**
     * Squared operation
     */
    private void squared() {
        BigDecimal num = stack.pop();
        Double tmp = Math.sqrt(num.doubleValue());
        stack.push(BigDecimal.valueOf(tmp).setScale(internalScale, BigDecimal.ROUND_HALF_UP));
        popRecords.push(num);
    }

    /**
     * Clean all the content in stack
     */
    private void clear() {
        stack.clear();
        history.clear();
        popRecords.clear();
    }

    /**
     * Undo the previous operation
     */
    private void undo() {
        if (history.size() == 0) {
            // There nothing for undo
            return;
        } else {
            String item = history.pop();
            if (isNumeric(item)) {
                // Undo a number, remove that number in stack
                stack.pop();
                return;
            }
            RPNOperators rpnOperators = validateOperator(item);
            if (Arrays.asList(unaryOperators).contains(rpnOperators)) {
                // Undo an unary operator, pop out the first item in stack, and move the first item from popRecord to stack
                stack.pop();
                stack.push(popRecords.pop());
                return;
            }
            if (Arrays.asList(binaryOperators).contains(rpnOperators)) {
                // Undo a binary operator, pop out the first item in stack, and move the first 2 item from popRecord to stack
                stack.pop();
                stack.push(popRecords.pop());
                stack.push(popRecords.pop());
                return;
            }
        }
    }
}
