package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends BasePage {

    // Constructor
    public MyAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // Initialize page elements
    }

    // WebElement for My Account heading
    @FindBy(xpath = "//h2[text()='My Account']")
    private WebElement myAccountHeading;

    // WebElement for Logout link
    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
    private WebElement logoutLink;

    // Action method to check if "My Account" heading is displayed
    public boolean isMyAccountPageDisplayed() {
        try {
            return myAccountHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Action method to click the Logout link
    public void clickLogout() {
        logoutLink.click();
    }
}

    
