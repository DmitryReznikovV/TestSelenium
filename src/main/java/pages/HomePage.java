package pages;

import Utils.BaseClass;
import Utils.Functions;
import Utils.PropertieLoader;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseClass{

    PropertieLoader prop = new PropertieLoader();

    @FindBy(xpath = "//a[@id='home-icon']")
    WebElement homePageLogo;

    @FindBy(xpath = "//li[@id='header-account-menu']//button")
    WebElement accountLink;

    @FindBy(id = "gb_71")
    WebElement logoutLink;

    @FindBy(id = "signIn")
    WebElement loginButton;

    @FindBy(id = "upload_button")
    WebElement uploadLink;

    @FindBy(id = "choose-button")
    WebElement chooseFileButton;

    //Constructor
    public HomePage(WebDriver driver){
        super(driver);

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void checkHomePage() {
        Functions.waitForElementIsVisible(driver, accountLink);

        Assert.assertEquals(accountLink.getText(), prop.getProperty("accountname"));
        Assert.assertTrue(homePageLogo.isDisplayed());
        Assert.assertEquals("Home - Dropbox", driver.getTitle());
    }

    public void clickUploadLink(){
        if(uploadLink.isDisplayed()){
            uploadLink.click();
            Functions.waitForElementIsVisible(driver, chooseFileButton);
        }
    }

    public void clickChooseFilesButton(){
        chooseFileButton.click();
    }
}
