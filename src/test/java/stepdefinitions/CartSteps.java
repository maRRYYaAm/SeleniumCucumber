package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.CartPage;
import pages.ProductPage;
import utils.TestData;
import static stepdefinitions.Hooks.driver;

public class CartSteps {

    ProductPage productPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);

    @When("user opens product {string} page")
    public void user_opens_product_page(String productName) {
        // Navigate to homepage where search is available
        driver.get(TestData.BASE_URL);
        productPage = new ProductPage(driver);
        productPage.openProductPage(productName);
    }

    @When("adds the product to the cart")
    public void adds_the_product_to_cart() {
        productPage.addToCart("M", "Blue");
    }

    @Then("cart should have {int} item")
    public void cart_should_have_item(Integer expectedCount) {
        Assert.assertEquals((int) expectedCount, cartPage.getCartItemCount());
    }

    @Then("cart should display product {string}")
    public void cart_should_display_product(String expectedTitle) {
        String actualTitle = cartPage.getCartProductTitle();
        Assert.assertEquals("Cart product title mismatch!", expectedTitle, actualTitle);
    }

    @Then("cart total should be ${double}")
    public void cart_total_should_be(double expectedTotal) {
        double actualTotal = cartPage.getCartTotal();
        Assert.assertEquals("Cart total mismatch!", expectedTotal, actualTotal, 0.01);
    }

}
