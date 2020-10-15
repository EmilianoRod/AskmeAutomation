package org.example;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pageObjects.AdminsPage;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminsTestCases extends Base {
    Statement st;

    public AdminsPage basePageNavigation() throws SQLException, InterruptedException {
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        connect();
        st = connection.createStatement();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%';");
        set.next();
        Thread.sleep(2000);
        login.LogIn(set.getString(1), "password");
        Thread.sleep(2000);
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getLateralMenuButtons(4).click();
        Thread.sleep(2000);
        AdminsPage adminPage = new AdminsPage(getDriver());
        Thread.sleep(2000);
        return adminPage;
    }

    @Test
    public void test() throws SQLException, InterruptedException {
        AdminsPage adminsPage = basePageNavigation();
    }
}
