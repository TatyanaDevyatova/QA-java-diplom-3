package models;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProfilePage {
    public static final String PROFILE_PAGE_PATH = "https://stellarburgers.nomoreparties.site/account";

    private final WebDriver driver;
    private final By constructorLink = By.xpath(".//nav//p[text()='Конструктор']");
    private final By stellarBurgersLink = By.xpath(".//nav//div[@class='AppHeader_header__logo__2D0X2']");
    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By accountText = By.xpath(".//p[contains(@class,'Account_text__fZAIn')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("click on 'constructor link' and redirect to main page")
    public MainPage clickConstructorLink() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(constructorLink)).click();
        return new MainPage(driver);
    }

    @Step("click on 'stellar-burgers link' and redirect to main page")
    public MainPage clickStellarBurgersLink() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(stellarBurgersLink)).click();
        return new MainPage(driver);
    }

    @Step("assert redirecting to profile page")
    public void assertRedirectToProfilePage() {
        assertEquals("redirected to wrong page", PROFILE_PAGE_PATH, driver.getCurrentUrl());
        assertNotNull(new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(logoutButton)));
    }

    @Step("get text")
    public String getAccountText() {
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(accountText)).getText();
    }

    @Step("click on 'logout button' and redirect to login page")
    public LoginPage clickLogoutButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(logoutButton));
        return new LoginPage(driver);
    }
}