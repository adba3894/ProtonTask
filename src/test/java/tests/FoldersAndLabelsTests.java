package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import enums.Browser;
import factory.BrowserProvider;
import pages.FoldersAndLabelsPage;
import pages.LoginPage;
import utils.Utils;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Folders&Labels section
 */

@Test
public class FoldersAndLabelsTests {

    private Utils su = new Utils();
    private WebDriver driver;
    private FoldersAndLabelsPage foldersAndLabels;

    @BeforeSuite
    public void initalize() {
        driver = BrowserProvider.createDriver(Browser.CHROME);
        driver.manage().window().maximize();
        foldersAndLabels = new FoldersAndLabelsPage(driver);
    }

    @BeforeTest
    public void login() throws FileNotFoundException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login();
        Utils.navigateTo(driver, "/settings/labels");
    }

    @Test(priority = 1)
    public void validateFoldersAndLabelsPageElements() {
        foldersAndLabels.validateFoldersAndLabelsPageElements(driver);
    }

    @Test(priority = 2)
    public void inheritColorFromParentFolderAppearsAfterAddFolderIsActivated() {
        foldersAndLabels.inheritColorFromParentFolderAppearsAfterAddFolderIsActivated(driver);
    }

    @Test(priority = 3)
    public void validateAddFolderFields() {
        foldersAndLabels.validateAddFolderFields(driver);
    }

    @Test(priority = 4)
    public void validateAddLabelFields() {
        foldersAndLabels.validateAddLabelFields(driver);
    }

    @Test(priority = 5)
    public void createFolderEmptyFields() throws FileNotFoundException {
        foldersAndLabels.createFolderEmptyFields(driver);
    }


    @Test(priority = 6)
    public void createParentFolder() throws FileNotFoundException {
        foldersAndLabels.createFolder(driver, su.loadPropertiesFile().getProperty("folder1"),
                su.loadPropertiesFile().getProperty("noParentFolder"),
                su.loadPropertiesFile().getProperty("greenColorCode"));
    }

    @Test(priority = 7)
    public void createFolderUnderFolderAndValidateColorInherited() throws FileNotFoundException {
        foldersAndLabels.createFolderUnderParentFolderAndValidateColorInherited(driver, su.loadPropertiesFile().getProperty("folder2"),
                su.loadPropertiesFile().getProperty("folder1"));
    }

    @Test(priority = 8)
    public void validateFolderUnderParentFolder() throws FileNotFoundException {
        Boolean childFolderDisplayed = foldersAndLabels.validateFolderUnderFolder(driver, su.loadPropertiesFile().getProperty("folder1"), su.loadPropertiesFile().getProperty("folder2"));
        assertTrue(childFolderDisplayed);
    }

    @Test(priority = 9)
    public void createThirdFolder() throws FileNotFoundException {
        foldersAndLabels.createFolder(driver, su.loadPropertiesFile().getProperty("folder3"),
                su.loadPropertiesFile().getProperty("noParentFolder"),
                su.loadPropertiesFile().getProperty("redColorCode"));
    }

    @Test(priority = 10)
    public void editFolder() throws FileNotFoundException {
        foldersAndLabels.editFolder(driver, su.loadPropertiesFile().getProperty("folder3")
                , su.loadPropertiesFile().getProperty("folder1New")
                , su.loadPropertiesFile().getProperty("folder1"));
    }

    @Test(priority = 11)
    public void createLabelEmptyFields() throws FileNotFoundException {
        foldersAndLabels.createLabelEmptyFields(driver);
    }

    @Test(priority = 12)
    public void createLabel() throws FileNotFoundException {
        foldersAndLabels.createLabel(driver, su.loadPropertiesFile().getProperty("label1"), su.loadPropertiesFile().getProperty("greenColorCode"));
    }

    @Test(priority = 13)
    public void editLabel() throws FileNotFoundException {
        foldersAndLabels.editLabel(driver, su.loadPropertiesFile().getProperty("label1"), su.loadPropertiesFile().getProperty("label1New"));
    }

    @Test(priority = 14)
    public void deleteLabelsAndFolders() throws FileNotFoundException {
        foldersAndLabels.deleteAllLabelsFolders(driver);
    }

    @Test(priority = 15)
    public void validateAllItemsDeleted() throws FileNotFoundException {
        assertFalse(foldersAndLabels.validateLabelVisible(driver, su.loadPropertiesFile().getProperty("folder1")));
        assertFalse(foldersAndLabels.validateFolderVisible(driver, su.loadPropertiesFile().getProperty("folder2")));
        assertFalse(foldersAndLabels.validateFolderVisible(driver, su.loadPropertiesFile().getProperty("folder3")));
        assertFalse(foldersAndLabels.validateLabelVisible(driver, su.loadPropertiesFile().getProperty("label1")));
    }

    /**
     * This method will be executed at the end of the test.
     */
    @AfterSuite
    public void quitDriver() throws FileNotFoundException {
        driver.quit();
        driver = null;
    }
}
