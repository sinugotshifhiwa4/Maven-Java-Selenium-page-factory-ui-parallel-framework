package com.adactin.utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {

    private static final ExtentFactory instance = new ExtentFactory();
    private static final ThreadLocal<ExtentTest> threadLocalExtent = new ThreadLocal<>();

    private ExtentFactory(){}

    public static ExtentFactory getInstance(){
        return instance;
    }

    public void setExtent(ExtentTest extent){
        threadLocalExtent.set(extent);
    }

    public ExtentTest getExtent(){
        return threadLocalExtent.get();
    }

    public void removeExtent(){
        threadLocalExtent.remove();
    }
}
