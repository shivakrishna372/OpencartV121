package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;
    List<String> includedGroups;

    public void onStart(ITestContext testContext) {
        // Generate report name with timestamp
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timestamp + ".html";

        // Ensure reports directory exists
        File reportDir = new File("./reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs(); // Ensure all directories are created
        }

        // Initialize ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter("./reports/" + repName);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Opencart Functional Testing");

        // Initialize ExtentReports and attach the reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Fetch parameters from XML
        String testerName = "John Doe";
        String systemUser = System.getProperty("user.name");
        String os = testContext.getCurrentXmlTest().getParameter("OS");
        String browser = testContext.getCurrentXmlTest().getParameter("Browser");

        if (os == null) os = "Unknown OS";
        if (browser == null) browser = "Unknown Browser";

        // Fetch included groups dynamically
        includedGroups = new ArrayList<>(Arrays.asList(testContext.getIncludedGroups()));

        // Set system info in report
        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("Tester", testerName);
        extent.setSystemInfo("Executed By", systemUser);
        extent.setSystemInfo("OS", os);
        extent.setSystemInfo("Browser", browser);
        extent.setSystemInfo("Included Groups", includedGroups.isEmpty() ? "None" : String.join(", ", includedGroups));
    }

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.info("Executing Test in Class: " + result.getTestClass().getName());
        String[] testGroups = result.getMethod().getGroups();
        if (testGroups.length > 0) {
            test.assignCategory(testGroups);
        }
    }

    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
        test.info("Executed Method: " + result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        test.fail("Test Failed: " + result.getThrowable());
        test.info("Executed Method: " + result.getMethod().getMethodName());

        try {
            BaseClass testInstance = (BaseClass) result.getTestClass().getRealClass().getDeclaredConstructor().newInstance();
            String screenshotPath = testInstance.captureScreenshot(result.getMethod().getMethodName());
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            test.fail("Failed to capture screenshot: " + e.getMessage());
        }
    }

    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped: " + result.getThrowable());
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();

        // Verify report file path
        File reportFile = new File("./reports/" + repName);
        System.out.println("Report generated at: " + reportFile.getAbsolutePath());

        if (reportFile.exists()) {
            try {
                Desktop.getDesktop().browse(reportFile.toURI());
            } catch (IOException e) {
                System.out.println("Error opening report: " + e.getMessage());
            }
        } else {
            System.out.println("Report file NOT found: " + reportFile.getAbsolutePath());
        }
    }
}
