package org.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.ResultPage;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginPageCases extends LoginPage{

    @Test
    public void loginWithInvalidPassword() throws SQLException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        login.LogIn(set.getString(1), "incorrectpassword");
        Assert.assertTrue(isVisibleInViewport(login.getErrorTextCredencialesInvalidas()));
    }

    @Test
    public void loginWithoutEmailAndPassword(){
        LoginPage login = basePageNavigation();
        login.getSubmitButton().click();
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsRequired()));
        Assert.assertTrue(isVisibleInViewport(login.getPasswordIsRequired()));
    }

    @Test
    public void validateLoginFlow() throws SQLException {
        LoginPage login = basePageNavigation();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        while(set.next()){
            login.LogIn(set.getString(1), "password");
            ResultPage resultPage = new ResultPage(getDriver());
            Assert.assertTrue(isVisibleInViewport(resultPage.getResultTitle()));
            resultPage.logOut();
        }
    }

    @Test(dataProvider="getDataInvalidEmail")
    public void loginWithInvalidEmail(String email, String password){
        LoginPage login = basePageNavigation();
        login.LogIn(email, password);
        Assert.assertTrue(isVisibleInViewport(login.getErrorText()));
    }

    @Test
    public void loginWithInvalidEmailAndThenLoginWithValidEmail() throws SQLException {
        LoginPage login = basePageNavigation();
        login.LogIn("prueba@@prueba.com", "132456897");
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsInvalid()));
        login.getEmailInput().clear();
        login.getPasswordInput().clear();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        login.LogIn(set.getString(1), "password");
        ResultPage resultPage = new ResultPage(getDriver());
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
