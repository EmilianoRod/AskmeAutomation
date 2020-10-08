package org.example;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.Base;


public class LoginPageCases extends Base {

    @BeforeMethod
    public LoginPage basePageNavigation(){
        WebDriver driver = getDriver();
        driver.get("https://admin-dev.webaskme.com");
        LoginPage login = new LoginPage(driver);
        return login;
    }


    @Test
    public void loginWithInvalidPassword() throws InterruptedException {
        LoginPage login = basePageNavigation();
        login.LogIn("erodriguez@effectussoftware.com", "password1");
        Thread.sleep(5000);
        Assert.assertTrue(isVisibleInViewport(login.getErrorTextCredencialesInvalidas()));
        System.out.println("Test completed");
    }

    @Test
    public void loginWithoutEmailAndPassword() throws InterruptedException {
        LoginPage login = basePageNavigation();
        login.getSubmitButton().click();
        //log.info("Botón ingresar clickeado");
        Thread.sleep(2000);
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsRequired()));
        Assert.assertTrue(isVisibleInViewport(login.getPasswordIsRequired()));
        System.out.println("Test completed");
    }

    @Test(dataProvider="getData")
    public void validateLoginFlow(String email, String password) throws InterruptedException {
        LoginPage login = basePageNavigation();
        login.LogIn(email, password);
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2000);
        Assert.assertTrue(isVisibleInViewport(resultPage.getResultTitle()));
        resultPage.logOut();
    }

    @Test(dataProvider="getDataInvalidEmail")
    public void loginWithInvalidEmail(String email, String password) throws InterruptedException {
        LoginPage login = basePageNavigation();
        login.LogIn(email, password);
        Assert.assertTrue(isVisibleInViewport(login.getErrorText()));
        getDriver().navigate().refresh();
        Thread.sleep(5000);
        System.out.println("Test completed");
    }


    @DataProvider
    public Object[][] getData(){
        // Row stands for how many different data types test should run
        //coloumn stands for how many values per each test

        // Array size is 2
        // 0,1
        Object[][] data=new Object[2][2];
        //0th row
        data[0][0]="erodriguez@effectussoftware.com";
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
