package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    By firstName = By.id("firstname");
    By lastName = By.id("lastname");
    By email = By.id("email_address");
    By password = By.id("password");
    By confirmPassword = By.id("password-confirmation");
    By submitButton = By.cssSelector("button[title='Create an Account']");
    By errorMsg = By.cssSelector(".message-error");

    public void fillRegistrationForm(String fname, String lname, String emailStr, String pass) {
        driver.findElement(firstName).sendKeys(fname);
        driver.findElement(lastName).sendKeys(lname);
        driver.findElement(email).sendKeys(emailStr);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(confirmPassword).sendKeys(pass);
    }

    public void submitForm() {
        driver.findElement(submitButton).click();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'message-error') or contains(@data-ui-id, 'message-error')]")
        ));
        return errorBox.getText();
    }

}
