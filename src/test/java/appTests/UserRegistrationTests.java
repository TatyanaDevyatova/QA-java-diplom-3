package appTests;

import com.github.javafaker.Faker;
import driverFactory.DriverFactory;
import driverFactory.DriverType;
import models.LoginPage;
import models.MainPage;
import models.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static utils.TestData.REGISTER_PAGE_PATH;

@RunWith(Parameterized.class)
public class RegisterTests {
    private WebDriver driver;
    private final DriverType driverType;

    public RegisterTests(DriverType driverType) {
        this.driverType = driverType;
    }

    String expectedErrorMessage = "Некорректный пароль";
    String expectedText = "Оформить заказ";

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver(driverType);
        driver.get(REGISTER_PAGE_PATH);
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
    public void shouldBeSuccessRegistrationWithValidData() {
        // Arrange
        String name = Faker.instance().name().username();
        String email = Faker.instance().internet().emailAddress();
        String password = Faker.instance().internet().password();

        RegisterPage registerPage = new RegisterPage(driver);

        // Act
        LoginPage loginPage = registerPage.fillRegisterFieldsAndGetLoginPage(name, email, password);
        MainPage actualMainPage = loginPage.fillFieldsAndSignIn(email, password);
        String actualText = actualMainPage.getActualText();

        // Assert
        assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldBeAppearErrorMessageWithShortPassword() {
        // Arrange
        RegisterPage registerPage = new RegisterPage(driver);

        // Act
        registerPage.fillRegisterFields(Faker.instance().name().username(), Faker.instance().internet().emailAddress(), Faker.instance().internet().password(1, 5));
        String actualErrorMessage = registerPage.getErrorMessage();

        // Assert
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}
