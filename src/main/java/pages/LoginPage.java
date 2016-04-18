package pages;

import Utils.BaseClass;
import Utils.Functions;
import Utils.PropertiesLoader;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {

    PropertiesLoader prop = new PropertiesLoader();
    Functions functions;

    @FindBy(xpath = "//button[contains(@class, 'login-button')]")
    public WebElement loginButton;

    @FindBy(xpath = "//input[@name='login_email']")
    WebElement userLogin;

    @FindBy(xpath = "//input[@name='login_password']")
    WebElement UserPassword;

    @FindBy(id = "sign-in")
    WebElement signInLink;

    @FindBy(xpath = "//input[@name='remember_me']")
    WebElement rememberMe;

    public LoginPage(WebDriver driver){
        super(driver);

        driver.get(prop.getProperty("URL"));

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void checkLoginPage(){
        functions.waitForElementIsVisible(driver, signInLink);

        // Verify the Login page title
        Assert.assertEquals("Dropbox - Sign in", driver.getTitle());
    }

    public void clickSignInLink(){
        signInLink.click();
        functions.waitForElementIsVisible(driver, loginButton);
    }

    public void loginToAccount(String login, String password){
        if(rememberMe.isSelected()){
            rememberMe.click();
        }
        userLogin.sendKeys(login);
        UserPassword.sendKeys(password);
        clickLoginButton();
    }

    public void clickLoginButton(){
        loginButton.click();
    }
}