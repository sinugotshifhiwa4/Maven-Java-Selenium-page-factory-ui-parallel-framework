package com.adactin.pages;

import com.adactin.utils.LoggerFactory;
import com.adactin.utils.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends TestBase {

    WebDriver driver;

    @FindBy(css = "#username")
    private WebElement usernameTextBox;

    @FindBy(css = "#password")
    private WebElement passwordTextBox;

    @FindBy(css = "#login")
    private WebElement loginButton;

    @FindBy(css = "img[class='logo']")
    private WebElement adactinHotelLogo;

    @FindBy(css = "div[class='auth_error']")
    private WebElement errorMessage;

    @FindBy(css = "#username_show")
    private WebElement welcomeText;




    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillUsername(String username){
        clearElement(usernameTextBox, "UsernameTextBox");
        fillElement(usernameTextBox, "UsernameTextBox", username);
    }

    public void fillPassword(String password){
        clearElement(passwordTextBox, "passwordTextBox");
        fillElement(passwordTextBox, "passwordTextBox", password);
    }

    public void clickLoginButton(){
        clickElement(loginButton, "loginButton");
    }

    public void loginToAdactinHotel(String username, String password){
        fillUsername(username);
        fillPassword(password);
        clickLoginButton();
    }

    public void verifyAdactinHotelLogoPresent(){
        wait.until(ExpectedConditions.visibilityOf(getErrorMessageElement()));
        isElementPresent(adactinHotelLogo, "AdactinHotelLogo");
        loginPage.verifyAdactinHotelLogoPresent();
    }

    public void verifyErrorMessagePresent(){
        isElementPresent(errorMessage, "errorMessagePresent");
    }

    public void verifyErrorMessageAbsent(){
        isElementAbsent(errorMessage, "errorMessageAbsent");
    }

    public void verifyWelcomeTextIsVisible(){
        isElementPresent(welcomeText, "welcomeText");
    }

    public WebElement getErrorMessageElement(){
        return errorMessage;
    }

    public WebElement getWelcomeTextElement(){
        return welcomeText;
    }
}
