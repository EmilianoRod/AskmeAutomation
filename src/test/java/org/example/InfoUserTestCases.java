package org.example;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.InfoUserPage;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.Base;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class InfoUserTestCases extends Base {

    @BeforeMethod
    public LoginPage basePageNavigation(){
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        return login;
    }

    @Test
    public void seeMyUserInfo() throws InterruptedException {
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
    public void editMyEmail() throws InterruptedException {
        LoginPage login = basePageNavigation();
        login.LogIn("erodriguez@effectussoftware.com", "password");
        ResultPage resultPage = new ResultPage(getDriver());
        Thread.sleep(2500);
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        Thread.sleep(1500);
        infoPage.getEditButton().click();
        Thread.sleep(1000);
        String email = infoPage.getEmailinput().getAttribute("value");
        char[] array = email.toCharArray();
        infoPage.getEmailinput().clear();
        int i = 0;
        while(array[i] != '@'){
            String s = String.valueOf(array[i]);
            infoPage.getEmailinput().sendKeys(s);
            i++;
        }
        Random rand = new Random();
        int random = rand.nextInt(1000);
        infoPage.getEmailinput().sendKeys("+" + random + "@");
        i++;
        while(i < array.length){
            String s = String.valueOf(array[i]);
            infoPage.getEmailinput().sendKeys(s);
            Thread.sleep(1000);
            i++;
        }
        String emailEdit = infoPage.getEmailinput().getAttribute("value");
        infoPage.getPasswordinput().sendKeys("password");
        infoPage.getconfirmPasswordinput().sendKeys("password");
        infoPage.getSaveButton().click();
        Thread.sleep(1000);
        Assert.assertTrue(infoPage.getEmailinput().getAttribute("value").contentEquals(emailEdit));
    }

}
