package com.adactin.components;

import com.adactin.utils.BrowserAndDriverFactory;
import com.adactin.utils.ExtentFactory;
import com.adactin.utils.LoggerFactory;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Base64;

public class WebActions {

    public Wait<WebDriver> initFluentWait() {

        return new FluentWait<>(BrowserAndDriverFactory.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(WebDriverException.class)
                .ignoring(RuntimeException.class);
    }

    public synchronized void fillElement(WebElement element, String fieldName, String value) {
        try {
            Wait<WebDriver> wait = initFluentWait();
            wait.until(ExpectedConditions.visibilityOf(element));

            element.sendKeys(value);
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " was filled successfully");

        } catch (NoSuchElementException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not found");
            LoggerFactory.error("NoSuchElementException");
            ExceptionHandler.handleException("fillElement - NoSuchElementException", e);

        } catch (TimeoutException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not visible in time");
            LoggerFactory.error("TimeoutException");
            ExceptionHandler.handleException("fillElement - TimeoutException", e);

        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " failed to fill");
            LoggerFactory.error("Failed to fill element");
            ExceptionHandler.handleException("fillElement", e);

        } finally {
            LoggerFactory.info("Element was filled successfully element");
        }
    }

    public synchronized void clickElement(WebElement element, String fieldName) {
        try {
            Wait<WebDriver> wait = initFluentWait();
            wait.until(ExpectedConditions.visibilityOf(element));

            element.click();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " was clicked successfully");

        } catch (NoSuchElementException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not found");
            LoggerFactory.error("NoSuchElementException");
            ExceptionHandler.handleException("clickElement - NoSuchElementException", e);

        } catch (TimeoutException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not visible in time");
            LoggerFactory.error("TimeoutException");
            ExceptionHandler.handleException("clickElement - TimeoutException", e);

        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " failed to click");
            LoggerFactory.error("Failed to click element");
            ExceptionHandler.handleException("clickElement", e);

        } finally {
            LoggerFactory.info("Element was clicked successfully");
        }
    }

    public synchronized void clearElement(WebElement element, String fieldName) {
        try {
            Wait<WebDriver> wait = initFluentWait();
            wait.until(ExpectedConditions.visibilityOf(element));

            element.clear();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " was cleared successfully");

        } catch (NoSuchElementException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not found");
            LoggerFactory.error("NoSuchElementException");
            ExceptionHandler.handleException("clearElement - NoSuchElementException", e);

        } catch (TimeoutException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not visible in time");
            LoggerFactory.error("TimeoutException");
            ExceptionHandler.handleException("clearElement - TimeoutException", e);

        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " failed to clear");
            LoggerFactory.error("Failed to clear element");
            ExceptionHandler.handleException("clearElement", e);

        } finally {
            LoggerFactory.info("Element was cleared successfully");
        }
    }


    public synchronized void selectElementFromDropDown(WebElement element, String fieldName, String selectMethod, Object dropDownValue) {
        try {
            Wait<WebDriver> wait = initFluentWait();
            wait.until(ExpectedConditions.visibilityOf(element));

            Select select = new Select(element);

            switch (selectMethod.toLowerCase()) {
                case "selectbyvisibletext":
                    select.selectByVisibleText((String) dropDownValue);
                    break;

                case "selectbyvalue":
                    select.selectByValue((String) dropDownValue);
                    break;

                case "selectbyindex":
                    select.selectByIndex((Integer) dropDownValue);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid specified select method " + selectMethod);
            }

            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " was selected successfully");

        } catch (NoSuchElementException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not found");
            LoggerFactory.error("NoSuchElementException");
            ExceptionHandler.handleException("selectElementFromDropDown - NoSuchElementException", e);

        } catch (TimeoutException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not visible in time");
            LoggerFactory.error("TimeoutException");
            ExceptionHandler.handleException("selectElementFromDropDown - TimeoutException", e);

        } catch (IllegalArgumentException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Invalid select method: " + selectMethod);
            LoggerFactory.error("Failed to select element from drop down");
            ExceptionHandler.handleException("selectElementFromDropDown", e);

        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " failed to select");
            LoggerFactory.error("Failed to select element from drop down");
            ExceptionHandler.handleException("selectElementFromDropDown", e);

        } finally {
            LoggerFactory.info("Element was selected from drop down successfully");
        }
    }

    public void captureScreenshot(String screenshotName) {

        try {
            File screenShot = ((TakesScreenshot) BrowserAndDriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
            byte[] fileScreenshot = Files.readAllBytes(screenShot.toPath());
            String baseEncoderScreenshot = Base64.getEncoder().encodeToString(fileScreenshot);

            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Screenshot captured successfully", MediaEntityBuilder.createScreenCaptureFromBase64String(baseEncoderScreenshot).build());

        } catch (IllegalArgumentException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to take screenshot");
            LoggerFactory.error("Failed to select element from drop down");
            ExceptionHandler.handleException("captureScreenshot", e);

        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to take screenshot");
            LoggerFactory.error("Failed to take screenshot");
            ExceptionHandler.handleException("captureScreenshot", e);

        } finally {
            LoggerFactory.info("screenshot was taken successfully");
        }
    }

    public void isElementPresent(WebElement element, String fieldName) {

        try {

            if (element.isDisplayed()) {
                ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " is visible");
            } else {
                ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " is not visible");
            }

        } catch (NoSuchElementException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not found");
            LoggerFactory.error("NoSuchElementException");
            ExceptionHandler.handleException("isElementPresent - NoSuchElementException", e);

        } catch (TimeoutException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " not visible in time");
            LoggerFactory.error("TimeoutException");
            ExceptionHandler.handleException("isElementPresent - TimeoutException", e);

        } catch (IllegalArgumentException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element is not visible");
            LoggerFactory.error("Failed to locate element");
            ExceptionHandler.handleException("isElementPresent", e);

        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to locate element");
            LoggerFactory.error("Failed to locate element");
            ExceptionHandler.handleException("isElementPresent", e);

        } finally {
            LoggerFactory.info("Element was located successfully");
        }
    }

    public void isElementAbsent(WebElement element, String fieldName) {

        try {
            if (!element.isDisplayed()) {
                ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " is not visible");
            } else {
                ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " is visible");
                LoggerFactory.error("Element " + fieldName + " is visible");
            }
        } catch (NoSuchElementException e) {
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " is not visible");
        } catch (TimeoutException e) {
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element " + fieldName + " is not visible in time (TimeoutException)");
            ExceptionHandler.handleException("isNotElementPresent", e);
        } catch (IllegalArgumentException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " is visible (IllegalArgumentException)");
            LoggerFactory.error("Failed to locate element");
            ExceptionHandler.handleException("isNotElementPresent - IllegalArgumentException", e);
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element " + fieldName + " is visible (Exception)");
            LoggerFactory.error("Failed to locate element");
            ExceptionHandler.handleException("isNotElementPresent - Exception", e);
        } finally {
            LoggerFactory.info("Element visibility check completed");
        }
    }
}
