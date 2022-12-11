package models;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {
    public static final String FORGOT_PASSWORD_PAGE_PATH = "https://stellarburgers.nomoreparties.site/forgot-password";

    private final WebDriver driver;
    private final By signInLink = By.xpath(".//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("click 'sign in link' and redirect to login page")
    public LoginPage clickSignInLink() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(signInLink));
        driver.findElement(signInLink).click();
        return new LoginPage(driver);
    }
}