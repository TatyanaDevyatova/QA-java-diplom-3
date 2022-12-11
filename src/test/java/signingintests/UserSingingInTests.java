package signingintests;

import driverfactory.DriverFactory;
import driverfactory.DriverType;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.ForgotPasswordPage;
import models.LoginPage;
import models.MainPage;
import models.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static models.ForgotPasswordPage.FORGOT_PASSWORD_PAGE_PATH;
import static models.MainPage.MAIN_PAGE_PATH;
import static models.RegisterPage.REGISTER_PAGE_PATH;
import static org.junit.Assert.assertEquals;
import static testdata.TestData.DEFAULT_EMAIL;
import static testdata.TestData.DEFAULT_PASSWORD;

@RunWith(Parameterized.class)
public class UserSingingInTests {
    private final DriverType driverType;
    private WebDriver driver;

    public UserSingingInTests(DriverType driverType) {
        this.driverType = driverType;
    }

    private final String expectedText = "Оформить заказ";

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver(driverType);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getDriverType() {
        return new Object[][]{
                {DriverType.CHROME},
                {DriverType.YANDEX},
        };
    }

    @Test
    @DisplayName("login via 'profile link'")
    public void shouldBeAbleLoginWithProfileLink() {
        // Arrange
        driver.get(MAIN_PAGE_PATH);
        MainPage mainPage = new MainPage(driver);

        // Act & Assert
        LoginPage loginPage = mainPage.clickProfileLinkByUnauthorizedUser();
        loginAndAssertResult(loginPage);
    }

    @Test
    @DisplayName("login via 'sign in button' on main page")
    public void shouldBeAbleLoginWithSignInButtonOnMainPage() {
        // Arrange
        driver.get(MAIN_PAGE_PATH);
        MainPage mainPage = new MainPage(driver);

        // Act & Assert
        LoginPage loginPage = mainPage.clickSignInButton();
        loginAndAssertResult(loginPage);
    }

    @Test
    @DisplayName("login via 'sign in link' on register page")
    public void shouldBeAbleLoginWithSignInLinkOnRegisterPage() {
        // Arrange
        driver.get(REGISTER_PAGE_PATH);
        RegisterPage registerPage = new RegisterPage(driver);

        // Act & Assert
        LoginPage loginPage = registerPage.clickSignInLink();
        loginAndAssertResult(loginPage);
    }

    @Test
    @DisplayName("login via 'sign in link' on forgot-password page")
    public void shouldBeAbleLoginWithSignInLinkOnForgotPasswordPage() {
        // Arrange
        driver.get(FORGOT_PASSWORD_PAGE_PATH);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        // Act & Assert
        LoginPage loginPage = forgotPasswordPage.clickSignInLink();
        loginAndAssertResult(loginPage);
    }

    @Step("user authorization and related checks")
    private void loginAndAssertResult(LoginPage loginPage) {
        loginPage.assertRedirectToLoginPage();
        MainPage actualMainPage = loginPage.fillFieldsAndSignIn(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        actualMainPage.assertRedirectToMainPage();
        String actualText = actualMainPage.getActualButtonText();
        assertEquals(expectedText, actualText);
    }
}