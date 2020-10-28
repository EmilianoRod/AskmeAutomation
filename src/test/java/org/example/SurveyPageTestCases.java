package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.ResultPage;
import pageObjects.SurveyPage;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class SurveyPageTestCases extends Base{
    Statement st;
    public SurveyPage basePageNavigation(){
        WebDriver driver = getDriver();
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        connect();
        try {
            st = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet set = null;
        try {
            set = st.executeQuery("Select email from admin_users where email like 'erod%' and type = 'AdminUser::Company';");
            set.next();
            login.LogIn(set.getString(1), "password");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getLateralMenuButtons(3).click();
        SurveyPage surveyPage = new SurveyPage(getDriver());
        return surveyPage;
    }


    @Test
    public void createASurvey(){
        SurveyPage surveyPage = basePageNavigation();
        surveyPage.getAddSurveyButton().click();
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        surveyPage.getNameInput().sendKeys(generatedString);
        surveyPage.getSaveButton().click();
        surveyPage.getBackFromDetailSurveyToMainSurvey().click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int rowNum = surveyPage.getRowsTable().size();
        boolean esta = false;
        for(int i = 1 ; i <= rowNum ; i++){
            if(surveyPage.getRowsTable(i).getText().equals(generatedString)){
                esta = true;
            }
        }
        Assert.assertTrue(esta);
    }

    @Test
    public void createASurveyWithAnExistingName(){ //This is a bug
        SurveyPage surveyPage = basePageNavigation();
        surveyPage.getAddSurveyButton().click();
        ResultSet set = null;
        try {
             set = st.executeQuery("select name from surveys");
             set.next();
             surveyPage.getNameInput().sendKeys(set.getString(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        surveyPage.getSaveButton().click();

    }

    @Test
    public void editASurvey(){
        SurveyPage surveyPage = basePageNavigation();
        surveyPage.getThreePointsButton(1).click(); //click on the menu of the first survey
        surveyPage.getEditButtonInThreePoint().click();
        surveyPage.getNameInput().clear();
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        surveyPage.getNameInput().sendKeys(generatedString);
        surveyPage.getSaveButton().click();
        surveyPage.getTable();
        int rowNum = surveyPage.getRowsTable().size();
        boolean esta = false;
        for(int i = 1 ; i <= rowNum ; i++){
            if(surveyPage.getRowsTable(i).getText().equals(generatedString)){
                esta = true;
                break;
            }
        }
        Assert.assertTrue(esta);
    }

    @Test
    public void createASurveyWithoutName(){
        SurveyPage surveyPage = basePageNavigation();
        surveyPage.getAddSurveyButton().click();
        surveyPage.getSaveButton().click();
        Assert.assertTrue(isVisibleInViewport(surveyPage.getNameIsRequiredLabel()));
    }

    @Test
    public void addAreaToSurvey() throws InterruptedException {
        SurveyPage surveyPage = basePageNavigation();
        Thread.sleep(2000);
        int RowsDropdownNum = surveyPage.getRowsLabelsAreaDropdown().size();
        int surveysNum = surveyPage.getRowsTable().size();
        int rowTable = (int)(Math.random() * (surveysNum - 1 + 1) + 1);
        int random_int2 = (int)(Math.random() * (RowsDropdownNum - 1 + 1) + 1);
        int numberOfLabels = surveyPage.getNumberOfLabels(rowTable).size();
        surveyPage.getAssignAreaButton(rowTable, numberOfLabels).click();
        Thread.sleep(2000);
        String nameAreaToAdd = surveyPage.getLabelsAreaDropdown(random_int2).getText();
        surveyPage.getLabelsAreaDropdown(random_int2).click();
        Thread.sleep(2000);
        int rowNum2 = surveyPage.getRowsLabelsAreaDropdown().size();
        int random_int3 = (int)(Math.random() * (rowNum2 - 1 + 1) + 1);
        String nameBranchToAdd = surveyPage.getLabelsAreaDropdown(random_int3).getText();
        surveyPage.getLabelsAreaDropdown(random_int3).click();
        Actions action = new Actions(surveyPage.driver); //click outside dropdown to close it
        action.moveByOffset(200,45).click().perform();
        action.moveByOffset(200,300).click().perform();
        System.out.println(numberOfLabels);
        System.out.println(surveyPage.getNumberOfLabels(rowTable).size());
        Thread.sleep(1000);
        Assert.assertTrue(numberOfLabels == (surveyPage.getNumberOfLabels(rowTable).size() - 1));
        Assert.assertTrue(surveyPage.getAreaLabelInTable(rowTable, numberOfLabels).getText().equals(nameAreaToAdd));
        Assert.assertTrue(surveyPage.getBranchLabelInTable(rowTable, numberOfLabels).getText().equals(nameBranchToAdd));
    }


}
