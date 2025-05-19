package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.BaseClass;
import utils.TestData;

public class LoginSteps extends BaseClass {

    WebDriver driver;
    LoginPage loginPage  = new LoginPage(Hooks.driver);

    @Given("user is on the login page")
    public void user_is_on_the_login_page() {
        initializeDriver();
        driver = BaseClass.driver;
        driver.get(TestData.BASE_URL + "customer/account/login/");
        loginPage = new LoginPage(driver);
    }

    @When("user enters valid credentials")
    public void user_enters_valid_credentials() {
        loginPage.enterEmail(TestData.LOGIN_EMAIL);
        loginPage.enterPassword(TestData.LOGIN_PASSWORD);
    }

    @When("clicks the login button")
    public void clicks_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() throws InterruptedException {
        Thread.sleep(2000); // Optional wait to allow redirect
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Optional: check for error message
        boolean isErrorPresent = !driver.findElements(By.cssSelector(".message-error")).isEmpty();
        if (isErrorPresent) {
            String errorText = driver.findElement(By.cssSelector(".message-error")).getText();
            System.out.println("Login failed: " + errorText);
        }

        Assert.assertTrue("User not redirected to account page", currentUrl.contains("customer/account"));
    }
}
