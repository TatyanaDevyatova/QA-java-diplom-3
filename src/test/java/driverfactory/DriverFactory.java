package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static WebDriver getDriver(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                return new ChromeDriver();
            case YANDEX:
                OperaOptions options = new OperaOptions();
                options.setBinary("C:\\Users\\leel\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
                return new OperaDriver(options);
        }
        return new ChromeDriver();
    }
}