package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.Base;


public class LoginPage extends Base {

    public WebDriver driver;

    private By emailInput = By.xpath("//input[@name='email']");
    private By passwordInput = By.xpath("//input[@name='password']");
    private By submitButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/form[1]/button[1]");
    private By errorText = By.xpath("//label[contains(text(),'El email no es válido')]");
    private By errorTextCredencialesInvalidas = By.xpath("//label[contains(text(),'Credenciales invalidas')]");
    private By resultsPageElement = By.xpath("//body/div[@id='root']/div/div/label[1]");
    private By emailIsRequired = By.xpath("//body//div[@id='root']//div//div//div//div[1]//label[2]");
    private By emailIsInvalid = By.xpath("//body/div[@id='root']/div[1]/div[2]/form[1]/div[1]/div[1]/label[2]");
    private By passwordIsRequired = By.xpath("//body/div[@id='root']/div[1]/div[2]/form[1]/div[1]/div[2]/label[2]");


    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getEmailInput(){  return fluentWait(emailInput); }

    public WebElement getPasswordInput(){
        return fluentWait(passwordInput);
    }

    public WebElement getSubmitButton(){
        return fluentWait(submitButton);
    }

    public WebElement getErrorText(){
        return fluentWait(errorText);
    }

    public WebElement getErrorTextCredencialesInvalidas(){
        return fluentWait(errorTextCredencialesInvalidas);
    }

    public WebElement getResultPageElement(){ return fluentWait(resultsPageElement); }

    public WebElement getEmailIsRequired(){ return fluentWait(emailIsRequired); }

    public WebElement getPasswordIsRequired(){ return fluentWait(passwordIsRequired); }

    public WebElement getEmailIsInvalid(){ return fluentWait(emailIsInvalid); }



    public void LogIn(String email, String password){
        Assert.assertTrue(getEmailInput().isDisplayed());
        Assert.assertTrue(getPasswordInput().isDisplayed());
        Assert.assertTrue(getSubmitButton().isDisplayed());
        try {
            getEmailInput().sendKeys(email);
            getPasswordInput().sendKeys(password);
            getSubmitButton().click();
            isVisibleInViewport(getResultPageElement());
            log.info("Sesión iniciada");
        } catch(Exception e){
            log.error("Error al iniciar sesion");
        }
    }
}
