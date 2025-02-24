package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class BasePage {
    // WebDriver instance with default (package-private) access
    WebDriver driver;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Initialize PageFactory elements
        PageFactory.initElements(driver, this);
    }

}