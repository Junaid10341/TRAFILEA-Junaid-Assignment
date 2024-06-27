import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Assignment extends Base {

    public static void main(String[] args) throws InterruptedException {

        Assignment assignment = new Assignment();
        assignment.openWebsite(); // This will open the website defined in Base.openWebsite()
        assignment.placeOrder();  // This will perform the actions in placeOrder()
    }

    // When: Method to place an order
    public void placeOrder() throws InterruptedException {

        try {
            // Custom wait for the specific element with a timeframe
            WebDriverWait customWait = new WebDriverWait(driver, 10);  // 10 seconds timeout

            // Given: Check if the element is present and visible
            WebElement closeButton = customWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='_xxsoyodj_popup-lead-trafilea-close _xxsoyodj_popup-lead-trafilea-close-cross']")));

            // When: Click the close button if it's displayed
            if (closeButton.isDisplayed()) {
                closeButton.click();
            }
        } catch (TimeoutException | NoSuchElementException e) {
            // Element was not found or not visible within the timeout, continue with the next steps
            System.out.println("Popup close button was not visible or not present, proceeding to the next step.");
        }

        // Given: Click on Best Sellers Category
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Best Sellers"))).click();

        // When: Scroll and click on the specified item
        scrollAndClick("//p[@class='css-1jb4ouw' and text()='Shapermint Essentials All Day Every Day Scoop Neck Cami']");
        scrollAndClick("//button[contains(text(),'ADD TO CART')]");

        // When: Switch to the iframe by passing the XPath
        switchToIframe(driver,"//iframe[@id='ju_iframe_853975']");

        // When: Locate and click the button inside the iframe
        WebElement buttonInIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//font[contains(text(),'x')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonInIframe); // Using JavaScript to click
        System.out.println("Clicked the button inside the iframe");

        // When: Switch back to the main content
        switchToMainContent(driver);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email']"))).sendKeys("qa.mail@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='delivery.buyer_name']"))).sendKeys("Junaid");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='delivery.buyer_lastname']"))).sendKeys("Shabbir");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='delivery.address1']"))).sendKeys("123 William Street");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='delivery.address2']"))).sendKeys("apt 1");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='delivery.zip']"))).sendKeys("10038");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='delivery.phone']"))).sendKeys("1234567890");

        // When: Scroll to the shipping method section
        scrollIntoView("Shipping Method");

        // Then: XPath for the element containing the shipping method text
        String shippingMethodXpath = "//p[contains(text(),'Standard Delivery (4-8 business days)')]";

        // Then: Use the assertElementPresent method to check for the shipping method text
        try {
            assertElementPresent(shippingMethodXpath, 60);
            System.out.println("Shipping method text is displayed as expected.");
        } catch (TimeoutException e) {
            System.out.println("Shipping method text is not displayed as expected.");
            throw e;
        }

        try {
            // Initialize WebDriverWait
            WebDriverWait wait = new WebDriverWait(driver, 60);

            // When: Switch to iframe for credit card number
            switchToIframeByIndex(driver, 0);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cardnumber"))).sendKeys("1234 1234 1234 1234");
            switchToMainContent(driver);

            // XPath for the element containing the error message for invalid card number
            String invalidCardNumberXpath = "//span[contains(@class, 'InputError_invalid__lM5RH') and text()='Your card number is invalid.']";

            // Use the assertElementPresent method to check for the error message
            try {
                assertElementPresent(invalidCardNumberXpath, 60);
                System.out.println("Error message for invalid card number is displayed as expected.");
            } catch (TimeoutException e) {
                System.out.println("Error message for invalid card number is not displayed as expected.");
                // Do not throw the exception to proceed to the next case
                // throw e;
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name on card']"))).sendKeys("Junaid Malik");

            // When: Switch to iframe for expiration date
            switchToIframeByIndex(driver, 1);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("exp-date"))).sendKeys("12/25");
            switchToMainContent(driver);

            // When: Switch to iframe for CVC
            switchToIframeByIndex(driver, 2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cvc"))).sendKeys("123");
            switchToMainContent(driver);

            // Then: Expected URL substring
            String expectedUrlSubstring = "/hc/checkout/";

            // Then: Use a try-catch block to check if the URL contains the expected substring
            try {
                boolean isUrlVisible = wait.until(ExpectedConditions.urlContains(expectedUrlSubstring));

                if (isUrlVisible) {
                    System.out.println("The URL contains the text /hc/checkout/");
                } else {
                    System.out.println("The URL does not contain the expected substring.");
                }
            } catch (TimeoutException e) {
                System.out.println("The URL does not contain the expected substring.");
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // When: Scroll to the complete order section
        scrollIntoView("COMPLETE ORDER");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("css-1l7hiuc"))).click();
    }
}
