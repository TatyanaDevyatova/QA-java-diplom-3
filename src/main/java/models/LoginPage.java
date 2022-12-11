package models;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginPage {
    public static final String LOGIN_PAGE_PATH = "https://stellarburgers.nomoreparties.site/login";

    private final WebDriver driver;
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By signInButton = By.xpath(".//form[contains(@class,'Auth_form__3qKeq')]/button");
    private final By header = By.xpath(".//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("enter email")
    public void setEmail(String email) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(emailInput)).sendKeys(email);
    }

    @Step("enter password")
    public void setPassword(String password) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
    }

    @Step("click 'sign in button' and redirect to main page")
    public MainPage clickSignInButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(signInButton));
        return new MainPage(driver);
    }

    @Step("enter all data, login user and redirect to main page")
    public MainPage fillFieldsAndSignIn(String email, String password) {
        setEmail(email);
        setPassword(password);
        return clickSignInButton();
    }

    @Step("assert redirecting to login page")
    public void assertRedirectToLoginPage() {
        assertEquals("redirected to wrong page", LOGIN_PAGE_PATH, driver.getCurrentUrl());
        assertNotNull(new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(header)));
    }
}