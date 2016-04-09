package Utils;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver driver;
    PropertieLoader properttieLoader = new PropertieLoader();

    @Before
    public void openBrowser() throws MalformedURLException {
        String browser = properttieLoader.getProperty("browser");
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + properttieLoader.getProperty("driverpath"));

        if(browser.equals("Firefox")) {
            FirefoxProfile fxProfile = new FirefoxProfile();
            fxProfile.setPreference("browser.download.folderList", 2);
            fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
            fxProfile.setPreference("browser.download.dir", path);
            fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain");
            driver = new FirefoxDriver(fxProfile);
        }
        else {
            if(browser.equals("Chrome")){
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void close(){
        if(driver!=null){
            driver.close();
        }
    }
}
