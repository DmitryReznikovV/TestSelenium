package pages;

import Utils.BaseClass;
import Utils.Functions;
import Utils.PropertiesLoader;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.io.File;

public class HomePage extends BaseClass {

    PropertiesLoader prop = new PropertiesLoader();
    String path = System.getProperty("user.dir");
    Functions functions;

    @FindBy(xpath = "//a[@id='home-icon']")
    WebElement homePageLogo;

    @FindBy(xpath = "//li[@id='header-account-menu']//button")
    WebElement accountLink;

    @FindBy(id = "upload_button")
    WebElement uploadLink;

    @FindBy(id = "choose-button")
    WebElement chooseFileButton;

    @FindBy(id = "done-button")
    WebElement doneButton;

    @FindBy(xpath = "//a[text()='Sign out']")
    WebElement signOutLink;

    @FindBy(xpath = "//a[contains(text(), 'BrainloopTestFolder_Reznikau')]")
    WebElement folderName;

    @FindBy(xpath = "//a[contains(text(), 'BrainloopTestFolder_Reznikau')]/../..")
    WebElement folderTableCell;

    @FindBy(xpath = "//a[contains(text(), 'Brainloop_Reznikau')]")
    WebElement fileName;

    By testFileName = By.xpath("//a[contains(text(), 'Brainloop_Reznikau')]");

    @FindBy(xpath = "//a[contains(text(), 'Brainloop_Reznikau')]/../..")
    WebElement tableCell;

    @FindBy(xpath = "//a[contains(text(), 'Brainloop_Reznikau_Temp')]")
    WebElement newFileName;

    @FindBy(xpath = "//a[contains(text(), 'Brainloop_Reznikau_Temp')]/../..")
    WebElement newTableCell;

    By tableCellWithNewFile = By.xpath("//a[contains(text(), 'Brainloop_Reznikau_Temp')]/../..");

    @FindBy(id = "rename_action_button")
    WebElement renameButton;

    @FindBy(xpath = ".//*[@id='null']/input")
    WebElement tableInputField;

    @FindBy(id = "new_folder_button")
    WebElement newFolderButton;

    @FindBy(id = "download_action_button")
    WebElement downloadButton;

    @FindBy(id = "move_action_button")
    WebElement moveLink;

    @FindBy(xpath = "//a[contains(text(), 'BrainloopTestFolder_Reznikau')]")
    WebElement folderLink;

    @FindBy(xpath = "//input[@value='Move']")
    WebElement moveButton;

    @FindBy(xpath = "//a[contains(@class, 'inline-share-button')]")
    WebElement shareButton;

    @FindBy(id = "share-link-modal-new-collab-input")
    WebElement emailForSharring;

    @FindBy(xpath = "//button[text() = 'Send']")
    WebElement sendButton;

    //Constructor
    public HomePage(WebDriver driver){
        super(driver);

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void checkHomePage() {
        functions.waitForElementIsVisible(driver, accountLink);

        // Verify Home page
        Assert.assertEquals(accountLink.getText(), prop.getProperty("accountname"));
        Assert.assertTrue(homePageLogo.isDisplayed());
        Assert.assertEquals("Home - Dropbox", driver.getTitle());
    }

    public void clickUploadLink() {
        if(uploadLink.isDisplayed()){
            uploadLink.click();
            functions.waitForElementIsVisible(driver, chooseFileButton);
        }
        System.out.println("Upload link absent");
    }

    public void clickDoneButton() {
        functions.waitForElementIsVisible(driver, doneButton);
        if (doneButton.isDisplayed()) {
            doneButton.click();
            functions.fluenWait(driver, testFileName);

            // Verify file is uploaded and presents on the page
            Assert.assertTrue(fileName.isDisplayed());
            Assert.assertEquals(prop.getProperty("filename"), fileName.getText());
        }
        System.out.println("Done button absent");
    }

    public void renameTestFile() {
        tableCell.click();
        functions.waitForElementIsVisible(driver, renameButton);
        renameButton.click();
        functions.waitForElementIsVisible(driver, tableInputField);

        tableInputField.clear();
        tableInputField.sendKeys(prop.getProperty("newfilename"));
        try {
            functions.performEnter();
            functions.waitForElementIsVisible(driver, newFileName);

            // Verify name of the new file
            Assert.assertEquals(prop.getProperty("newfilename") ,newFileName.getText());
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createNewFolder() {
        Assert.assertTrue(newFolderButton.isDisplayed());

        newFolderButton.click();
        functions.waitForElementIsVisible(driver, tableInputField);

        tableInputField.clear();
        tableInputField.sendKeys(prop.getProperty("foldername"));
        try {
            functions.performEnter();
            functions.fluenWait(driver, tableCellWithNewFile);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveFileToFolder() {
        newTableCell.click();
        functions.waitForElementIsVisible(driver, moveLink);
        moveLink.click();
        functions.waitForElementIsVisible(driver, folderLink);
        folderLink.click();
        moveButton.click();
        functions.waitForElementIsVisible(driver, uploadLink);

        // Verify that after moving file to the particular folder we don't see this file in the table anymore
        Assert.assertEquals(0, driver.findElements(By.xpath("//a[contains(text(), 'Brainloop_Reznikau_Temp')]")).size());
    }

    public void clickChooseFilesButton() {
        chooseFileButton.click();
    }

    public void navigateToFolder(){
        folderName.click();

        // Verify uploaded file in the folder
        Assert.assertTrue(newFileName.isDisplayed());
    }

    public void downloadNewFile(){
        newTableCell.click();
        functions.waitForElementIsVisible(driver, downloadButton);
        downloadButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Verify that downloaded file exists in the current directory
        File varTmpDir = new File(path + "\\" + prop.getProperty("newfilename"));
        Assert.assertTrue(varTmpDir.exists());
    }

    public void shareFile(){
        shareButton.click();

        // Verify
        Assert.assertTrue(emailForSharring.isDisplayed());
        Assert.assertTrue(sendButton.isDisplayed());

        emailForSharring.sendKeys(prop.getProperty("mailusername"));
        sendButton.click();

        functions.waitForElementIsNotVisible(driver, By.xpath("//button[text() = 'Send']"));
        Assert.assertEquals(0, driver.findElements(By.xpath("//button[text() = 'Send']")).size());
    }

    public void logout(){
        accountLink.click();
        functions.waitForElementIsVisible(driver, signOutLink);
        signOutLink.click();
    }
}
