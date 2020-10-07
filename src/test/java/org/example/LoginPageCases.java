package org.example;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import resources.Base;

public class LoginPageCases extends Base {


    @Test
    public void loginWithInvalidPassword() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://admin-dev.webaskme.com");
        LoginPage login = new LoginPage(driver);
        login.LogIn("erodriguez@effectussoftware.com", "password1");
        Thread.sleep(5000);
        Assert.assertTrue(isVisibleInViewport(login.getErrorTextCredencialesInvalidas()));
        System.out.println("Test completed");
    }

    @Test
    public void loginWithoutEmailAndPassword() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://admin-dev.webaskme.com");
        LoginPage login = new LoginPage(driver);
        login.getSubmitButton().click();
       //log.info("Botón ingresar clickeado");
        Thread.sleep(5000);
        Assert.assertTrue(isVisibleInViewport(login.getEmailIsRequired()));
        Assert.assertTrue(isVisibleInViewport(login.getPasswordIsRequired()));
        System.out.println("Test completed");
    }
}
