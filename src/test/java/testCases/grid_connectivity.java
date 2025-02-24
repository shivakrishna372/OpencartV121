package testCases;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class grid_connectivity {
    public static void main(String[] args) throws MalformedURLException {
        String hubURL = "http://192.168.29.47:4444/wd/hub"; 
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL(hubURL), options);

        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.quit();
    }
}
