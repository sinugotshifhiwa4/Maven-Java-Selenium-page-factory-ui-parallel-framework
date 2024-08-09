package com.adactin.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerFactory {

    private static final ThreadLocal<Logger> threadLocalLogger = ThreadLocal.withInitial(() -> LogManager.getLogger(LoggerFactory.class));

    private LoggerFactory() {}


    public static void setLogger(Class<?> clazz) {
        threadLocalLogger.set(LogManager.getLogger(clazz));
    }

    public static void info(String message) {
        threadLocalLogger.get().info(message);
    }

    public static void warn(String message) {
        threadLocalLogger.get().warn(message);
    }

    public static void error(String message) {
        threadLocalLogger.get().error(message);
    }

    public static void debug(String message) {
        threadLocalLogger.get().debug(message);
    }

    public static void trace(String message) {
        threadLocalLogger.get().trace(message);
    }

    public static void fatal(String message) {
        threadLocalLogger.get().fatal(message);
    }
}
