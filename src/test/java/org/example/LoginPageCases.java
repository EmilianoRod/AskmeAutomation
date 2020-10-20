package org.example;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginPageCases extends Base {

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
    public void loginWithInvalidPassword() throws InterruptedException, SQLException {
        LoginPage login = basePageNavigation();
        Thread.sleep(2000);
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        Thread.sleep(2000);
        login.LogIn(set.getString(1), "incorrectpassword");
        Thread.sleep(2000);
        Assert.assertTrue(isVisibleInViewport(login.getErrorTextCredencialesInvalidas()));
        Thread.sleep(2000);
        System.out.println("Test completed");
    }

    @Test
    public void loginWithoutEmailAndPassword() throws InterruptedException, SQLException {
        LoginPage login = basePageNavigation();
        login.getSubmitButton().click();
        log.info("Botón ingresar clickeado");
        Thread.sleep(2000);
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsRequired()));
        Assert.assertTrue(isVisibleInViewport(login.getPasswordIsRequired()));
        System.out.println("Test completed");
    }

    @Test
    public void validateLoginFlow() throws InterruptedException, SQLException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        while(set.next()){
            login.LogIn(set.getString(1), "password");
            ResultPage resultPage = new ResultPage(getDriver());
            Thread.sleep(2000);
            Assert.assertTrue(isVisibleInViewport(resultPage.getResultTitle()));
            resultPage.logOut();
        }
    }

    @Test(dataProvider="getDataInvalidEmail")
    public void loginWithInvalidEmail(String email, String password) throws SQLException, InterruptedException {
        LoginPage login = basePageNavigation();
        login.LogIn(email, password);
        Assert.assertTrue(isVisibleInViewport(login.getErrorText()));
        getDriver().navigate().refresh();
        log.info("Test completed");
    }

    @Test
    public void loginWithInvalidEmailAndThenLoginWithValidEmail() throws InterruptedException, SQLException {
        LoginPage login = basePageNavigation();
        login.LogIn("prueba@@prueba.com", "132456897");
        Thread.sleep(1000);
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsInvalid()));
        login.getEmailInput().clear();
        login.getPasswordInput().clear();
        login.LogIn("erodriguez@effectussoftware.com", "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2000);
        Assert.assertTrue(isVisibleInViewport(resultPage.getResultTitle()));
    }




    @DataProvider
    public Object[] getData(){
        // Row stands for how many different data types test should run
        //coloumn stands for how many values per each test

        // Array size is 2
        // 0,1
        Object[][] data=new Object[2][2];
        //0th row
        data[0][0]= "erodriguez@effectussoftware.com";
        data[0][1]="password";
        //1st row
        data[1][0]="erodriguez+1@effectussoftware.com";
        data[1][1]="password";

        return data;
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
