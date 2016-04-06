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

    @FindBy(xpath = "//a[@id='home-icon']")
    WebElement homePageLogo;

    @FindBy(xpath = "//span[@class='gb_1a gbii']")
    WebElement accountLink;

    @FindBy(id = "gb_71")
    WebElement logoutLink;

    @FindBy(id = "signIn")
    WebElement loginButton;

    //Constructor
    public HomePage(WebDriver driver){
        super(driver);

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void checkHomePage() {
        Functions.waitForElementIsVisible(driver, accountLink);
        Assert.assertTrue(homePageLogo.isDisplayed());
        Assert.assertEquals("Home - Dropbox", driver.getTitle());
    }

    public void logout() {
        clickAccountLink();
        clickLogoutLink();
    }

    public void clickAccountLink(){
        Functions.waitForElementIsVisible(driver, accountLink);
        accountLink.click();
    }

    public void clickLogoutLink(){
        Functions.waitForElementIsVisible(driver, logoutLink);
        logoutLink.click();

        Functions.waitForElementIsVisible(driver, loginButton);
    }
}
