package com.airwallex.interview;

import java.util.Scanner;

/**
 * Create commandline reader for RPN calculator input
 * Can create different readers like: UI component reader later
 */
public class CommandLineReader implements CalculatorReader{

    // Exit flag for command line
    private final static String exit = "EXIT";

    private RPNCalculator calculator;

    /**
     * Bind a RPN calculator for a command line reader
     */
    public CommandLineReader() {
        calculator = new RPNCalculator();
    }

    /**
     * Start the command line reader, read system input and print the result
     */
    public void start() {
        for (boolean endLoop = false; !endLoop;)
        {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (exit.equalsIgnoreCase(line)) {
                endLoop = true;
            } else {
                System.out.println(calculator.execute(line));
            }
        }
    }

}
