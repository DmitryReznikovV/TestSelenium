package tests;

import Utils.PropertieLoader;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class LoginLogoutTest {

    public WebDriver driver;
    PropertieLoader properttieLoader = new PropertieLoader();

    @Before
    public void setup(){
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

    @Test
    public void applyAsDeveloper(){

        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());

        //Create instance of LoginPage Class
        LoginPage home = new LoginPage(driver);

        home.clickSignInLink();
        home.loginToAccount(properttieLoader.getProperty("username"), properttieLoader.getProperty("password"));

        //Create instance of HomePage Class
        HomePage homePage = new HomePage(driver);

        //Check if page is opened
        homePage.checkHomePage();

        //Upload File
        homePage.clickUploadLink();
        homePage.clickChooseFilesButton();

        //Logout
        //homePage.logout();

        System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @After
    public void close(){
        if(driver!=null){
            driver.close();
        }
    }
}
