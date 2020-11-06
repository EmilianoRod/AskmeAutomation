package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
        int rowNum = surveyPage.getRowsTableNumber().size();
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
        String generatedString = RandomStringUtils.random(10, true, false);
        surveyPage.getNameInput().sendKeys(generatedString);
        surveyPage.getSaveButton().click();
        surveyPage.getTable();
        int rowNum = surveyPage.getRowsTableNumber().size();
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
    public void addAreaToSurvey(){
        SurveyPage surveyPage = basePageNavigation();
        int surveysNum = surveyPage.getRowsTableNumber().size();
        int rowTable = (int)(Math.random() * (surveysNum - 1 + 1) + 1);
        int numberOfLabels = surveyPage.getNumberOfLabels(rowTable).size();
        surveyPage.getAssignAreaButton(rowTable).click();
        int RowsDropdownNum = surveyPage.getRowsLabelsAreaDropdown().size();
        int random_int2 = (int)(Math.random() * (RowsDropdownNum - 1 + 1) + 1);
        String nameAreaToAdd = surveyPage.getLabelsAreaDropdown(random_int2).getText();
        surveyPage.getLabelsAreaDropdown(random_int2).click();
        int rowNum2 = surveyPage.getRowsLabelsAreaDropdown().size();
        int random_int3 = (int)(Math.random() * (rowNum2 - 1 + 1) + 1);
        String nameBranchToAdd = surveyPage.getLabelsAreaDropdown(random_int3).getText();
        surveyPage.getLabelsAreaDropdown(random_int3).click();
        Actions action = new Actions(surveyPage.driver); //click outside dropdown to close it
        if(rowTable > 8){
            action.moveByOffset(200,500).click().perform();
        }else{
            action.moveByOffset(200,45).click().perform();
        }
        Assert.assertTrue(numberOfLabels == (surveyPage.getNumberOfLabels(rowTable).size() - 1));
        Assert.assertTrue(surveyPage.getAreaLabelInTable(rowTable, numberOfLabels).getText().equals(nameAreaToAdd));
        Assert.assertTrue(surveyPage.getBranchLabelInTable(rowTable, numberOfLabels).getText().equals(nameBranchToAdd));
    }

    @Test
    public void unassingASurveyArea() throws InterruptedException {
        SurveyPage surveyPage = basePageNavigation();
        int surveysNum = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (surveysNum - 1 + 1) + 1);
        int numberOfLabels = surveyPage.getNumberOfLabels(rowSelectedInTable).size() - 1; //Because getNumberOfLabels count assign button
        while(numberOfLabels < 1){
            rowSelectedInTable = (int)(Math.random() * (surveysNum - 1 + 1) + 1);
            numberOfLabels = surveyPage.getNumberOfLabels(rowSelectedInTable).size() - 1; //Because getNumberOfLabels count assign button
        }
        int labelNumberToDelete = (int)(Math.random() * (numberOfLabels - 1 + 1) + 1);
        String nameAreaToDelete = surveyPage.getAreaLabelInTable(rowSelectedInTable, labelNumberToDelete).getText();
        String nameBranchToDelete = surveyPage.getBranchLabelInTable(rowSelectedInTable, numberOfLabels).getText();
        Thread.sleep(5000);
        surveyPage.getDeleteAreaButtonLabel(rowSelectedInTable, labelNumberToDelete).click();
        Thread.sleep(5000);
        Assert.assertTrue(surveyPage.getNumberOfLabels(rowSelectedInTable).size() == numberOfLabels); //Because getNumberOfLabels count assign button
       // Assert.assertFalse(surveyPage.getAreaLabelInTable(rowSelectedInTable, labelNumberToDelete).getText().equals(nameAreaToDelete) && surveyPage.getBranchLabelInTable(rowSelectedInTable, labelNumberToDelete).getText().equals(nameBranchToDelete));
    }

    @Test
    public void assignAllTheAreasPosiblesInASurvey() throws InterruptedException {
        SurveyPage surveyPage = basePageNavigation();
        Thread.sleep(2000);
        int surveysNum = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (surveysNum - 1 + 1) + 1);
        Thread.sleep(2000);
        surveyPage.getAssignAreaButton(rowSelectedInTable).click();
        int numberOfLabels = surveyPage.getRowsLabelsAreaDropdown().size();
        int numberOfLabelsInSelectedRow = surveyPage.getNumberOfLabels(rowSelectedInTable).size() - 1; //Because getNumberOfLabels count assign button
        for(int j = 1 ; j <= numberOfLabels; j++ ){
            String nameAreaToAdd = surveyPage.getLabelsAreaDropdown(1).getText();
            surveyPage.getLabelsAreaDropdown(1).click();
            int rowsBranchesDropdown = surveyPage.getRowsLabelsAreaDropdown().size();
            if(rowsBranchesDropdown == 1){
                surveyPage.getLabelsBranchesDropdown(1).click();
                numberOfLabelsInSelectedRow++;
                Thread.sleep(2000);
                Assert.assertTrue(surveyPage.getNumberOfLabels(rowSelectedInTable).size() - 1 == numberOfLabelsInSelectedRow);
            }else if(rowsBranchesDropdown == 2) {
                for (int k = 1; k <= rowsBranchesDropdown; k++) {
                    surveyPage.getLabelsBranchesDropdown(1).click();
                    numberOfLabelsInSelectedRow++;
                    Thread.sleep(2000);
                    Assert.assertTrue(surveyPage.getNumberOfLabels(rowSelectedInTable).size() - 1 == numberOfLabelsInSelectedRow);
                    if(k==1) {
                        surveyPage.getLabelsAreaDropdown(1).click();
                    }
                }
            }else if(rowsBranchesDropdown == 3){
                for(int l = 1 ; l <= rowsBranchesDropdown ; l++ ){
                    surveyPage.getLabelsBranchesDropdown(1).click();
                    Thread.sleep(2000);
                    numberOfLabelsInSelectedRow++;
                    Assert.assertTrue(surveyPage.getNumberOfLabels(rowSelectedInTable).size() - 1 == numberOfLabelsInSelectedRow);
                    if(l == 1 || l == 2 ) {
                            surveyPage.getLabelsAreaDropdown(1).click();
                    }
                }
            }
        }
    }

    @Test
    public void addScaleQuestionToSurvey(){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        surveyPage.getAddNewQuestion().click();
        String generatedString = RandomStringUtils.random(10, true, false);
        surveyPage.getQuestionTextInput().sendKeys(generatedString);
        surveyPage.getSaveQuestionButton().click();
        int numberOfRowsQuestionTable = surveyPage.getNumberOfRowsQuestionsTable().size();
        boolean esta = false;
        for(int i = 1 ; i <= numberOfRowsQuestionTable ; i++){
            if(surveyPage.getQuestionColumnInQuestionsTable(i).getText().equals(generatedString)){
                esta = true;
                break;
            }
        }
        Assert.assertTrue(esta);
    }

    @Test
    public void addScaleQuestionToSurveyWithoutText(){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        surveyPage.getAddNewQuestion().click();
        surveyPage.getSaveQuestionButton().click();
        Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionTextIsRequiredLabel()));
    }

    @Test
    public void addFacesQuestionToSurveyWithoutSatisfactionIndex(){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        surveyPage.getAddNewQuestion().click();
        String generatedString = RandomStringUtils.random(10, true, false);
        surveyPage.getQuestionTextInput().sendKeys(generatedString);
        surveyPage.getTypeOfQuestionsDropDown().click();
        surveyPage.getOptionsInTypeOfQuestionsDropdown(2).click();
        surveyPage.getSaveQuestionButton().click();
        int numberOfRowsQuestionTable = surveyPage.getNumberOfRowsQuestionsTable().size();
        boolean esta = false;
        int i = 1;
        while(i <= numberOfRowsQuestionTable){
            if(surveyPage.getQuestionColumnInQuestionsTable(i).getText().equals(generatedString)){
                esta = true;
                break;
            }
            i++;
        }
        Assert.assertTrue(esta);
        if(rowSelectedInTable >= 9){
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            WebElement element = surveyPage.getQuestionColumnInQuestionsTable(i);
            js.executeScript("arguments[0].scrollIntoView();", element);
        }
        Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionColumnInQuestionsTable(i)));
    }

    @Test
    public void addFacesQuestionToSurveyWithoutSatisfactionIndexAndText(){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        surveyPage.getAddNewQuestion().click();
        surveyPage.getTypeOfQuestionsDropDown().click();
        surveyPage.getOptionsInTypeOfQuestionsDropdown(2).click();
        surveyPage.getSaveQuestionButton().click();
        Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionTextIsRequiredLabel()));
    }

    @Test
    public void addFacesQuestionToSurveyWithSatisfactionIndexAndWithoutText(){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        surveyPage.getAddNewQuestion().click();
        surveyPage.getTypeOfQuestionsDropDown().click();
        surveyPage.getOptionsInTypeOfQuestionsDropdown(2).click();
        int satisfactionIndex = (int)(Math.random() * (10 - 1 + 1) + 1);
        String index = String.valueOf(satisfactionIndex);
        surveyPage.getSatisfactionIndexInput().sendKeys(index);
        surveyPage.getSaveQuestionButton().click();
        Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionTextIsRequiredLabel()));
    }


}
