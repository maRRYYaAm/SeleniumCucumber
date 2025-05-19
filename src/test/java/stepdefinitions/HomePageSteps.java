package stepdefinitions;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

public class HomePageSteps {

    WebDriver driver;
    HomePage homePage;

    @Given("user is on the home page")
    public void user_is_on_the_home_page() {
        WebDriverManager.chromedriver().setup(); //
        driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");
        homePage = new HomePage(driver);
    }

    @When("user clicks on the Sign In link")
    public void user_clicks_sign_in() {
        homePage.clickSignIn();
    }

    @Then("Sign In page should be displayed")
    public void sign_in_page_displayed() {
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("customer/account/login"));
    }

    @When("user navigates back and clicks on the Create Account link")
    public void user_clicks_create_account() {
        driver.navigate().back();
        homePage.clickCreateAccount();
    }

    @Then("Create Account page should be displayed")
    public void create_account_page_displayed() {
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("customer/account/create"));
        driver.quit();
    }
}
