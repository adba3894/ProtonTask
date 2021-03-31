package pages;

import factory.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utils;

import java.io.FileNotFoundException;
import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

/**
 * Elements of the Folders & Labels page
 */
public class FoldersAndLabelsPage extends PageBase {

    private final WebDriver driver;
    private final Utils su = new Utils();

    // Folders & labels page elements
    private final By foldersAndLabelsLabel = By.xpath("//h1[text()='Folders & labels']");
    private final By foldersLabel = By.xpath("//h2[text()='Folders']");
    private final By labelsLabel = By.xpath("//h2[text()='Labels']");
    private final By inheritedFromParentFolderLabel = By.xpath(".//div[text()='Inherited from parent folder']");

    // Common buttons
    private final By saveBtn = By.xpath("//button[text()='Save']");

    // Folders buttons
    private final By useFolderColorsBtn = By.xpath("//label[text()='Use folder colors']//parent::div//label[@class='toggle-label']//span");
    private final By inheritColorFromParentFolderBtn = By.xpath("//span[text()='Inherit color from parent folder']//parent::*//parent::*//label[@class='toggle-label']");
    private final By addFolderBtn = By.xpath("//button[text()='Add folder']");
    private final By folderNotificationBtn = By.xpath("//span[text()='Notification']//parent::*//parent::div//label[@class='toggle-label']");
    private final By folderColorBtn = By.xpath("//label[text()='Color']//parent::*//button");
    private final By folderCloseBtn = By.xpath("//h1[text()='Create folder']//parent::*//button[@title='Close modal']");
    private final By folderNameField = By.xpath("//input[@placeholder='Folder name']");
    private final By folderLocationDropDownList = By.xpath("//label[text()='Folder location']//parent::*//select");
    private final By createFolderLabel = By.xpath("//h1[text()='Create folder']");
    private final By deleteButtonPopup = By.xpath("//div[contains(text(),'Are you sure you want to delete this')]//..//..//..//..//button[text()='Delete']");

    // Labels buttons
    private final By addLabelBtn = By.xpath("//button[text()='Add label']");
    private final By sortBtn = By.xpath("//button[text()='Sort']");
    private final By labelCloseBtn = By.xpath("//h1[text()='Create label']//parent::*//button[@title='Close modal']");
    private final By createLabel = By.xpath("//h1[text()='Create label']");
    private final By labelField = By.xpath("//input[@placeholder='Label name']");

    public FoldersAndLabelsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void validateFoldersAndLabelsPageElements(WebDriver driver) {
        Utils.waitForElementToBeVisible(driver, foldersAndLabelsLabel);
        Utils.waitForElementToBeVisible(driver, foldersLabel);
        Utils.waitForElementToBeVisible(driver, useFolderColorsBtn);
        Utils.waitForElementToBeVisible(driver, labelsLabel);
        Utils.waitForElementToBeVisible(driver, addFolderBtn);
        Utils.waitForElementToBeVisible(driver, addLabelBtn);
        Utils.waitForElementToBeVisible(driver, sortBtn);
    }

    public void inheritColorFromParentFolderAppearsAfterAddFolderIsActivated(WebDriver driver) {
        disableInheritColorFromParentFolder(driver);
        Utils.elementAppearsAfterAnotherClicked(driver, useFolderColorsBtn, inheritColorFromParentFolderBtn);
    }

    public void validateAddFolderFields(WebDriver driver) {
        Utils.waitForElementToBeClickable(driver, addFolderBtn).click();
        Utils.waitForElementToBeVisible(driver, createFolderLabel);
        Utils.waitForElementToBeVisible(driver, folderNameField);
        Utils.waitForElementToBeVisible(driver, folderLocationDropDownList);
        Utils.waitForElementToBeVisible(driver, folderColorBtn);
        Utils.waitForElementToBeVisible(driver, folderNotificationBtn);
        Utils.waitForElementToBeVisible(driver, saveBtn);
        closeAddFolderWindow();
    }

    public void disableInheritColorFromParentFolder(WebDriver driver) {
        if (Utils.isElementPresent(driver, inheritColorFromParentFolderBtn)) {
            Utils.waitForElementToBeClickable(driver, useFolderColorsBtn).click();
        }
    }


    public void closeAddFolderWindow() {
        Utils.waitForElementToBeClickable(driver, folderCloseBtn).click();
    }

    public void closeAddLabelWindow() {
        Utils.waitForElementToBeClickable(driver, labelCloseBtn).click();
    }

    public void validateAddLabelFields(WebDriver driver) {
        Utils.waitForElementToBeClickable(driver, addLabelBtn).click();
        Utils.waitForElementToBeVisible(driver, createLabel);
        Utils.waitForElementToBeVisible(driver, labelField);
        Utils.waitForElementToBeVisible(driver, folderColorBtn);
        Utils.waitForElementToBeVisible(driver, saveBtn);
        closeAddLabelWindow();
    }

    public void createFolderEmptyFields(WebDriver driver) throws FileNotFoundException {
        Utils.waitForElementToBeVisible(driver, addFolderBtn).click();
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
        String message = driver.findElement(folderNameField).getAttribute("validationMessage");
        assertEquals(message, su.loadPropertiesFile().getProperty("html5WarningEmptyField"));
        closeAddFolderWindow();
    }

