package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;


    private By signInLink = By.linkText("Sign In");
    private By createAccountLink = By.linkText("Create an Account");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInBtn.click();
    }

    public void clickCreateAccount() {
        driver.findElement(createAccountLink).click();
    }
}
