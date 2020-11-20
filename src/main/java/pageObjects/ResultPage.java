package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.Base;


public class ResultPage extends Base {
    public WebDriver driver;
    public ResultPage(WebDriver driver){
        this.driver = driver;
    }


    private By logOutButton = By.xpath("//label[contains(text(),'Cerrar sesión')]");
    private By myEmailButton = By.xpath("//body/div[@id='root']/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/label[1]");


    public WebElement getLateralMenuButtons(int i){return fluentWait(By.xpath("//body/div[@id='root']/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/button[" + i + "]"));}//i=1..6
    public WebElement getLogOutButton(){ return fluentWait(logOutButton); }
    public WebElement getResultTitle(){ return fluentWait(resultTitle); }
    private By resultTitle = By.xpath("//body/div[@id='root']/div[1]/div[2]/label[1]");
    public WebElement getEmailButton(){ return fluentWait(myEmailButton); }


    public void logOut(){
        Assert.assertTrue(isVisibleInViewport(getLogOutButton()));
        getLogOutButton().click();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(isVisibleInViewport(loginPage.getEmailInput()));
        log.info("sesión cerrada");
    }
}
