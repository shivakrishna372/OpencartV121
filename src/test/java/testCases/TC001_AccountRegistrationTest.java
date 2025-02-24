package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups={"Regression","Master"})
    public void verify_account_registration() {
        logger.info("************ Starting TC001_AccountRegistrationTest ************");

        try {
            // Get the WebDriver instance from BaseClass
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();
            logger.info("Navigated to registration page.");

            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

            // Generate dynamic test data
            String firstName = randomString(5);
            String lastName = randomString(5);
            String email = randomEmail();
            String telephone = randomPhoneNumber();
            String password = randomAlphaNumeric(10);

            // Fill the registration form
            regpage.enterFirstName(firstName);
            regpage.enterLastName(lastName);
            regpage.enterEmail(email);
            regpage.enterTelephone(telephone);
            regpage.enterPassword(password);
            regpage.enterConfirmPassword(password);
            regpage.clickPrivacyPolicy();
            regpage.clickContinueButton();
            logger.info("Registration form submitted.");

            // Verify successful registration
            boolean isSuccess = regpage.isRegistrationSuccessful();
            Assert.assertTrue(isSuccess, "Account registration failed!");
            logger.info("Account registration successful.");
        } catch (Exception e) {
            logger.error("Exception occurred in registration test: " + e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
