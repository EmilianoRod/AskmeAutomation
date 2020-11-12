package pageObjects;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import resources.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SurveyPage extends Base{
    public WebDriver driver;
    public SurveyPage(WebDriver driver){
        this.driver = driver;
    }
    Statement st;


    final private By addSurveyButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/button[1]");
    final private By nameInput = By.tagName("input");
    final private By saveButton = By.xpath("//button[contains(text(),'Guardar')]");
    final private By backFromDetailSurveyToMainSurvey = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/button[1]/img[1]");
    final private By table = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[3]/table[1]");
    final private By tableRows = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr");
    final private By editButtonInThreePoint = By.xpath("//body/div[@id='transitions-popper']/div[1]/div[1]");
    final private By nameIsRequiredLabel = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/label[2]");
    final private By rowsLabelsAreaDropdown = By.xpath("//*[@class='MuiButtonBase-root MuiListItem-root sc-fznNTe fnbSrb MuiListItem-gutters MuiListItem-button']");
    final private By rowsLabelsBranchesDropdown = By.xpath("//body/div[3]/div[3]/div[1]/ul[1]/div");
    final private By surveyButtonLateralMenu = By.xpath("//body/div[@id='root']/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/button[3]/label[1]");
    final private By dropdownSelectAreas = By.xpath("//body/div[3]/div[3]");
    final private By inputSelectAreas = By.xpath("//body[1]/div[3]/div[3]/div[1]/div[2]/input[1]");
    final private By newQuestion = By.xpath("//button[@class='sc-qWgaf bCsuiW' or contains(text(), '+ Nueva pregunta')]");
    final private By newQuestion2 = By.xpath("//button[@class='sc-fzqBkg hzvDQH']");
    final private By questionTextInput = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/input[1]");
    final private By saveQuestionButton = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[3]/button[1]");
    final private By rowsQuestionsTable = By.tagName("tr");
    final private By questionTextIsRequiredLabel = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[3]/label[1]");
    final private By typeOfQuestionsDropDown =  By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]");
    final private By satisfactionIndexInput = By.xpath("//input[@class='sc-AxhUy kbFuQf question']");
    final private By addOptionButton = By.xpath("//button[@class='sc-pbIaG jGPUoW margin']");
    final private By shouldHaveBetweenTwoAndSixAnswersLabel = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[3]/label[1]");
    final private By descriptionField = By.xpath("//textarea[@class='sc-AxgMl dxZxmV questionName']");
    final private By subTypeQuestionType = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[4]/div[1]/div[1]/div[1]");


    public SurveyPage() {
    }


    //final private By questionColumnInQuestionsTable = By.xpath("//tbody/tr["+i+"]/td[2]");

    public WebElement getAddSurveyButton(){ return fluentWait(addSurveyButton); }
    public WebElement getNameInput(){ return fluentWait(nameInput); }
    public WebElement getSaveButton(){return fluentWait(saveButton); }
    public WebElement getBackFromDetailSurveyToMainSurvey(){ return fluentWait(backFromDetailSurveyToMainSurvey); }
    public List<WebElement> getRowsTableNumber(){ return fluentWaitForMultipleElements(tableRows); }
    public WebElement getRowsTable(int i){ return fluentWait(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr["+i+"]/td[1]"));} //i=1 to cant of rows
    public WebElement getThreePointsButton(int i){ return fluentWait(By.xpath("//tbody/tr["+i+"]/td[3]/div[1]/button[1]")); }
    public WebElement getEditButtonInThreePoint(){ return fluentWait(editButtonInThreePoint); }
    public WebElement getTable(){ return fluentWait(table); }
    public WebElement getNameIsRequiredLabel(){ return fluentWait(nameIsRequiredLabel); }
    public WebElement getAssignAreaButton(int i){ return fluentWait(By.xpath("//tbody/tr["+i+"]/td[2]/div/div[1]/div[1]/button[1]")); } //i for row and
    public WebElement getLabelsAreaDropdown(int i){ return fluentWait(By.xpath("//ul[@class='MuiList-root sc-fzoPby cSPfCG MuiList-padding']/div["+i+"]"));}
    public WebElement getNameSurvey(int i){ return fluentWait(By.xpath("//tbody/tr["+i+"]/td[1]")); }
    public WebElement getLabelsBranchesDropdown(int i){ return fluentWait(By.xpath("//ul[@class='MuiList-root sc-fzoPby cSPfCG MuiList-padding']/child::div["+i+"]"));}
    public List<WebElement> getRowsLabelsAreaDropdown(){ return fluentWaitForMultipleElements(rowsLabelsAreaDropdown); }
    public List<WebElement> getRowsLabelsBranchesDropdown(){ return fluentWaitForMultipleElements(rowsLabelsBranchesDropdown); }
    public List<WebElement> getNumberOfLabels(int i){ return fluentWaitForMultipleElements(By.xpath("//tbody/tr["+i+"]/td[2]/div")); }
    public WebElement getSurveyButtonLateralMenu(){return fluentWait(surveyButtonLateralMenu); }
    public WebElement getAreaLabelInTable(int numRow, int numLabels){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+numRow+"]/td[2]/div["+numLabels+"]/div[1]/*[name()='span']"));}
    public WebElement getBranchLabelInTable(int numRow, int numLabels){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+numRow+"]/td[2]/div["+numLabels+"]/div[2]/*[name()='span']"));}
    public WebElement getDeleteAreaButtonLabel(int numRow, int numLabels){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+numRow+"]/td[2]/div["+numLabels+"]/div[2]/*[name()='svg']")); }
    public WebElement getDropdownSelectAreas(){ return fluentWait(dropdownSelectAreas); }
    public WebElement getInputSelectAreas(){ return fluentWait(inputSelectAreas); }
    public WebElement getAddNewQuestion(){ return fluentWait(newQuestion); }
    public WebElement getAddNewQuestionCentered(){return fluentWait(newQuestion2); }
    public WebElement getQuestionTextInput(){ return fluentWait(questionTextInput); }
    public WebElement getSaveQuestionButton(){ return fluentWait(saveQuestionButton); }
    public List<WebElement> getNumberOfRowsQuestionsTable(){ return fluentWaitForMultipleElements(rowsQuestionsTable); }
    public WebElement getQuestionColumnInQuestionsTable(int i){return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+i+"]/td[2]"));}
    public WebElement getQuestionTextIsRequiredLabel(){ return fluentWait(questionTextIsRequiredLabel); }
    public WebElement getTypeOfQuestionsDropDown(){return fluentWait(typeOfQuestionsDropDown); }
    public WebElement getOptionsInTypeOfQuestionsDropdown(int i){ return fluentWait(By.xpath("//ul[@class='MuiList-root sc-fzoPby cSPfCG question MuiList-padding']/div["+i+"]"));} // 1 for scale, 2 for faces, 3 for multiple option, 4 for text, 5 for contact
    public WebElement getSatisfactionIndexInput(){ return fluentWait(satisfactionIndexInput); }
    public WebElement getAddOptionButton(){ return fluentWait(addOptionButton); }
    public WebElement getOptionInput(int i){return fluentWait(By.xpath("//div[@class='sc-qQYBZ euMZXN']/div[2]/div["+i+"]/div[1]/input")); } //i=3 for first input, i=8 for six input
    public WebElement getShouldHaveBetweenTwoAndSixAnswersLabel(){return fluentWait(shouldHaveBetweenTwoAndSixAnswersLabel); }
    public WebElement getDescriptionField(){return fluentWait(descriptionField); }
    public WebElement getOpenDropdownSubTypeQuestionType(){ return fluentWait(subTypeQuestionType); }
    public WebElement getSubTypeQuestionType(int i){ return fluentWait(By.xpath("//ul[@class='MuiList-root sc-fzoPby cSPfCG question MuiList-padding']/div["+i+"]")); } //i=1 for CI, i=2 for Email, i=3 for mobile phone
    public WebElement getTypeOfQuestionInTable(int rowNum){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+rowNum+"]/td[3]")); }
    public WebElement getThreePointsQuestionButton(int rowNum){ return fluentWait(By.xpath("//tbody[@class='MuiTableBody-root']/tr["+rowNum+"]/td[5]/div")); }
    public WebElement getOptionsInThreePointsDropDownQuestionTable(int i){ return fluentWait(By.xpath("//div[@class='sc-pJkiN csfYLt']/div["+i+"]")); }//i=1 for edit and 2 for delete




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
        }
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getLateralMenuButtons(3).click();
        SurveyPage surveyPage = new SurveyPage(getDriver());
        return surveyPage;
    }

    public void editTitleQuestion(String type){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int i = 1;
        boolean esta = false;
        while(i < numberOfSurveys && !esta){
            surveyPage.getNameSurvey(i).click();
            int numberOfQuestions = surveyPage.getNumberOfRowsQuestionsTable().size();
            int j = 1;
            while(j < numberOfQuestions && !esta){
                if(surveyPage.getTypeOfQuestionInTable(j).getText().equals(type)){
                    esta = true;
                    surveyPage.getThreePointsQuestionButton(j).click();
                    surveyPage.getOptionsInThreePointsDropDownQuestionTable(1).click();
                    surveyPage.getQuestionTextInput().clear();
                    String generatedString = RandomStringUtils.random(10, true, false);
                    surveyPage.getQuestionTextInput().sendKeys(generatedString);
                    surveyPage.getSaveButton().click();
                    Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionColumnInQuestionsTable(j)));
                    Assert.assertTrue(surveyPage.getQuestionColumnInQuestionsTable(j).getText().equals(generatedString));
                }
                j++;
            }
            if(!esta){
                surveyPage.getBackFromDetailSurveyToMainSurvey().click();
            }
            i++;
        }
    }

    public void addAContactTypeQuestionWithSubtype(String Subtype, boolean Descripcion){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        try{
            surveyPage.getAddNewQuestion().click();
        }catch (Exception e){
            surveyPage.getAddNewQuestionCentered().click();
        }

        surveyPage.getSaveButton().click();
        int numberOfRowsQuestionTable = surveyPage.getNumberOfRowsQuestionsTable().size();
        boolean esta = false;
        int i = 1;
        while(i <= numberOfRowsQuestionTable){
//            if(surveyPage.getQuestionColumnInQuestionsTable(i).getText().equals(generatedString)){
//                esta = true;
//                break;
//            }
            i++;
        }
        Assert.assertTrue(esta);
        moveToElement(surveyPage.getQuestionColumnInQuestionsTable(i));
        Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionColumnInQuestionsTable(i)));
    }

    public void addQuestionToSurvey(String type, Boolean withText, Boolean withSatisfactionIndex, int validOptions, int invalidOptions, String SubtypeForContactType, boolean DescripcionForContactType){
        SurveyPage surveyPage = basePageNavigation();
        int numberOfSurveys = surveyPage.getRowsTableNumber().size();
        int rowSelectedInTable = (int)(Math.random() * (numberOfSurveys - 1 + 1) + 1);
        surveyPage.getNameSurvey(rowSelectedInTable).click();
        try{
            surveyPage.getAddNewQuestion().click();
        }catch (Exception e){
            surveyPage.getAddNewQuestionCentered().click();
        }
        String generatedString = RandomStringUtils.random(10, true, false);
        if(withText) {
            surveyPage.getQuestionTextInput().sendKeys(generatedString);
        }
        switch(type){
            case "Caras":
                surveyPage.getTypeOfQuestionsDropDown().click();
                surveyPage.getOptionsInTypeOfQuestionsDropdown(2).click();
                if(withSatisfactionIndex){
                    int satisfactionIndex = (int)(Math.random() * (10 - 1 + 1) + 1);
                    String index = String.valueOf(satisfactionIndex);
                    surveyPage.getSatisfactionIndexInput().sendKeys(index);
                }
                break;
            case "Contacto":
                surveyPage.getTypeOfQuestionsDropDown().click();
                surveyPage.getOptionsInTypeOfQuestionsDropdown(5).click();
                if(DescripcionForContactType){
                    String description = RandomStringUtils.random(20, true, false);
                    surveyPage.getDescriptionField().sendKeys(description);
                }
                switch(SubtypeForContactType){
                    case "CI":
                        break;
                    case "Email":
                        surveyPage.getOpenDropdownSubTypeQuestionType().click();
                        surveyPage.getSubTypeQuestionType(2).click();
                        break;
                    case "Telefono":
                        surveyPage.getOpenDropdownSubTypeQuestionType().click();
                        surveyPage.getSubTypeQuestionType(3).click();
                        break;
                }
                break;
            case "Multiple opción":
                surveyPage.getTypeOfQuestionsDropDown().click();
                surveyPage.getOptionsInTypeOfQuestionsDropdown(3).click();
                if(validOptions > 2){
                    for(int j = 1 ; j <= validOptions - 2 ; j++){
                        surveyPage.getAddOptionButton().click();
                    }
                }
                for(int i = 1 ; i <= validOptions ; i++){
                    String generatedStringForOptioni = RandomStringUtils.random(10, true, false);
                    surveyPage.getOptionInput(i+2).sendKeys(generatedStringForOptioni);
                }
                if(validOptions < 2){
                    for(int l = validOptions ; l <= invalidOptions - 3 ; l++){
                        surveyPage.getAddOptionButton().click();
                    }
                }else {
                    for (int k = 1; k <= invalidOptions; k++) {
                        surveyPage.getAddOptionButton().click();
                    }
                }
                break;
            case "Texto":
                surveyPage.getTypeOfQuestionsDropDown().click();
                surveyPage.getOptionsInTypeOfQuestionsDropdown(4).click();
                break;
        }
        surveyPage.getSaveQuestionButton().click();
        if(invalidOptions > 0 && validOptions < 2){
            Assert.assertTrue(isVisibleInViewport(surveyPage.getShouldHaveBetweenTwoAndSixAnswersLabel()));
        }else if(!withText){
            Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionTextIsRequiredLabel()));
        }else{
            int numberOfRowsQuestionTable = surveyPage.getNumberOfRowsQuestionsTable().size();
            boolean esta = false;
            int i = 1;
            while(i < numberOfRowsQuestionTable){
                if(surveyPage.getQuestionColumnInQuestionsTable(i).getText().equals(generatedString)){
                    Assert.assertTrue(surveyPage.getTypeOfQuestionInTable(i).getText().equals(type));
                    esta = true;
                    break;
                }
                i++;
            }
            Assert.assertTrue(esta);
            moveToElement(surveyPage.getQuestionColumnInQuestionsTable(i));
            Assert.assertTrue(isVisibleInViewport(surveyPage.getQuestionColumnInQuestionsTable(i)));
        }
    }
}

