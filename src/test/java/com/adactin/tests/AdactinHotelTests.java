package com.adactin.tests;

import com.adactin.components.ConfigProperties;
import com.adactin.components.EncryptionUtil;
import com.adactin.utils.LoggerFactory;
import com.adactin.utils.TestBase;
import org.testng.annotations.Test;

public class AdactinHotelTests extends TestBase {


    @Test
    public void loginWithValidCredentials(){
        loginPage.loginToAdactinHotel(
                EncryptionUtil.decrypt(ConfigProperties.getPropertyByKey("USER")),
                EncryptionUtil.decrypt(ConfigProperties.getPropertyByKey("PASSWORD")));
        loginPage.verifyErrorMessageAbsent();
        loginPage.verifyWelcomeTextIsVisible();
        LoggerFactory.info("Login successful");
    }

    @Test
    public void loginWithInvalidCredentials(){
        loginPage.loginToAdactinHotel(
                EncryptionUtil.decrypt(ConfigProperties.getPropertyByKey("INVALID_USER")),
                EncryptionUtil.decrypt(ConfigProperties.getPropertyByKey("INVALID_PASSWORD")));
        loginPage.verifyErrorMessagePresent();
        LoggerFactory.info("Login failed. Error message displayed successfully");
    }

    @Test
    public void loginWithInvalidCredentials2(){
        loginPage.loginToAdactinHotel(
                "lunah",
                "passcode");
        loginPage.verifyErrorMessagePresent();
        LoggerFactory.info("Login failed. Error message displayed successfully");
    }

    @Test
    public void loginWithInvalidCredentials3(){
        loginPage.loginToAdactinHotel(
                "admin123",
                "passcode1");
        loginPage.verifyErrorMessagePresent();
        LoggerFactory.info("Login failed. Error message displayed successfully");
    }
}
