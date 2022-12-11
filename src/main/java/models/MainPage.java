package models;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainPage {
    public static final String MAIN_PAGE_PATH = "https://stellarburgers.nomoreparties.site/";

    private final WebDriver driver;
    private final By profileLink = By.xpath(".//nav//p[text()='Личный Кабинет']");
    private final By signInButton = By.xpath(".//section[contains(@class,'BurgerConstructor_basket__29Cd7')]//button");
    private final By header = By.xpath(".//h1[text()='Соберите бургер']");
    private final By bunTab = By.xpath(".//span[text()='Булки']");
    private final By sauceTab = By.xpath(".//span[text()='Соусы']");
    private final By fillingTab = By.xpath(".//span[text()='Начинки']");
    private final By bunTabParent = By.xpath(".//span[text()='Булки']/parent::div");
    private final By sauceTabParent = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingTabParent = By.xpath(".//span[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("click on 'profile link' by unauthorized user and redirect to login page")
    public LoginPage clickProfileLinkByUnauthorizedUser() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(profileLink)).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(signInButton));
        return new LoginPage(driver);
    }

    @Step("click on 'profile link' by authorized user and redirect to profile page")
    public ProfilePage clickProfileLinkByAuthorizedUser() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(profileLink)).click();
        return new ProfilePage(driver);
    }

    @Step("click on 'sign in button' and redirect to login page")
    public LoginPage clickSignInButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(signInButton));
        return new LoginPage(driver);
    }

    @Step("get button's text")
    public String getActualButtonText() {
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(signInButton)).getText();
    }

    @Step("assert redirecting to main page")
    public void assertRedirectToMainPage() {
        assertEquals("redirected to wrong page", MAIN_PAGE_PATH, driver.getCurrentUrl());
        assertNotNull(new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(header)));
    }

    @Step("click on 'bun tab' and get property's text")
    public String clickBunTab() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(bunTab)).click();
        return driver.findElement(bunTabParent).getAttribute("class");
    }

    @Step("click on 'sauce tab' and get property's text")
    public String clickSauceTab() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(sauceTab)).click();
        return driver.findElement(sauceTabParent).getAttribute("class");
    }

    @Step("click on 'filling tab' and get property's text")
    public String clickFillingTab() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(fillingTab)).click();
        return driver.findElement(fillingTabParent).getAttribute("class");
    }
}