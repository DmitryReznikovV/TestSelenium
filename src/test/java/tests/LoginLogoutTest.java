package tests;

import Utils.Hooks;
import Utils.PropertieLoader;
import pages.LoginPage;
import pages.HomePage;
import org.junit.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class LoginLogoutTest extends Hooks{

    PropertieLoader properttieLoader = new PropertieLoader();
    String path = System.getProperty("user.dir");

    @Test
    public void applyAsDeveloper() throws InterruptedException, AWTException {

        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());

        //Create instance of LoginPage Class
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickSignInLink();
        loginPage.loginToAccount(properttieLoader.getProperty("username"), properttieLoader.getProperty("password"));

        //Create instance of HomePage Class
        HomePage homePage = new HomePage(driver);

        //Check if page is opened
        homePage.checkHomePage();

        //Upload File
        homePage.clickUploadLink();
        homePage.clickChooseFilesButton();

        Thread.sleep(4000);

        //Set the filename for upload in clipboard
        StringSelection stringSelection = new StringSelection(path + "\\" + "Brainloop_Reznikau.txt");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

        //Performs native keystrokes for CTRL+V and ENTER keys
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_ENTER);

        homePage.clickDoneButton();

        //Logout
        homePage.logout();

        loginPage.checkLoginPage();

        System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
    }
}
