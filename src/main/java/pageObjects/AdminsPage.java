package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import resources.Base;

import java.util.ArrayList;
import java.util.List;

public class AdminsPage extends Base {

    public WebDriver driver;
    public AdminsPage(WebDriver driver){ this.driver = driver; }

    private By newAdminButton =  By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/button[1]");
    private By emailInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/input[1]");
    private By passwordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/input[1]");
    private By confirmButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/button[1]");
    private By confirmPasswordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/input[1]");
    private By adminsTable = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[3]/table[1]/tbody[1]");
    private By adminsTableRows = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[3]/table[1]/tbody[1]/tr");


    public WebElement getRowsAdminTable(int i){ return fluentWait(By.xpath("//tbody/tr["+i+"]td[1]"));} //i=1 to cant of rows
    public WebElement getNewAdminButton(){ return fluentWait(newAdminButton);}
    public WebElement getEmailInput(){ return fluentWait(emailInput); }
    public WebElement getPasswordInput(){ return fluentWait(passwordInput); }
    public WebElement getConfirmButton(){ return fluentWait(confirmButton); }
    public WebElement getConfirmPasswordInput(){ return fluentWait(confirmPasswordInput); }
    public WebElement getAdminsTable(){ return driver.findElement(adminsTable); }
    public List<WebElement> getRowsTable(){ return driver.findElements(adminsTableRows); }
}
