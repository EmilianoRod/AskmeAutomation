package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import resources.Base;

public class AdminsPage extends Base {

    public WebDriver driver;
    public AdminsPage(WebDriver driver){ this.driver = driver; }

    private By newAdminButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/button[1]");
    private By emailInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/input[1]");
    private By passwordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/input[1]");
    private By confirmButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/button[1]");


    public WebElement getNewAdminButton(){
        return driver.findElement(newAdminButton);
    }

    public WebElement getEmailInput(){
        return driver.findElement(emailInput);
    }

    public WebElement getPasswordInput(){
        return driver.findElement(passwordInput);
    }

    public WebElement confirmButton(){
        return driver.findElement(confirmButton);
    }
}
