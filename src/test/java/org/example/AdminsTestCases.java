package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AdminsPage;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class AdminsTestCases extends Base {
    Statement st;

    public AdminsPage basePageNavigation() throws SQLException, InterruptedException {
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        connect();
        st = connection.createStatement();
        ResultSet set = st.executeQuery("Select email from admin_users where email like 'erod%' and type = 'AdminUser::Company';");
        set.next();
        login.LogIn(set.getString(1), "password");
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getLateralMenuButtons(4).click();
        AdminsPage adminPage = new AdminsPage(getDriver());
        return adminPage;
    }

    @Test
    public void createCompanyAdmin() throws SQLException, InterruptedException {
        AdminsPage adminsPage = basePageNavigation();
        adminsPage.getNewAdminButton().click();
        Random rand = new Random();
        int random = rand.nextInt(9999);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        Thread.sleep(2000);
        adminsPage.getEmailInput().sendKeys(email);
        Thread.sleep(2000);
        adminsPage.getPasswordInput().sendKeys("password");
        Thread.sleep(2000);
        adminsPage.getConfirmPasswordInput().sendKeys("password");
        Thread.sleep(2000);
        adminsPage.getConfirmButton().click();
        boolean esta = false;
        System.out.println(adminsPage.getRowsTable().size());
        for(int row = 1 ; row < adminsPage.getRowsTable().size() ; row++) {
            System.out.println(adminsPage.getRowsAdminTable(row).getAttribute("value"));
        }

    }

}
