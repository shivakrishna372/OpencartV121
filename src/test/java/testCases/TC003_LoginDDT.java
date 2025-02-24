package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TC003_LoginDDT extends BaseClass {

    // Logger initialization (updated for Log4j 2.x)
    private static final Logger logger = LogManager.getLogger(TC003_LoginDDT.class);

    // DataProvider linked to the DataProviders class to get login data from Excel
    @DataProvider(name = "LoginData")
    public Object[][] loginData() {
        DataProviders dataProvider = new DataProviders();
        logger.info("Fetching login data from Excel...");
        return dataProvider.getLoginData();  // This returns the login data from the Excel file
    }

    // Test case to verify login using data from the Excel file
    @Test(dataProvider = "LoginData", description = "Test login functionality with valid and invalid data",groups="Datadriven")
    public void verify_loginDDT(String username, String password, String expectedResult) {
        logger.info("Starting the login test with username: " + username);

        // HomePage
        HomePage hp = new HomePage(driver);
        hp.clickMyAccount();
        hp.clickLogin();
        logger.info("Clicked on login button");

        // LoginPage
        LoginPage lp = new LoginPage(driver);
        lp.enterEmail(username);  // Using data from Excel
        lp.enterPassword(password);  // Using data from Excel
        lp.clickLogin();
        logger.info("Entered login credentials and clicked login");

        // MyAccountPage
        MyAccountPage macc = new MyAccountPage(driver);
        boolean targetPage = macc.isMyAccountPageDisplayed();  // Check if My Account page is displayed

        // Assertion based on expected result
        if (expectedResult.equals("Login Successful")) {
            // Assert that My Account page is displayed (successful login)
            Assert.assertTrue(targetPage, "Login failed: My Account page is not displayed.");
            logger.info("Login successful, My Account page displayed.");
        } else if (expectedResult.equals("Login Failed")) {
            // Assert that the login failed, meaning the page is not the My Account page
            Assert.assertFalse(targetPage, "Login should have failed, but My Account page is displayed.");
            logger.info("Login failed, My Account page not displayed.");
        } else {
            Assert.fail("Unexpected result for username: " + username);
            logger.error("Unexpected result: " + expectedResult);
        }
    }
}
