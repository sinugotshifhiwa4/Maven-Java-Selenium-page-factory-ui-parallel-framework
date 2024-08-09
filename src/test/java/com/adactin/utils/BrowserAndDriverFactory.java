package com.adactin.utils;

import com.adactin.components.ExceptionHandler;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URL;

public class BrowserAndDriverFactory {

    private static final BrowserAndDriverFactory instance = new BrowserAndDriverFactory();
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private BrowserAndDriverFactory() {
    }

    public static BrowserAndDriverFactory getInstance() {
        return instance;
    }

    public void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }

    public WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public WebDriver initializeBrowser(String browserName) {

        try {

            switch (browserName.toLowerCase()) {

                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    setDriver(new ChromeDriver(chromeOptions));
                    break;

                case "msedge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--headless");
                    setDriver(new EdgeDriver(edgeOptions));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless");
                    setDriver(new FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name specified " + browserName);
            }

        } catch (IllegalArgumentException e) {
            LoggerFactory.error("Failed to initialize browser");
            throw e; // Re-throw to propagate the exception
        } catch (Exception e) {
            LoggerFactory.error("Failed to initialize browser");
            ExceptionHandler.handleException("initializeBrowser", e);
        } finally {
            LoggerFactory.info("Browser initialized successfully");
        }

        return getDriver();
    }

    public WebDriver initGridBrowser(String browserName) {
        WebDriver driver = null;
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            switch (browserName.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    capabilities.setPlatform(Platform.WINDOWS); // Use Platform.WINDOWS if WIN11 is not available
                    break;

                case "msedge":
                    capabilities.setBrowserName("msedge");
                    capabilities.setPlatform(Platform.WINDOWS);
                    break;

                case "firefox":
                    capabilities.setBrowserName("firefox");
                    capabilities.setPlatform(Platform.WINDOWS);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name specified: " + browserName);
            }

            driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), capabilities); // Replace with your Grid URL
            BrowserAndDriverFactory.getInstance().setDriver(driver);
            LoggerFactory.info("Browser initialized successfully");

        } catch (IllegalArgumentException e) {
            LoggerFactory.error("Failed to initialize browser: " + e.getMessage());
            throw e; // Re-throw to propagate the exception
        } catch (Exception e) {
            LoggerFactory.error("Failed to initialize browser: " + e.getMessage());
            ExceptionHandler.handleException("initializeBrowser", e);
        }

        return driver;
    }


    public void navigateTo(String url) {
        getDriver().get(url);
    }

    public void quitDriver() {

        try {
            WebDriver driver = threadLocalDriver.get();

            if (driver != null) {
                Thread.sleep(4000);
                driver.quit();
            }

        } catch (InterruptedException e) {
            LoggerFactory.error("Failed to quit driver");
            Thread.currentThread().interrupt(); // Restore interrupted status
        } catch (Exception e) {
            LoggerFactory.error("Failed to quit driver");
            ExceptionHandler.handleException("quitDriver", e);
        } finally {
            threadLocalDriver.remove();
            LoggerFactory.info("Driver was quited successfully");
        }
    }
}
