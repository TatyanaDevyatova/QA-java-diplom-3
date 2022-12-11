package appTests;

import com.github.javafaker.Faker;
import driverFactory.DriverFactory;
import driverFactory.DriverType;
import io.qameta.allure.junit4.DisplayName;
import models.LoginPage;
import models.MainPage;
import models.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static models.RegisterPage.REGISTER_PAGE_PATH;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserRegistrationTests {
    private final DriverType driverType;
    private WebDriver driver;
    private RegisterPage registerPage;

    public UserRegistrationTests(DriverType driverType) {
        this.driverType = driverType;
    }

    private final String expectedErrorMessage = "Некорректный пароль";
    private final String expectedText = "Оформить заказ";

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver(driverType);
        driver.get(REGISTER_PAGE_PATH);
        this.registerPage = new RegisterPage(driver);
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
    @DisplayName("success user's registration with valid data")
    public void shouldBeSuccessRegistrationWithValidData() {
        // Arrange
        String name = Faker.instance().name().username();
        String email = Faker.instance().internet().emailAddress();
        String password = Faker.instance().internet().password();

        // Act
        LoginPage loginPage = registerPage.fillRegisterFieldsAndGetLoginPage(name, email, password);

        // Assert
        loginPage.assertRedirectToLoginPage();
        MainPage actualMainPage = loginPage.fillFieldsAndSignIn(email, password);
        String actualText = actualMainPage.getActualButtonText();
        assertEquals(expectedText, actualText);
    }

    @Test
    @DisplayName("failed user's registration with too short password")
    public void shouldBeErrorMessageWithShortPassword() {
        // Arrange & Act
        registerPage.fillRegisterFields(Faker.instance().name().username(), Faker.instance().internet().emailAddress(), Faker.instance().internet().password(1, 5));

        // Assert
        String actualErrorMessage = registerPage.getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}