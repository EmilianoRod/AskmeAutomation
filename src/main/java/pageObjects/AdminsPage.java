package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminsPage extends Base {

    Statement st;
    public WebDriver driver;
    public AdminsPage(WebDriver driver){ this.driver = driver; }
    public AdminsPage(){}

    private By newAdminButton =  By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/button[1]");
    private By emailInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/input[1]");
    private By passwordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/input[1]");
    private By confirmButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/button[1]");
    private By confirmPasswordInput = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[2]/div[2]/input[1]");
    private By table = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[3]/table[1]");
    private By tableRows = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[3]/table[1]/tbody[1]/tr");
    private By openDropdownAcceso = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/form[1]/div[1]/div[2]/div[1]/div[1]/div[1]");
    private By emailIsRequiredLabel = By.xpath("//label[contains(text(),'Campo requerido')]");
    private By emailInvalidLabel = By.xpath("//label[contains(text(),'El email no es válido')]");
    private By passwordsNotMatch = By.xpath("//label[contains(text(),'Las contraseñas no coinciden')]");
    private By confirmDeleteAdmin = By.xpath("//div[@class='sc-oUDcU cnVYtu']/button[2]");


    public WebElement getRowColumnAdminTable(int numRow, int numColumn){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+numRow+"]/td["+numColumn+"]"));}
    public WebElement getThreePointsButton(int numRow){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+numRow+"]/td[3]/div/button")); }
    public WebElement getOptionsEditOrDelete(int i){return fluentWait(By.xpath("//div[@class='sc-pJkiN csfYLt']/div["+i+"]")); }  //1 for edit, 2 for delete
    public WebElement getNewAdminButton(){ return fluentWait(newAdminButton);}
    public WebElement getEmailInput(){ return fluentWait(emailInput); }
    public WebElement getPasswordInput(){ return fluentWait(passwordInput); }
    public WebElement getConfirmButton(){ return fluentWait(confirmButton); }
    public WebElement getConfirmPasswordInput(){ return fluentWait(confirmPasswordInput); }
    public WebElement getAdminsTable(){ return fluentWait(table); }
    public List<WebElement> getRowsTable(){ return fluentWaitForMultipleElements(tableRows); }
    public WebElement getOpenDropdownAcceso(){ return fluentWait(openDropdownAcceso); }
    public WebElement getOptionsAccessDropdown(int i){ return fluentWait(By.xpath("//ul[@class='MuiList-root sc-fzoPby cSPfCG question MuiList-padding']/div["+i+"]")); } //i=1 for Admin, i=2 for visualizator
    public WebElement getEmailIsRequiredLabel(){return fluentWait(emailIsRequiredLabel); }
    public WebElement getEmailInvalidLabel(){return fluentWait(emailInvalidLabel); }
    public WebElement getPasswordsNotMatchLabel(){return fluentWait(passwordsNotMatch); }
    public WebElement getConfirmDeleteAdmin(){ return fluentWait(confirmDeleteAdmin); }

    public AdminsPage basePageNavigation(){
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        connect();
        ResultSet set = null;
        try{
            st = connection.createStatement();
            set = st.executeQuery("Select email from admin_users where email like 'erod%' and type = 'AdminUser::Company';");
        }catch(SQLException e){
            e.printStackTrace();
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
                }catch (Exception e){}
            } while(!displayed);
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getLateralMenuButtons(4).click();
        AdminsPage adminPage = new AdminsPage(getDriver());
        return adminPage;
        }
    }

