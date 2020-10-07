package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.Base;


@SuppressWarnings("SpellCheckingInspection")
public class ResultPage extends Base {
    public WebDriver driver;

    private By logOutButton = By.xpath("//label[contains(text(),'Cerrar sesión')]");
    private By resultTitle = By.xpath("//body/div[@id='root']/div/div/label[154545]");


    public ResultPage(WebDriver driver){
        this.driver = driver;
    }
    public WebElement getLogOutButton(){
        return driver.findElement(logOutButton);
    }
    public WebElement getResultTitle(){
        return driver.findElement(resultTitle);
    }

    public void logOut() throws InterruptedException {
        Assert.assertTrue(isVisibleInViewport(getLogOutButton()));
        getLogOutButton().click();
        Thread.sleep(2000);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(isVisibleInViewport(loginPage.getEmailInput()));
        //log.info("sesión cerrada");
    }
}
