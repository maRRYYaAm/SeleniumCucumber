package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductPage;
import utils.BaseClass;
import utils.TestData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductSteps {

    WebDriver driver;
    ProductPage productPage;

    public ProductSteps() {
        this.driver = BaseClass.getDriver();
        this.productPage = new ProductPage(driver);
    }

    @Given("user is on the product listing page")
    public void user_is_on_product_page() {
        driver.get(TestData.BASE_URL + "gear/bags.html");
    }

    @When("user searches for product by title")
    public void user_searches_for_product_by_title() {
        productPage.searchProduct(TestData.PRODUCT_SEARCH_TERM);
    }

    @Then("searched product should be displayed")
    public void searched_product_should_be_displayed() {
        boolean visible = productPage.isProductVisible(TestData.PRODUCT_SEARCH_TERM);
        Assert.assertTrue("Product not found in search results!", visible);
    }

    @When("user selects sort by price: low to high")
    public void user_selects_sort_by_price_low_to_high() {
        productPage.sortByLowToHigh();
    }

    @Then("products should be displayed in ascending price order")
    public void verify_products_sorted_low_to_high() {
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(d -> {
            List<Double> prices = getVisiblePrices();
            return !prices.isEmpty() && prices.get(0).equals(Collections.min(prices));
        });

        List<Double> actualPrices = getVisiblePrices();
        List<Double> expected = new ArrayList<>(actualPrices);
        expected.sort(Double::compareTo);

        Assert.assertEquals("❌ Products are not sorted low to high", expected, actualPrices);
    }

    @When("user selects sort by price: high to low")
    public void user_selects_sort_by_price_high_to_low() {
        productPage.sortByHighToLow();
    }

    @Then("products should be displayed in descending price order")
    public void verify_products_sorted_high_to_low() {
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(d -> {
            List<Double> prices = getVisiblePrices();
            return !prices.isEmpty() && prices.get(0).equals(Collections.max(prices));
        });

        List<Double> actualPrices = getVisiblePrices();
        List<Double> expected = new ArrayList<>(actualPrices);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals("❌ Products are not sorted high to low", expected, actualPrices);
    }


    private List<Double> getVisiblePrices() {
        List<WebElement> priceElements = driver.findElements(org.openqa.selenium.By.cssSelector(".price"));
        List<Double> prices = new ArrayList<>();

        for (WebElement priceEl : priceElements) {
            String text = priceEl.getText().replace("$", "").replace(",", "").trim();
            if (!text.isEmpty()) {
                try {
                    prices.add(Double.parseDouble(text));
                } catch (NumberFormatException ignored) {}
            }
        }

        return prices;
    }

}
