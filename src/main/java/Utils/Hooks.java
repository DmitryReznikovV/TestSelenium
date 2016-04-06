package Utils;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver driver;
    PropertieLoader properttieLoader = new PropertieLoader();

    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void openBrowser() throws MalformedURLException {
        String browser = properttieLoader.getProperty("browser");
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + properttieLoader.getProperty("driverpath"));

        if(browser.equals("Firefox")) {
            driver = new FirefoxDriver();
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
