package profiletests;

import driverfactory.DriverFactory;
import driverfactory.DriverType;
import io.qameta.allure.junit4.DisplayName;
import models.LoginPage;
import models.MainPage;
import models.ProfilePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static models.LoginPage.LOGIN_PAGE_PATH;
import static org.junit.Assert.assertEquals;
import static testdata.TestData.DEFAULT_EMAIL;
import static testdata.TestData.DEFAULT_PASSWORD;

@RunWith(Parameterized.class)
public class ProfileTests {
    private final DriverType driverType;
    private WebDriver driver;
    private MainPage mainPage;

    public ProfileTests(DriverType driverType) {
        this.driverType = driverType;
    }

    private final String expectedText = "В этом разделе вы можете изменить свои персональные данные";

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver(driverType);
        driver.get(LOGIN_PAGE_PATH);
        LoginPage loginPage = new LoginPage(driver);
        this.mainPage = loginPage.fillFieldsAndSignIn(DEFAULT_EMAIL, DEFAULT_PASSWORD);
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
    @DisplayName("redirecting authorized user to profile page")
    public void shouldBeRedirectToProfilePageWithProfileLink() {
        // Arrange & Act
        ProfilePage profilePage = mainPage.clickProfileLinkByAuthorizedUser();

        // Assert
        profilePage.assertRedirectToProfilePage();
        String actualText = profilePage.getAccountText();
        assertEquals(expectedText, actualText);
    }

    @Test
    @DisplayName("logout authorized user")
    public void shouldBeLogoutWithLogoutButton() {
        // Arrange
        ProfilePage profilePage = mainPage.clickProfileLinkByAuthorizedUser();

        // Act
        LoginPage loginPage = profilePage.clickLogoutButton();

        // Assert
        loginPage.assertRedirectToLoginPage();
    }

    @Test
    @DisplayName("redirecting from profile page to main page via 'constructor link'")
    public void shouldBeRedirectToMainPageWithConstructorLink() {
        // Arrange
        ProfilePage profilePage = mainPage.clickProfileLinkByAuthorizedUser();

        // Act
        MainPage mainPage = profilePage.clickConstructorLink();

        // Assert
        mainPage.assertRedirectToMainPage();
    }

    @Test
    @DisplayName("redirecting from profile page to main page via 'stellar-burgers link'")
    public void shouldBeRedirectToMainPageWithStellarBurgersLink() {
        // Arrange
        ProfilePage profilePage = mainPage.clickProfileLinkByAuthorizedUser();

        // Act
        MainPage mainPage = profilePage.clickStellarBurgersLink();

        // Assert
        mainPage.assertRedirectToMainPage();
    }
}