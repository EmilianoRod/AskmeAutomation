package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.base;
import java.io.IOException;
import java.time.Duration;


public class LoginPageTestCases extends base{
    LoginPage login;
    ResultPage resultPage;
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void initialize() throws IOException {
        driver = initializeDriver();
        //wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        basePageNavigation();
    }
    public void basePageNavigation(){
        driver.get(prop.getProperty("url"));
        login = new LoginPage(driver);
    }
    
   @Test(dataProvider="getData")
   public void validateLoginFlow(String email, String password) throws InterruptedException {
         login.LogIn(email, password);
        resultPage = new ResultPage(driver);
        Assert.assertTrue(isVisibleInViewport(resultPage.getResultTitle()));
        resultPage.logOut();
        Thread.sleep(1000);
   }

   @Test(dataProvider="getDataInvalidEmail")
   public void loginWithInvalidEmail(String email, String password) throws InterruptedException {
        login.LogIn(email, password);
        Assert.assertTrue(isVisibleInViewport(login.getErrorText()));
        driver.navigate().refresh();
        Thread.sleep(1000);
        System.out.println("Test completed");
   }

   @Test
   public void loginWithInvalidPassword() throws InterruptedException {
        login.LogIn("erodriguez@effectussoftware.com", "password1");
        Thread.sleep(1000);
        Assert.assertTrue(isVisibleInViewport(login.getErrorTextCredencialesInvalidas()));
        driver.navigate().refresh();
        System.out.println("Test completed");
   }

   @Test
   public void loginWithoutEmailAndPassword(){
        login.getSubmitButton().click();
        log.info("Botón ingresar clickeado");
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsRequired()));
        Assert.assertTrue(isVisibleInViewport(login.getPasswordIsRequired()));
        System.out.println("Test completed");
   }


    @AfterMethod
    public void teardown() {
        if(driver!=null){
            driver.quit();
            driver = null;
        }
        System.out.println("Closing the browser");
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