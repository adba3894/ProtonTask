package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import factory.PageBase;
import utils.Utils;

/**
 * Elements of the Dashboard page
 */

public class DashboardPage extends PageBase {

    private WebDriver driver;
    public By searchMessagesBtn = By.xpath("//input[@placeholder='Search messages']");
    private By settingsField = By.xpath("//button[contains(text(),'Settings')]");

    /**
     * Constructor of the Dashboard page.
     *
     * @param driver
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Default constructor of the Dashboard page.
     */
    public DashboardPage() {
    }

    /**
     * Performs a validation to check if we are on the Dashboard page currently
     */
    public void validateIfWeOnDashboard() {
        Utils.waitForElementToBeVisible(driver, searchMessagesBtn);
    }

}
