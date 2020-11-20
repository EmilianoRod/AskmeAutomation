package org.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.InfoUserPage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class InfoUserTestCases extends InfoUserPage{

    @Test
    public void seeMyUserInfo() {
        InfoUserPage infoPage = basePageNavigation();
        ResultSet set = null;
        String email = "";
        try {
            set = st.executeQuery("Select email from admin_users where email like 'erod%' and type = 'AdminUser::Company';");
            set.next();
            email = set.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Assert.assertTrue(infoPage.getMyEmailText().getText().equals(email));
    }


    @Test
    public void editMyEmail(){
        InfoUserPage infoPage = basePageNavigation();
        infoPage.getEditButton().click();
        Random rand = new Random();
        int random = rand.nextInt(1000);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getPasswordinput().sendKeys("password");
        infoPage.getconfirmPasswordinput().sendKeys("password");
        infoPage.getSaveButton().click();
        Assert.assertTrue(infoPage.getMyEmailText().getText().contentEquals(email));
    }

    @Test
    public void editMyInfoIncorrectly(){
        InfoUserPage infoPage = basePageNavigation();
        infoPage.getEditButton().click();
        Random rand = new Random();
        int random = rand.nextInt(1000);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getPasswordinput().sendKeys("password");
        infoPage.getconfirmPasswordinput().sendKeys("password1");
        infoPage.getSaveButton().click();
        Assert.assertTrue(infoPage.getPasswordsNotMatch().isDisplayed());
    }

// https://effectus.atlassian.net/jira/software/projects/AM/boards/19/backlog?selectedIssue=AM-294
//    @Test
//    public void editMyEmailWithoutPassword(){
//        InfoUserPage infoPage = basePageNavigation();
//        infoPage.getEditButton().click();
//        Random rand = new Random();
//        int random = rand.nextInt(1000);
//        String email = "erodriguez+" + random + "@effectussoftware.com";
//        infoPage.getEmailinput().clear();
//        infoPage.getEmailinput().sendKeys(email);
//        infoPage.getSaveButton().click();
//        Assert.assertTrue();
//    }

    @Test(dataProvider="getDataInvalidEmail")
    public void editMyInfoWithInvalidEmail(String email, String password){
        InfoUserPage infoPage = basePageNavigation();
        infoPage.getEditButton().click();
        infoPage.getEmailinput().clear();
        infoPage.getEmailinput().sendKeys(email);
        infoPage.getPasswordinput().sendKeys(password);
        infoPage.getconfirmPasswordinput().sendKeys(password);
        infoPage.getSaveButton().click();
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

        data[3][0]="erodriguez#effectussoftware.com";
        data[3][1]="password";

        return data;
    }
}
