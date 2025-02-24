package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import java.text.SimpleDateFormat;

public class BaseClass {

    public static WebDriver driver;
    public static final Logger logger = LogManager.getLogger(BaseClass.class);
    public Properties p;

    @Parameters({"os", "browser"})
    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    public void setup(String os, String browser) throws IOException {
        
        // Load config.properties
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        String executionEnv = p.getProperty("execution_env", "local");
        logger.info("Execution Environment: " + executionEnv);
        logger.info("Running on OS: " + os + " | Browser: " + browser);
        
        if (executionEnv.equalsIgnoreCase("remote")) {
            try {
                String hubURL = p.getProperty("hubURL", "http://localhost:4444");
                logger.info("Connecting to Selenium Grid at: " + hubURL);

                switch (browser.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (!System.getProperty("headless", "true").equalsIgnoreCase("false")) {
                            chromeOptions.addArguments("--headless=new");
                        }
                        driver = new RemoteWebDriver(new URL(hubURL), chromeOptions);
                        break;
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (!System.getProperty("headless", "true").equalsIgnoreCase("false")) {
                            firefoxOptions.addArguments("--headless");
                        }
                        driver = new RemoteWebDriver(new URL(hubURL), firefoxOptions);
                        break;
                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        if (!System.getProperty("headless", "true").equalsIgnoreCase("false")) {
                            edgeOptions.addArguments("--headless");
                        }
                        driver = new RemoteWebDriver(new URL(hubURL), edgeOptions);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
                logger.info("Remote WebDriver initialized successfully.");

                // Verify session
                if (((RemoteWebDriver) driver).getSessionId() == null) {
                    throw new RuntimeException("Remote WebDriver session not established!");
                }
            } catch (Exception e) {
                logger.error("Error initializing remote WebDriver: ", e);
                throw new RuntimeException("Failed to connect to Selenium Grid.");
            }
        } else {
            throw new UnsupportedOperationException("Only remote execution is supported.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(p.getProperty("appURL"));
        logger.info("Navigated to URL: " + p.getProperty("appURL"));
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing browser...");
            driver.quit();
        }
    }

    public String randomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

    public String randomEmail() {
        return randomString(7).toLowerCase() + "@test.com";
    }

    public String randomPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    public String randomAlphaNumeric(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public static String captureScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destination = String.format("./screenshots/%s_%s.png", testName, timestamp);
        try {
            FileUtils.copyFile(source, new File(destination));
            return destination;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }
}
