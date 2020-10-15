package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import resources.Base;

public class InfoUserPage extends Base {

    public WebDriver driver;
    public InfoUserPage(WebDriver driver){ this.driver = driver; }

    private By myEmailText = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/label[2]");
    private By editButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/button[1]");
    private By emailInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/input[1]");
    private By passwordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[2]/input[1]");
    private By confirmPasswordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/input[1]");
    private By saveButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/button[1]");
    private By passwordsNotMatch = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/label[1]");
    private By emailInvalidMessage = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/label[2]");


    public WebElement getMyEmailText(){ return driver.findElement(myEmailText); }
    public WebElement getEditButton(){ return driver.findElement(editButton); }
    public WebElement getEmailinput(){ return driver.findElement(emailInput); }
    public WebElement getPasswordinput(){ return driver.findElement(passwordInput); }
    public WebElement getconfirmPasswordinput(){ return driver.findElement(confirmPasswordInput); }
    public WebElement getSaveButton(){ return driver.findElement(saveButton); }
    public WebElement getPasswordsNotMatch(){ return driver.findElement(passwordsNotMatch); }
    public WebElement getEmailInvalidMessage(){ return driver.findElement(emailInvalidMessage); }



}
