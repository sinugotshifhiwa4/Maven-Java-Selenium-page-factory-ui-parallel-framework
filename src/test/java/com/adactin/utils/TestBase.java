package com.adactin.utils;

import com.adactin.components.ExceptionHandler;
import com.adactin.components.WebActions;
import com.adactin.pages.LoginPage;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.time.Duration;

public class TestBase extends WebActions {

    String sBrowser;
    String sUrl;
    protected LoginPage loginPage;
    protected WebDriverWait wait;


    @BeforeMethod
    @Parameters({"browser", "url"})
    public void init(String browser, String url, Method method) {
        this.sBrowser = browser;
        this.sUrl = url;

        try {
            // Set the log filename context
            String logFilename = method.getDeclaringClass().getSimpleName() + "-" + method.getName();
            ThreadContext.put("LogFilename", logFilename);
            // Set up Logger
            LoggerFactory.setLogger(this.getClass());

            // Initialize the browser and set the WebDriver instance
            BrowserAndDriverFactory browserAndDriverFactory = BrowserAndDriverFactory.getInstance();
            browserAndDriverFactory.setDriver(browserAndDriverFactory.initGridBrowser(browser));
            WebDriver driver = browserAndDriverFactory.getDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            browserAndDriverFactory.navigateTo(url);
            loginPage = new LoginPage(driver);
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            // Log the successful initialization
            LoggerFactory.info("Successfully initialized browser");

        } catch (Exception e) {
            // Log any exceptions that occur during initialization
            ExceptionHandler.handleException("Failed to initialize browser or navigate to URL: ", e);
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            // Quit the WebDriver instance
            BrowserAndDriverFactory.getInstance().quitDriver();
            // Log the successful teardown
            LoggerFactory.info("Browser closed successfully.");

        } catch (Exception e) {
            // Log any exceptions that occur during teardown
            LoggerFactory.error("Failed to close the browser");
        } finally {
            ThreadContext.clearAll();
        }
    }
}