    public void createLabelEmptyFields(WebDriver driver) throws FileNotFoundException {
        Utils.waitForElementToBeVisible(driver, addLabelBtn).click();
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
        String message = driver.findElement(labelField).getAttribute("validationMessage");
        assertEquals(message, su.loadPropertiesFile().getProperty("html5WarningEmptyField"));
        closeAddLabelWindow();
    }

    public void createFolder(WebDriver driver, String folderNameToCreate, String folderLocation, String colorCode) {
        Utils.waitForElementToBeVisible(driver, addFolderBtn).click();
        Utils.waitForElementToBeVisible(driver, folderNameField).sendKeys(folderNameToCreate);
        Select dropdown = new Select(driver.findElement(folderLocationDropDownList));
        dropdown.selectByVisibleText(folderLocation);
        chooseColor(colorCode);
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
    }

    public void createFolderUnderParentFolderAndValidateColorInherited(WebDriver driver, String folderNameToCreate, String folderLocation) {
        Utils.waitForElementToBeVisible(driver, addFolderBtn).click();
        Utils.waitForElementToBeVisible(driver, folderNameField).sendKeys(folderNameToCreate);
        Select dropdown = new Select(driver.findElement(folderLocationDropDownList));
        dropdown.selectByVisibleText(folderLocation);
        Utils.waitForElementToBeVisible(driver, inheritedFromParentFolderLabel);
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
    }

    public void createLabel(WebDriver driver, String labelNameToCreate, String colorCode) {
        Utils.waitForElementToBeVisible(driver, addLabelBtn).click();
        Utils.waitForElementToBeVisible(driver, labelField).sendKeys(labelNameToCreate);
        chooseColor(colorCode);
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
        assertTrue(Utils.waitForElementToBeVisible(driver, getLabel(labelNameToCreate)).isDisplayed());
    }

    public Boolean validateLabelVisible(WebDriver driver, String createdLabelName) {
        return Utils.isElementPresent(driver, getLabel(createdLabelName));
    }

    public By getLabel(String createdLabelName) {
        return By.xpath(".//h2[text()='Labels']//parent::*//span[@title='" + createdLabelName + "']");
    }

    public Boolean validateFolderVisible(WebDriver driver, String createdFolderName) {
        return Utils.isElementPresent(driver, getFolder(createdFolderName));
    }

    public By getFolder(String createdFolderName) {
        return By.xpath(".//h2[text()='Folders']//parent::*//ul[@role='tree']//span[@title='" + createdFolderName + "']");
    }

    public void chooseColor(String colorCode) {
        Utils.waitForElementToBeVisible(driver, folderColorBtn).click();
        By color = By.xpath("//input[@value='" + colorCode + "']");
        driver.findElement(color).click();
    }

    public Boolean validateFolderUnderFolder(WebDriver driver, String parentFolder, String childFolder) {
        By element = By.xpath("//span[@title='" + parentFolder + "']//parent::*//parent::*//parent::*//following-sibling::ul//span[@title='" + childFolder + "']");
        return Utils.waitForElementToBeVisible(driver, element).isDisplayed();
    }

    public By folderOrLabelToEditBtn(String folderToBeEdited) {
        return By.xpath("//span[@title='" + folderToBeEdited + "']//..//..//..//button[text()='Edit']");
    }

    public void editFolder(WebDriver driver, String folderToBeEdited, String newFolderName, String folderLocation) {
        Utils.waitForElementToBeVisible(driver, folderOrLabelToEditBtn(folderToBeEdited)).click();
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(folderNameField)).perform();
        Utils.waitForElementToBeVisible(driver, folderNameField).sendKeys(newFolderName);
        Select dropdown = new Select(driver.findElement(folderLocationDropDownList));
        dropdown.selectByVisibleText(folderLocation);
        Utils.waitForElementToBeVisible(driver, inheritedFromParentFolderLabel);
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
        assertTrue(validateFolderUnderFolder(driver, folderLocation, newFolderName));
        assertTrue(validateFolderVisible(driver, newFolderName));
    }

    public void editLabel(WebDriver driver, String labelToBeEdited, String newLabelName) {
        Utils.waitForElementToBeVisible(driver, folderOrLabelToEditBtn(labelToBeEdited)).click();
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(labelField)).perform();
        Utils.waitForElementToBeVisible(driver, labelField).sendKeys(newLabelName);
        Utils.waitForElementToBeClickable(driver, saveBtn).click();
        assertTrue(validateLabelVisible(driver, newLabelName));
    }

    public void deleteAllLabelsFolders(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Utils.waitForElementToBeVisible(driver, foldersAndLabelsLabel);
        List<WebElement> actionsDropDown = driver.findElements(By.cssSelector("[data-test-id='dropdown:open']"));
        for (int i = actionsDropDown.size() - 1; i >= 0; i--) {
            wait.until(ExpectedConditions.elementToBeClickable(actionsDropDown.get(i)));
            try {
                actionsDropDown.get(i).click();
            } catch (Exception e) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", actionsDropDown.get(i));
            }
            By delete = (By.xpath("//*[text()='Delete']"));
            Utils.waitForElementToBeClickable(driver, delete).click();
            Utils.waitForElementToBeClickable(driver, deleteButtonPopup).click();
        }
    }
}
