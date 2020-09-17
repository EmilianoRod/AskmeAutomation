package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage {

    public WebDriver driver;

    private By emailInput = By.xpath("//input[@name='email']");
    private By passwordInput = By.xpath("//input[@name='password']");
    private By submitButton = By.xpath("//button[@class='sc-AxmLO clVYPn']");


    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getEmailInput(){
        return driver.findElement(emailInput);
    }

    public WebElement getPasswordInput(){
        return driver.findElement(passwordInput);
    }

    public WebElement getSubmitButton(){
        return driver.findElement(submitButton);
    }

    public void LogIn(String email, String password){
        Assert.assertTrue(getEmailInput().isDisplayed());
        Assert.assertTrue(getPasswordInput().isDisplayed());
        Assert.assertTrue(getSubmitButton().isDisplayed());
        getEmailInput().sendKeys(email);
        getPasswordInput().sendKeys(password);
        getSubmitButton().click();
    }
}
