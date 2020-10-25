package com.airwallex.interview;

public class Application {

    public static void main(String[] args) {
        System.out.println("************************************************************");
        System.out.println("                  Welcome to RPN Calculator                 ");
        System.out.println("Usage: Input numbers and operators with whitespace separated");
        System.out.println("Supported operators: +, -, *, /, sqrt, undo, clear          ");
        System.out.println("Example: 1 2 +                                              ");
        System.out.println("Output: 3.0000000000                                        ");
        System.out.println("Type exit to quite the calculator                           ");
        System.out.println("************************************************************");
        CommandLineReader commandLineReader = new CommandLineReader();
        commandLineReader.start();
    }

}
