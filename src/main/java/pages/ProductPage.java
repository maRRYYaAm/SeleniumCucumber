package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By sortByDropdown = By.id("sorter");
    By productNames = By.cssSelector(".product-item-name a");
    By productPrices = By.cssSelector(".price");
    By searchBox = By.id("search");
    By productLinks = By.cssSelector(".product-item-link");

    public ProductPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void searchProduct(String productTitle) {
        WebElement searchInput = driver.findElement(searchBox);
        searchInput.clear();
        searchInput.sendKeys(productTitle);
        searchInput.sendKeys(Keys.ENTER);
    }

    public boolean isProductVisible(String productTitle) {
        List<WebElement> results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productLinks));
        for (WebElement el : results) {
            if (el.getText().toLowerCase().contains(productTitle.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


//    public void sortByLowToHigh() {
//        WebElement dropdown = driver.findElement(sortByDropdown);
//        Select select = new Select(dropdown);
//        select.selectByVisibleText("Price");
//    }
    public void sortByLowToHigh() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Select 'Price' from dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Price");

        // Wait until product prices stabilize
        List<Double> beforePrices = getProductPrices();
        wait.until(driver -> {
            List<Double> currentPrices = getProductPrices();
            return !currentPrices.isEmpty() && currentPrices.size() == beforePrices.size();
        });

        // Optional: validate ascending order inside this method
        List<Double> actualPrices = getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals("Products are not sorted by ascending price", expectedPrices, actualPrices);
    }


    public void sortByHighToLow() {
        WebElement dropdown = driver.findElement(sortByDropdown);
        Select select = new Select(dropdown);

        List<Double> beforePrices = getProductPrices();
        select.selectByVisibleText("Price");

        WebElement sortDirection = driver.findElement(By.cssSelector(".sorter-action"));
        sortDirection.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            List<Double> afterPrices = getProductPrices();
            return !afterPrices.equals(beforePrices);
        });
    }

//    public List<WebElement> getProductNames() {
//        return driver.findElements(productNames);
//    }

    public List<Double> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrices);
        List<Double> prices = new ArrayList<>();

        for (WebElement el : priceElements) {
            String priceText = el.getText().replace("$", "").replace(",", "").trim();
            if (!priceText.isEmpty()) {
                prices.add(Double.parseDouble(priceText));
            }
        }

        return prices;
    }

    public void openProductPage(String productName) {
        searchProduct(productName);
        WebElement productLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'product-item-link') and contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + productName.toLowerCase() + "')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
        productLink.click();
    }

    public void logAllProductTitles() {
        List<WebElement> titles = driver.findElements(By.cssSelector(".product-item-link"));
        for (WebElement title : titles) {
            System.out.println("Found Product: " + title.getText());
        }
    }

    public void selectSizeAndColor(String size, String color) {
        List<WebElement> sizeOptions = driver.findElements(By.cssSelector("div.swatch-attribute.size div.swatch-option"));
        boolean sizeSelected = false;
        for (WebElement option : sizeOptions) {
            String label = option.getAttribute("option-label");
            if (label != null && label.equalsIgnoreCase(size)) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                sizeSelected = true;
                break;
            }
        }
        if (!sizeSelected) throw new RuntimeException("Size '" + size + "' not available");

        List<WebElement> colorOptions = driver.findElements(By.cssSelector("div.swatch-attribute.color div.swatch-option"));
        boolean colorSelected = false;
        for (WebElement option : colorOptions) {
            String label = option.getAttribute("option-label");
            if (label != null && label.equalsIgnoreCase(color)) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                colorSelected = true;
                break;
            }
        }
        if (!colorSelected) throw new RuntimeException("Color '" + color + "' not available");
    }

    public void addToCart(String size, String color) {
        selectSizeAndColor(size, color);
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("product-addtocart-button")));
        addToCartBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-success")));
    }
}
