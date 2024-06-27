import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

    public static WebDriver driver;
    public static WebDriverWait wait;

    // Data: Constructor to initialize the ChromeDriver and open the browser
    public Base() {
        try {
            // Specify the path to chromedriver.exe
            System.setProperty("webdriver.chrome.driver", "D:\\Assignment\\drivers\\chromedriver.exe");

            // Initialize ChromeDriver
            driver = new ChromeDriver();
            System.out.println("ChromeDriver initialized successfully.");

            // Maximize the browser window
            driver.manage().window().maximize();

            // Initialize WebDriverWait with a timeout of 60 seconds
            wait = new WebDriverWait(driver, 60);
            System.out.println("WebDriverWait initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing WebDriver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Given: Method to open the website
    public void openWebsite() {
        try {
            // Open Website
            driver.get("https://shapermint.com");
            System.out.println("Opened website successfully.");
        } catch (Exception e) {
            System.err.println("Error opening website: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper Methods

    // When: Method to scroll and click on an element by its XPath
    public void scrollAndClick(String xpath) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element); // Using JavaScript to click
            System.out.println("Clicked on element with XPath: " + xpath);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to scroll and click: " + e.getMessage());
        }
    }

    public void scrollIntoView(String expectedText) {
        try {
            // Wait for up to 60 seconds for the element to be present in the DOM
            WebDriverWait wait = new WebDriverWait(driver, 60);
            By byText = By.xpath("//*[text()='" + expectedText + "']");
            wait.until(ExpectedConditions.presenceOfElementLocated(byText));

            WebElement element = driver.findElement(byText);

            // Scroll to the element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            // Verify the element text
            if (element != null && element.isDisplayed()) {
                String actualText = element.getText();
                System.out.println("Actual Text: " + actualText);  // Print actual text for debugging
                if (actualText.equals(expectedText)) {
                    System.out.println("Element with text '" + expectedText + "' found and text verified after scrolling.");
                } else {
                    throw new AssertionError("Element with text '" + expectedText + "' found, but text verification failed. Actual text: " + actualText);
                }
            } else {
                throw new NoSuchElementException("Element with text '" + expectedText + "' not found or not displayed after scrolling.");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Element with text '" + expectedText + "' not found after scrolling.");
            throw e;
        }
    }

    // Helper Methods to switch to and from iframes
    public static void switchToIframe(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(xpath)));
    }

    public static void switchToMainContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public static void switchToIframeByIndex(WebDriver driver, int index) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("(//iframe[contains(@name, 'privateStripeFrame')])[" + (index + 1) + "]")));
    }

    // Then: Method to assert element presence
    public void assertElementPresent(String xpath, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            WebElement element = customWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            if (element != null && element.isDisplayed()) {
                System.out.println("Element with XPath '" + xpath + "' is present and displayed.");
            } else {
                throw new AssertionError("Element with XPath '" + xpath + "' is not displayed.");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Element with XPath '" + xpath + "' is not present.");
            throw e;
        }
    }
}
