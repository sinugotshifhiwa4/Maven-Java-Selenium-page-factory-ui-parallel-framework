package com.adactin.components;

import com.adactin.utils.LoggerFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportConfig {

    static ExtentReports reports;


    public static ExtentReports initExtentReport(String browser, String url) {
        try {
            // Format the current date and time for the report file name
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            Date date = new Date();
            String formatDate = simpleDateFormat.format(date);

            // Define the report path
            String reportPath = System.getProperty("user.dir") + "/reports/adactin-hotel-report-" + formatDate + ".html";

            // Initialize the ExtentSparkReporter with the report path
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Initialize ExtentReports and attach the reporter
            reports = new ExtentReports();
            reports.attachReporter(sparkReporter);

            // Configure the report
            sparkReporter.config().setDocumentTitle(ConfigProperties.getPropertyByKey("PROJECT_NAME"));
            sparkReporter.config().setReportName(ConfigProperties.getPropertyByKey("REPORT_NAME"));
            sparkReporter.config().setTheme(Theme.DARK);

            // Set system information
            reports.setSystemInfo("Environment", ConfigProperties.getPropertyByKey("ENV"));
            reports.setSystemInfo("URL", url);
            reports.setSystemInfo("Browser", browser);
            reports.setSystemInfo("OS", System.getProperty("os.name"));
            reports.setSystemInfo("User", System.getProperty("user.name"));

        } catch (Exception e) {
            // Log the error and handle the exception
            LoggerFactory.error("Failed to initialize extent report");
            ExceptionHandler.handleException("initExtentReport", e);

        } finally {
            // Log the successful operation
            LoggerFactory.info("Extent report was initialized successfully");
        }

        return reports;
    }
}
