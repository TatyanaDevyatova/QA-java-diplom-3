package models;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    public static final String REGISTER_PAGE_PATH = "https://stellarburgers.nomoreparties.site/register";

    private final WebDriver driver;
    private final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By errorMessage = By.xpath(".//p[@class='input__error text_type_main-default']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By signInLink = By.xpath(".//div[@class='Auth_login__3hAey']//a[@class='Auth_link__1fOlj']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("enter name")
    public void setName(String name) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(nameInput)).sendKeys(name);
    }

    @Step("enter email")
    public void setEmail(String email) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(emailInput)).sendKeys(email);
    }

    @Step("enter password")
    public void setPassword(String password) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
    }

    @Step("click 'register button'")
    public void clickRegisterButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    @Step("await loading login page")
    public LoginPage waitForChangingPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(registerButton));
        return new LoginPage(driver);
    }

    @Step("enter all data, register user and redirect to login page")
    public LoginPage fillRegisterFieldsAndGetLoginPage(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
        return waitForChangingPage();
    }

    @Step("enter all data and register user")
    public void fillRegisterFields(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @Step("get error message")
    public String getErrorMessage() {
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    @Step("click 'sign in link' and redirect to login page")
    public LoginPage clickSignInLink() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(signInLink)).click();
        return new LoginPage(driver);
    }
}