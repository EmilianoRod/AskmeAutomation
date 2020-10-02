package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.base;

public class LoginPage extends base{

    public WebDriver driver;

    private By emailInput = By.xpath("//input[@name='email']");
    private By passwordInput = By.xpath("//input[@name='password']");
    private By submitButton = By.xpath("//button[@class='sc-AxmLO clVYPn']");
    private By errorText = By.xpath("//label[contains(text(),'El email no es válido')]");
    private By errorTextCredencialesInvalidas = By.xpath("//label[contains(text(),'Credenciales invalidas')]");
    private By resultsPageElement = By.xpath("//body/div[@id='root']/div/div/label[1]");
    private By emailIsRequired = By.xpath("//body//div[@id='root']//div//div//div//div[1]//label[2]");
    private By passwordIsRequired = By.xpath("//body/div[@id='root']/div[1]/div[2]/form[1]/div[1]/div[2]/label[2]");


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

    public WebElement getErrorText(){
        return driver.findElement(errorText);
    }

    public WebElement getErrorTextCredencialesInvalidas(){
        return driver.findElement(errorTextCredencialesInvalidas);
    }

    public WebElement getResultPageElement(){ return driver.findElement(resultsPageElement); }

    public WebElement getEmailIsRequired(){ return driver.findElement(emailIsRequired); }

    public WebElement getPasswordIsRequired(){ return driver.findElement(passwordIsRequired); }



    public void LogIn(String email, String password){
        Assert.assertTrue(getEmailInput().isDisplayed());
        Assert.assertTrue(getPasswordInput().isDisplayed());
        Assert.assertTrue(getSubmitButton().isDisplayed());
        getEmailInput().sendKeys(email);
        getPasswordInput().sendKeys(password);
        getSubmitButton().click();
        try {
            isVisibleInViewport(getResultPageElement());
            log.info("Sesión iniciada");
        } catch(Exception e){
            log.error("Error al iniciar sesion");
        }

    }
}
