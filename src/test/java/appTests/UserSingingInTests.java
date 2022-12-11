package appTests;

import driverFactory.DriverFactory;
import driverFactory.DriverType;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static utils.TestData.*;

@RunWith(Parameterized.class)
public class SingInTests {
    private WebDriver driver;
    private final DriverType driverType;

    public SingInTests(DriverType driverType) {
        this.driverType = driverType;
    }

    String expectedText = "Оформить заказ";

        @Before
    public void setUp() {
        driver = DriverFactory.getDriver(driverType);
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
    public void shouldBeAbleLoginWithProfileLink() {
        // Arrange
        driver.get(MAIN_PAGE_PATH);
        MainPage mainPage = new MainPage(driver);

        // Act & Assert
        LoginPage loginPage = mainPage.clickProfileLink();
        loginAndAssertResult(loginPage);
    }

    @Test
    public void shouldBeAbleLoginWithSignInButtonOnMainPage() {
        // Arrange
        driver.get(MAIN_PAGE_PATH);
        MainPage mainPage = new MainPage(driver);

        // Act & Assert
        LoginPage loginPage = mainPage.clickSignInButton();
        loginAndAssertResult(loginPage);
    }

    @Test
    public void shouldBeAbleLoginWithSignInLinkOnRegisterPage() {
        // Arrange
        driver.get(REGISTER_PAGE_PATH);
        RegisterPage registerPage = new RegisterPage(driver);

        // Act & Assert
        LoginPage loginPage = registerPage.clickSignInLink();
        loginAndAssertResult(loginPage);
    }

    @Test
    public void shouldBeAbleLoginWithSignInLinkOnForgotPasswordPage() {
        // Arrange
        driver.get(FORGOT_PASSWORD_PAGE_PATH);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        // Act & Assert
        LoginPage loginPage = forgotPasswordPage.clickSignInLink();
        loginAndAssertResult(loginPage);
    }

    private void loginAndAssertResult(LoginPage loginPage){
        MainPage actualMainPage = loginPage.fillFieldsAndSignIn(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        String actualText = actualMainPage.getActualText();

        // Assert
        assertEquals(expectedText, actualText);
    }

}
