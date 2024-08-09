package com.adactin.components;

import com.adactin.utils.BrowserAndDriverFactory;
import com.adactin.utils.ExtentFactory;
import com.adactin.utils.LoggerFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class ReportListeners implements ITestListener {

    ExtentReports extentReports;
    ExtentTest test;
    String methodName;

    public void onStart(ITestContext context) {

        // Retrieve parameters from the test context
        String browser = context.getCurrentXmlTest().getParameter("browser");
        String url = context.getCurrentXmlTest().getParameter("url");
        extentReports = ExtentReportConfig.initExtentReport(browser, url);
    }

    public void onTestStart(ITestResult result) {
        methodName = result.getMethod().getMethodName();
        test = extentReports.createTest(methodName);
        ExtentFactory.getInstance().setExtent(test);
    }

    public void onTestSuccess(ITestResult result) {
        ExtentFactory.getInstance().getExtent().log(Status.PASS, "Test case " + methodName + " is Passed.");
        ExtentFactory.getInstance().removeExtent();
    }

    public void onTestFailure(ITestResult result) {
        try {
            // Capture the screenshot as a file
            File screenshot = ((TakesScreenshot) BrowserAndDriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);

            // Read the screenshot file into a byte array
            byte[] screenshotFile = Files.readAllBytes(screenshot.toPath());

            // Encode the screenshot to Base64
            String base64Screenshot = Base64.getEncoder().encodeToString(screenshotFile);

            // Log the failure status and attach the screenshot to the report
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Test case " + methodName + " is Failed.", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, result.getThrowable());
            //ExtentFactory.getInstance().getExtent().addScreenCaptureFromBase64String(base64Screenshot, "Test Failed Screenshot");

            // Remove the extent instance
            ExtentFactory.getInstance().removeExtent();

            // Delete the screenshot file to free up space
            Files.delete(screenshot.toPath());

        } catch (Exception e) {
            // Log the error and handle the exception
            LoggerFactory.error("Failed to capture failed test");
            ExceptionHandler.handleException("onTestFailure", e);

        } finally {
            // Log the successful operation
            LoggerFactory.info("Captured failed test successfully");
        }
    }

    public void onTestSkipped(ITestResult result) {
        ExtentFactory.getInstance().getExtent().log(Status.SKIP, "Test case " + methodName + " is Skipped");
        ExtentFactory.getInstance().removeExtent();
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
