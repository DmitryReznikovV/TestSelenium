package Utils;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Functions {

    public static void waitForElementIsVisible(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new IllegalStateException("element is not displaying");
        }
    }

    public static void uploadFile() throws AWTException, InterruptedException {

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
    }

    public static void performEnter() throws AWTException, InterruptedException {

        Robot robot = new Robot();
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
    }

    public static void dragAndDrop(WebDriver driver, WebElement source, WebElement target){
        Actions builder = new Actions(driver);

        Action dragAndDrop = builder.clickAndHold(source)
                .moveToElement(target)
                .release(target)
                .build();

        dragAndDrop.perform();
    }

    public static void deleteFile(String path, String fileName){
        try{
            File file = new File(path + "\\" + fileName);

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void waitForElementIsNotVisible(WebDriver driver, By locator) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement fluenWait(final WebDriver driver, final By locator) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(locator);
            }
        });
    }
}
