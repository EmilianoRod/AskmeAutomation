package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.SurveyPage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SurveyPageTestCases extends SurveyPage{
    Statement st;

    @Test
    public void createASurvey(){
        SurveyPage surveyPage = basePageNavigation();
        surveyPage.getAddSurveyButton().click();
        int length = 10;
        String generatedString = RandomStringUtils.random(length, true, false);
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

//    @Test
//    public void createASurveyWithAnExistingName(){ //This is a bug
//        SurveyPage surveyPage = basePageNavigation();
//        surveyPage.getAddSurveyButton().click();
//        ResultSet set = null;
//        try {
//             set = st.executeQuery("select name from surveys");
//             set.next();
//             surveyPage.getNameInput().sendKeys(set.getString(1));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        surveyPage.getSaveButton().click();
//
//    }

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
        surveyPage.getDeleteAreaButtonLabel(rowSelectedInTable, labelNumberToDelete).click();
        Thread.sleep(2000);
        Assert.assertFalse(surveyPage.getAreaLabelInTable(rowSelectedInTable, labelNumberToDelete).getText().equals(nameAreaToDelete) && surveyPage.getBranchLabelInTable(rowSelectedInTable, labelNumberToDelete).getText().equals(nameBranchToDelete));
        Assert.assertTrue(surveyPage.getNumberOfLabels(rowSelectedInTable).size() == numberOfLabels); //Because getNumberOfLabels count assign button
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
        addQuestionToSurvey("Escala", true, false, 0, 0, "", false);
    }

    @Test
    public void addScaleQuestionToSurveyWithoutText(){
        addQuestionToSurvey("Escala", false,false, 0, 0,"", false);
    }

    @Test
    public void addFacesQuestionToSurveyWithoutSatisfactionIndex(){
        addQuestionToSurvey("Caras", true, false, 0, 0,"", false);
    }

    @Test
    public void addFacesQuestionToSurveyWithoutSatisfactionIndexAndText(){
        addQuestionToSurvey("Caras", false, false, 0, 0,"", false);
    }

    @Test
    public void addFacesQuestionToSurveyWithSatisfactionIndexAndWithoutText() throws InterruptedException {
        addQuestionToSurvey("Caras", false, true, 0, 0,"", false);
    }

    @Test
    public void addFacesQuestionToSurveyWithSatisfactionIndex() throws InterruptedException {
        addQuestionToSurvey("Caras", true, true, 0, 0,"", false);
    }

    @Test
    public void addMultipleOptionQuestionToSurveyWithTwoOptions() throws InterruptedException {
        addQuestionToSurvey("Multiple opción", true, false, 2, 0,"", false);
    }

    @Test
    public void addMultipleOptionQuestionToSurveyWithSixOptions() throws InterruptedException {
        addQuestionToSurvey("Multiple opción", true, false, 6, 0,"", false);
    }

    @Test
    public void addMultipleOptionQuestionToSurveyWithTwoEmptyOptions() throws InterruptedException {
        addQuestionToSurvey("Multiple opción", true, false, 0, 2,"", false);
    }

    @Test
    public void addMultipleOptionQuestionToSurveyWithThreeOptionsOneEmpty() throws InterruptedException {
        addQuestionToSurvey("Multiple opción", true, false, 2, 1,"", false);
    }

    @Test
    public void addAContactTypeQuestionWithSubtypeIDWithoutDescription(){
        addQuestionToSurvey("Contacto", true, false, 0, 0,"ID", false);
    }

    @Test
    public void addAContactTypeQuestionWithSubtypeIDWithDescription(){
        addQuestionToSurvey("Contacto", true, false, 0, 0,"ID", true);
    }

    @Test
    public void addAContactTypeQuestionWithSubtypeEmailWithoutDescription(){
        addQuestionToSurvey("Contacto", true, false, 0, 0,"Email", false);
    }

    @Test
    public void addAContactTypeQuestionWithSubtypeEmailWithDescription(){
        addQuestionToSurvey("Contacto", true, false, 0, 0,"Email", true);
    }

    @Test
    public void addAContactTypeQuestionWithSubtypeMobileNumberWithoutDescription(){
        addQuestionToSurvey("Contacto", true, false, 0, 0,"Telefono", false);
    }

    @Test
    public void addAContactTypeQuestionWithSubtypeMobileNumberWithDescription(){
        addQuestionToSurvey("Contacto", true, false, 0, 0,"Telefono", true);
    }

    @Test
    public void addATextQuestionToSurvey(){
        addQuestionToSurvey("Texto", true, false, 0, 0, "", false);
    }

    @Test
    public void editTitleQuestionScaleType(){
        editTitleQuestion("Escala");
    }

    @Test
    public void editTitleQuestionFacesType(){
        editTitleQuestion("Caras");
    }

    @Test
    public void editTitleQuestionMultipleOptionType(){
        editTitleQuestion("Multiple opción");
    }

    @Test
    public void editTitleQuestionContactType(){
        editTitleQuestion("Contacto");
    }

    @Test
    public void editTitleQuestionTextType(){
        editTitleQuestion("Texto");
    }

    @Test
    public void editDescriptionToAContactTypeQuestion(){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int i = 1;
        boolean esta = false;
        while(i < numberOfSurveys && !esta){
            surveyPage.getNameSurvey(i).click();
            int numberOfQuestions = surveyPage.getNumberOfRowsQuestionsTable().size();
            int j = 1;
            while(j < numberOfQuestions && !esta){
                if(surveyPage.getTypeOfQuestionInTable(j).getText().equals("Contacto")){
                    esta = true;
                    surveyPage.getThreePointsQuestionButton(j).click();
                    surveyPage.getOptionsInThreePointsDropDownQuestionTable(1).click();
                    surveyPage.getDescriptionField().clear();
                    String generatedString = RandomStringUtils.random(30, true, false);
                    surveyPage.getDescriptionField().sendKeys(generatedString);
                    surveyPage.getSaveButton().click();
                    surveyPage.getThreePointsQuestionButton(j).click();
                    surveyPage.getOptionsInThreePointsDropDownQuestionTable(1).click();
                    Assert.assertTrue(surveyPage.getDescriptionField().getText().equals(generatedString));
                }
                j++;
            }
            if(!esta){
                surveyPage.getBackFromDetailSurveyToMainSurvey().click();
            }
            i++;
        }
    }

    @Test
    public void addDescriptionToAnExistentContactTypeQuestion(){ //not work correctly
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int i = 1;
        boolean esta = false;
        boolean haveDescription = false;
        while(i < numberOfSurveys && !esta && !haveDescription){
            surveyPage.getNameSurvey(i).click();
            int numberOfQuestions = surveyPage.getNumberOfRowsQuestionsTable().size();
            int j = 1;
            while(j < numberOfQuestions && !esta && !haveDescription){
                if(surveyPage.getTypeOfQuestionInTable(j).getText().equals("Contacto")){
                    esta = true;
                    surveyPage.getThreePointsQuestionButton(j).click();
                    surveyPage.getOptionsInThreePointsDropDownQuestionTable(1).click();
                    if(surveyPage.getDescriptionField().getText().isEmpty()){
                        haveDescription = true;
                        String generatedString = RandomStringUtils.random(30, true, false);
                        surveyPage.getDescriptionField().sendKeys(generatedString);
                        surveyPage.getSaveButton().click();
                        surveyPage.getThreePointsQuestionButton(j).click();
                        surveyPage.getOptionsInThreePointsDropDownQuestionTable(1).click();
                        Assert.assertTrue(surveyPage.getDescriptionField().getText().equals(generatedString));
                    }else{
                        surveyPage.getCancelEditQuestionButton().click();
                    }
                }
                j++;
            }
            if(!esta){
                surveyPage.getBackFromDetailSurveyToMainSurvey().click();
            }
            i++;
        }
    }

}
