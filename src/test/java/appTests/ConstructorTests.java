package appTests;

import driverFactory.DriverFactory;
import driverFactory.DriverType;
import io.qameta.allure.junit4.DisplayName;
import models.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static models.MainPage.MAIN_PAGE_PATH;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ConstructorTests {
    private final DriverType driverType;
    private WebDriver driver;
    private MainPage mainPage;

    public ConstructorTests(DriverType driverType) {
        this.driverType = driverType;
    }

    private final String expectedText = "tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect";

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver(driverType);
        driver.get(MAIN_PAGE_PATH);
        this.mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Parameterized.Parameters
    public static Object[][] getDriverType() {
        return new Object[][]{
                {DriverType.CHROME},
                {DriverType.YANDEX},
        };
    }

    @Test
    @DisplayName("activating 'bun tab' after click on it")
    public void shouldBeActiveBunTabAfterClickOnIt() {
        // Arrange
        mainPage.clickSauceTab();

        // Act
        String actualText = mainPage.clickBunTab();

        // Assert
        assertEquals(expectedText, actualText);
    }

    @Test
    @DisplayName("activating 'sauce tab' after click on it")
    public void shouldBeActiveSauceTabAfterClickOnIt() {
        // Arrange & Act
        String actualText = mainPage.clickSauceTab();

        // Assert
        assertEquals(expectedText, actualText);
    }

    @Test
    @DisplayName("activating 'filling tab' after click on it")
    public void shouldBeActiveFillingTabAfterClickOnIt() {
        // Arrange & Act
        String actualText = mainPage.clickFillingTab();

        // Assert
        assertEquals(expectedText, actualText);
    }
}