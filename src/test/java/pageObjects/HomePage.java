package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver); // Call BasePage constructor
    }

    // Locators (Default access instead of private)
    @FindBy(linkText = "My Account")
    WebElement myAccountLink; // Removed private

    @FindBy(linkText = "Register")
    WebElement registerLink; // Removed private
    
    @FindBy(linkText = "Login")
    WebElement loginLink;


    // Action Methods
    public void clickMyAccount() {
        myAccountLink.click();
    }

    public void clickRegister() {
        registerLink.click();
    }
    public void clickLogin() {
        loginLink.click();
    }

}
