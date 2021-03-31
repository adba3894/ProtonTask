package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class containing many static methods to help testing.
 */
public class Utils {

    private static final int DEFAULT_WAIT_TIME = 20;
    private static final Properties prop = new Properties();

    /**
     * Try to find an element in the page by xpath. If the element is not found,
     * null is returned.
     *
     * @param driver
     * @param element
     * @return
     */
    public static List<WebElement> findElements(WebDriver driver, By element) {
        try {
            return driver.findElements(element);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Try to find an element in the page by xpath. If the element is not found,
     * null is returned.
     *
     * @param driver
     * @param element
     * @return
     */
    public static WebElement findElement(WebDriver driver, By element) {
        try {
            return driver.findElement(element);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Navigates to specific path in base url
     *
     * @param driver
     * @param url
     * @return
     */
    public static void navigateTo(WebDriver driver, String url) throws FileNotFoundException {
        String baseUrl = prop.getProperty("baseUrl");
        driver.navigate().to(baseUrl + url);
    }

    /**
     * Check if element displayed
     *
     * @param driver
     * @param by
     * @return
     */
    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Try to find elements in the page by xpath. If the elements are not found,
     * null is returned.
     *
     * @param driver
     * @param elementToPress
     * @param elementToAppear
     * @return
     */
    public static WebElement elementAppearsAfterAnotherClicked(WebDriver driver, By elementToPress, By elementToAppear) {
        waitForElementToBeVisible(driver, elementToPress);
        waitForElementToBeClickable(driver, elementToPress).click();
        return waitForElementToBeVisible(driver, elementToAppear);
    }

    /**
     * Wait for an element to be visible on the screen, given a maximum time to wait
     *
     * @param driver
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, By element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Wait for an element to be visible on the screen, using the DEFAULT_WAIT_TIME
     *
     * @param driver
     * @param element
     * @return
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, By element) {
        return waitForElementToBeVisible(driver, element, DEFAULT_WAIT_TIME);
    }

    /**
     * Wait for an element to be present in the DOM, given a maximum time to wait
     *
     * @param driver
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public static WebElement waitForElement(WebDriver driver, By element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(element));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Wait for an element to be present in the DOM, using the DEFAULT_WAIT_TIME
     *
     * @param driver
     * @param element
     * @return
     */
    public static WebElement waitForElement(WebDriver driver, By element) {
        return waitForElement(driver, element, DEFAULT_WAIT_TIME);
    }

    /**
     * Wait for at least one element to be present in the DOM, given a maximum time to wait
     *
     * @param driver
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public static List<WebElement> waitForElements(WebDriver driver, By element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Wait for at least one element to be present in the DOM, using the DEFAULT_WAIT_TIME
     *
     * @param driver
     * @param element
     * @return
     */
    public static List<WebElement> waitForElements(WebDriver driver, By element) {
        return waitForElements(driver, element, DEFAULT_WAIT_TIME);
    }

    /**
     * Wait for an element to be clickable, given a maximum time to wait
     *
     * @param driver
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, By element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Wait for an element to be clickable, using the DEFAULT_WAIT_TIME
     *
     * @param driver
     * @param element
     * @return
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, By element) {
        return waitForElementToBeClickable(driver, element, DEFAULT_WAIT_TIME);
    }

    /**
     * Wait for a certain condition to occur for up to DEFAULT_WAIT_TIME
     * <p>
     * Usage: waitUntil(driver, ExpectedConditions.elementToBeClickable(By.xpath("/input[@id='q']")))
     *
     * @param condition
     * @return
     */
    public <T> T waitUntil(WebDriver driver, Function<? super WebDriver, T> condition) {
        return waitUntil(driver, condition, DEFAULT_WAIT_TIME);
    }

    /**
     * Wait for a certain condition to occur for up to a given time in seconds
     *
     * @param condition
     * @param timeOutInSeconds
     * @return
     */
    public <T> T waitUntil(WebDriver driver, Function<? super WebDriver, T> condition, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(condition);
    }


    /**
     * Load config.properties file
     *
     * @return
     */

    public Properties loadPropertiesFile() throws FileNotFoundException {
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return prop;
    }
}
