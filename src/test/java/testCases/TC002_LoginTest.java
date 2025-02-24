package testCases;

import org.testng.Assert;  // Correct import
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass {

    @Test(groups={"Sanity","Master"})
    public void verify_login() {
        logger.info("************* TC002_LoginTest *************");

        try {
            // HomePage
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            // LoginPage
            LoginPage lp = new LoginPage(driver);
            lp.enterEmail(p.getProperty("email"));
            lp.enterPassword(p.getProperty("password"));
            lp.clickLogin();

            // MyAccountPage
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageDisplayed();  // Fixed method call

            // Assertion
            Assert.assertTrue(targetPage, "Login failed: My Account page is not displayed.");

        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getMessage());
            Assert.fail("Test failed due to an exception.");
        }

        logger.info("************* TC002_Login Test Completed *************");
    }
}
