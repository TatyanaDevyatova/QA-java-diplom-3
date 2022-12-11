package models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    // кнопка "Личный кабинет"
    private final By profileButton = By.xpath(".//nav[@class='AppHeader_header__nav__g5hnF']/a[@class='AppHeader_header__link__3D_hX']/p[@class='AppHeader_header__linkText__3q_va ml-2']");
    // кнопка "Войти в аккаунт"
    private final By signInButton = By.xpath(".//section[@class='BurgerConstructor_basket__29Cd7 mt-25 ']//button[@class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickProfileButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(profileButton));
        driver.findElement(profileButton).click();
    }

    public void clickSignInButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(signInButton));
        driver.findElement(signInButton).click();
    }
}
