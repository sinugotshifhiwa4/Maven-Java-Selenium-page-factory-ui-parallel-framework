package com.adactin.components;

public class ExceptionHandler {

    public static void handleException(String methodName, Exception e){
        throw new IllegalArgumentException("An error occurred on " + methodName + ": " + e.getMessage(), e);
    }
}
