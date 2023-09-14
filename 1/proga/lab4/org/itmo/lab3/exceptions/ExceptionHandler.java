package org.itmo.lab3.exceptions;

public class ExceptionHandler {
    public static void handle(Exception exp) {
        describe(exp);
    }

    public static void describe(Exception exp) {
        System.out.println(exp.getMessage());
    }
}
