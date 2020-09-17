package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import resources.base;

import java.io.IOException;
import java.time.Duration;

public class LoginPageTestCases extends base {
    public static Logger log = LogManager.getLogger(base.class.getName());
    public WebDriver driver;
    LoginPage login;
    WebDriverWait wait;

    @BeforeTest
    public void initialize() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        basePageNavigation();
    }

    public void basePageNavigation() throws IOException{
        driver.get(prop.getProperty("url"));
        login = new LoginPage(driver);
    }

   @Test
   public void validateLoginFlow(){

   }

}