package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By cartIcon = By.cssSelector("a.action.showcart");
    private final By cartCountLocator = By.cssSelector("span.counter.qty > span.counter-number");
    private final By cartTotalLocator = By.cssSelector(".subtotal .price");
    private final By cartItemTitleLocator = By.cssSelector(".product-item-name a");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public int getCartItemCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 🔹 Wait for cart icon to be present
        WebElement cartIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.action.showcart")));

        // 🔹 Scroll into view just to be safe
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartIcon);

        // 🔹 Try clicking safely
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartIcon);
        }

        // 🔹 Wait for the cart count to be visible and return
        WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span.counter-number")));
        return Integer.parseInt(cartCount.getText().trim());
    }

    public double getCartTotal() {
        WebElement totalElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartTotalLocator)
        );
        String totalText = totalElement.getText().replace("$", "").trim();
        return Double.parseDouble(totalText);
    }

    public String getCartProductTitle() {
        WebElement titleElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartItemTitleLocator)
        );
        return titleElement.getText().trim();
    }
}
