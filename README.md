A Java implementation of a **Selenium Project** using the developing Factory Pattern to manage the WebDrivers, containing Utility Functions and using **TestNG** to execute the tests.

### The Browser Enum

This enum defines how each browser is initialized or instantiated.

```java
public abstract WebDriver initialize(DesiredCapabilities capabilities);
```

To handle a new browser, just create a new **Browser Enum field** and implement the **initialize** method that return a WebDriver.

```java
CHROME {
    @Override
		public WebDriver initialize(DesiredCapabilities capabilities) {
			synchronized (BrowserProvider.class) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.merge(capabilities);
				return new ChromeDriver(options);
			}
		}
	}
```

## Instantiating a WebDriver

After implementing the initialize logic behind the scenes, in order to help us to instantiate a new WebDriver, just call the method **createDriver** of the class **BrowserProvider** passing a **Browser Enum**.

```java
WebDriver driver = BrowserProvider.createDriver(Browser.CHROME);
```

## Utility Classes

Utility Classes are very useful to provide reusable methods. The main utility class in this project is the **SeleniumUtils** one. This class intends to provide methods to help us to find elements, wait for certain conditions, manage checkboxes, radio buttons and more.

```java
SeleniumUtils.findElement(driver, xpath);
SeleniumUtils.findElements(driver, xpath);
SeleniumUtils.waitForElement(driver, xpath);
SeleniumUtils.waitForElementToBeClickable(driver, xpath);
SeleniumUtils.waitForElementToBeVisible(driver, xpath);
SeleniumUtils.waitForElementToBeInvisible(driver, xpath);
```


