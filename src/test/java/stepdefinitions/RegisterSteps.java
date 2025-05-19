package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.RegisterPage;
import utils.TestData;

public class RegisterSteps {
    WebDriver driver;
    RegisterPage registerPage = new RegisterPage(Hooks.driver);

    @Given("user is on the registration page")
    public void user_is_on_the_registration_page() {
        driver = new ChromeDriver();
        driver.get(TestData.BASE_URL + "customer/account/create/");
        registerPage = new RegisterPage(driver);
    }

    @When("user enters valid registration details")
    public void enter_valid_details() {
        registerPage.fillRegistrationForm(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.EMAIL, TestData.PASSWORD);
    }

    @When("user enters details with an existing email")
    public void enter_existing_email() {
        registerPage.fillRegistrationForm("John", "Doe", TestData.EMAIL, "Password123");
    }

    @When("submits the registration form")
    public void submits_the_registration_form() {
        registerPage.submitForm();
    }

    @When("user submits the registration form with missing fields")
    public void submit_with_missing_fields() {
        registerPage.submitForm();  // No fields filled
    }

    @Then("user should be redirected to account page")
    public void user_redirected() {
        Assert.assertTrue(driver.getCurrentUrl().contains("customer/account"));
    }

    @Then("account page should show correct first name, last name, and email")
    public void validate_account_info() {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(TestData.FIRST_NAME));
        Assert.assertTrue(pageSource.contains(TestData.LAST_NAME));
        Assert.assertTrue(pageSource.contains(TestData.EMAIL));
        driver.quit();
    }

    @Then("error message for existing email should be displayed")
    public void error_existing_email() {
        String error = registerPage.getErrorMessage();
        Assert.assertTrue("Expected error message not found!",
                error.contains("There is already an account with this email address")
                                || error.contains("email address is already associated"));
    }


    @Then("error message for missing fields should be displayed")
    public void error_missing_fields() {
        String source = driver.getPageSource();
        Assert.assertTrue(source.contains("This is a required field"));
        driver.quit();
    }
}
