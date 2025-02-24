package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
    // Constructor
    public AccountRegistrationPage(WebDriver driver) {
        super(driver); // Call BasePage constructor
    }

    // Locators using XPath
    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txtLastName;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement txtTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkdPrivacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[contains(text(), 'Your Account Has Been Created!')]")
    WebElement msgSuccess;

    // Methods
    public void enterFirstName(String firstName) {
        txtFirstName.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        txtLastName.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void enterTelephone(String telephone) {
        txtTelephone.sendKeys(telephone);
    }

    public void enterPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        txtConfirmPassword.sendKeys(confirmPassword);
    }

    public void clickPrivacyPolicy() {
        chkdPrivacyPolicy.click();
    }

    public void clickContinueButton() {
        btnContinue.click();
    }

    public boolean isRegistrationSuccessful() {
        return msgSuccess.isDisplayed();
    }
}
