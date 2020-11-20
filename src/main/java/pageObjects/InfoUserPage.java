package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InfoUserPage extends Base {
   public Statement st;

    public WebDriver driver;
    public InfoUserPage(WebDriver driver){ this.driver = driver; }
    public InfoUserPage(){}

    private By myEmailText = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/label[2]");
    private By editButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/button[1]");
    private By emailInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/input[1]");
    private By passwordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[2]/input[1]");
    private By confirmPasswordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/input[1]");
    private By saveButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/button[1]");
    private By passwordsNotMatch = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/label[1]");
    private By emailInvalidMessage = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/label[2]");


    public WebElement getMyEmailText(){ return fluentWait(myEmailText); }
    public WebElement getEditButton(){ return fluentWait(editButton); }
    public WebElement getEmailinput(){ return fluentWait(emailInput); }
    public WebElement getPasswordinput(){ return fluentWait(passwordInput); }
    public WebElement getconfirmPasswordinput(){ return fluentWait(confirmPasswordInput); }
    public WebElement getSaveButton(){ return fluentWait(saveButton); }
    public WebElement getPasswordsNotMatch(){ return fluentWait(passwordsNotMatch); }
    public WebElement getEmailInvalidMessage(){ return fluentWait(emailInvalidMessage); }


    public InfoUserPage basePageNavigation(){
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        connect();
        ResultSet set = null;
        try {
            st = connection.createStatement();
            set = st.executeQuery("Select email from admin_users where email like 'erod%' and type = 'AdminUser::Company';");
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        Boolean displayed = false;
        do{
            try{
                set.next();
                login.LogIn(set.getString(1), "password");
                try{
                    displayed = login.getResultTitle().isDisplayed();
                } catch (Exception ignore){}
                if(!displayed){
                    login.getEmailInput().clear();
                    login.getPasswordInput().clear();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } while(!displayed);
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        return infoPage;
    }

}
