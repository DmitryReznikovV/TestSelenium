package tests;

import Utils.*;
import pages.LoginPage;
import pages.HomePage;
import org.junit.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class DropBoxTest extends Hooks {

    PropertiesLoader properttieLoader = new PropertiesLoader();
    String path = System.getProperty("user.dir");
    Functions functions;

    @Test
    public void dropboxFileUpload() throws Exception {

        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());

        try {
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

            //Performs native keystrokes for CTRL+V and ENTER keys and upload the file
            functions.uploadFile();
            homePage.clickDoneButton();

            //Rename uploaded file
            homePage.renameTestFile();

            //Create new folder
            homePage.createNewFolder();

            //Move uploaded file to created folder and navigate to this folder
            homePage.moveFileToFolder();
            homePage.navigateToFolder();

            //Download the file by using Firefox profile
            homePage.downloadNewFile();

            //Delete file from the current directory
            functions.deleteFile(path, "Brainloop_Reznikau_Temp.txt");

            //Share file
            homePage.shareFile();

            //Check Inbox after sharing file
            CheckingMails.check(properttieLoader.getProperty("mailhost"), properttieLoader.getProperty("mailStoreType"),
                    properttieLoader.getProperty("mailusername"), properttieLoader.getProperty("mailpassword"));

            //Logout
            homePage.logout();
            loginPage.checkLoginPage();
        }catch (Exception e){
            Functions.takeScreenshot(driver);
            throw (e);
        }
        System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
    }
}
