package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import factory.PageBase;
import utils.Utils;

import java.io.FileNotFoundException;

/**
 * Elements of the login page
 */

public class LoginPage extends PageBase {

    private WebDriver driver;
    private Utils su = new Utils();
    private final By userNameField = By.xpath("//*[@id='login']");
    private final By passwordField = By.xpath("//*[@id='password']");
    private final By submitField = By.xpath("//button[contains(text(),'Sign in')]");

    /**
     * Constructor of the Login page
     *
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Performs a login operation.
     */

    public void login() throws FileNotFoundException {
        String username = su.loadPropertiesFile().getProperty("username");
        String password = su.loadPropertiesFile().getProperty("password");
        Utils.navigateTo(driver, "/login");
        Utils.waitForElement(driver, userNameField).sendKeys(username);
        Utils.waitForElement(driver, passwordField).sendKeys(password);
        Utils.waitForElementToBeClickable(driver, submitField).click();
        new DashboardPage(driver).validateIfWeOnDashboard();
    }
}
