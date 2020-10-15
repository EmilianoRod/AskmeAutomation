package org.example;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.InfoUserPage;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class InfoUserTestCases extends Base {
    Statement st;

    public LoginPage basePageNavigation() throws SQLException {
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        connect();
        st = connection.createStatement();
        return login;
    }

    @Test
    public void seeMyUserInfo() throws InterruptedException, SQLException {
        LoginPage login = basePageNavigation();
        login.LogIn("erodriguez@effectussoftware.com", "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(1500);
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        Thread.sleep(1500);
        Assert.assertTrue(infoPage.getMyEmailText().getText().contains("erodriguez@effectussoftware.com"));
    }


    @Test
    public void editMyEmail() throws SQLException, InterruptedException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        login.LogIn(set.getString(1), "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2500);
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        Thread.sleep(1500);
        infoPage.getEditButton().click();
        Thread.sleep(1000);
        Random rand = new Random();
        int random = rand.nextInt(1000);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getPasswordinput().sendKeys("password");
        infoPage.getconfirmPasswordinput().sendKeys("password");
        infoPage.getSaveButton().click();
        Thread.sleep(1000);
      //  Assert.assertTrue(infoPage.getMyEmailText().getAttribute("value").contentEquals(email));
        Assert.assertTrue(infoPage.getMyEmailText().getText().contentEquals(email));
    }

    @Test
    public void editMyInfoIncorrectly() throws SQLException, InterruptedException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        login.LogIn(set.getString(1), "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2000);
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        Thread.sleep(1500);
        infoPage.getEditButton().click();
        Thread.sleep(1000);
        Random rand = new Random();
        int random = rand.nextInt(1000);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getPasswordinput().sendKeys("password");
        infoPage.getconfirmPasswordinput().sendKeys("password1");
        infoPage.getSaveButton().click();
        Thread.sleep(1500);
        Assert.assertTrue(isVisibleInViewport(infoPage.getPasswordsNotMatch()));
    }

 //https://effectus.atlassian.net/jira/software/projects/AM/boards/19/backlog?selectedIssue=AM-294
    @Test
    public void editMyEmailWithoutPassword() throws SQLException, InterruptedException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        login.LogIn(set.getString(1), "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2000);
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        Thread.sleep(1500);
        infoPage.getEditButton().click();
        Thread.sleep(1000);
        Random rand = new Random();
        int random = rand.nextInt(1000);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getSaveButton().click();
    }

    @Test(dataProvider="getDataInvalidEmail")
    public void editMyInfoWithInvalidEmail(String email, String password) throws SQLException, InterruptedException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        login.LogIn(set.getString(1), "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2000);
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        Thread.sleep(1500);
        infoPage.getEditButton().click();
        Thread.sleep(1000);
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getPasswordinput().sendKeys(password);
        infoPage.getconfirmPasswordinput().sendKeys(password);
        infoPage.getSaveButton().click();
        Thread.sleep(1000);
        Assert.assertTrue(isVisibleInViewport(infoPage.getEmailInvalidMessage()));
    }





    @DataProvider
    public Object[][] getDataInvalidEmail(){
        // Row stands for how many different data types test should run
        //coloumn stands for how many values per each test

        // Array size is 2
        // 0,1
        Object[][] data=new Object[4][2];
        //0th row
        data[0][0]="erodriguez@@effectussoftware.com";
        data[0][1]="password";
        //1st row
        data[1][0]="erodriguez@effectussoftware..com";
        data[1][1]="password";

        data[2][0]="erodriguez@effectussoftware..c";
        data[2][1]="password";

        data[3][0]="erodriguez#effectussoftware..com";
        data[3][1]="password";

        return data;
    }
}
