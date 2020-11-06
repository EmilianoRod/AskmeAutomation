package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import resources.Base;

import java.util.List;

public class SurveyPage extends Base{
    public WebDriver driver;
    public SurveyPage(WebDriver driver){
        this.driver = driver;
    }

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
    final private By newQuestion = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/button[1]");
    final private By questionTextInput = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/input[1]");
    final private By saveQuestionButton = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[1]/div[3]/button[1]");
    final private By rowsQuestionsTable = By.xpath("//tbody/tr");
    final private By questionTextIsRequiredLabel = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[3]/label[1]");
    final private By typeOfQuestionsDropDown =  By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]");
    final private By satisfactionIndexInput = By.xpath("//input[@class='sc-AxhUy kbFuQf question']");
    final private By option1Input = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[2]/div[3]/div[1]/input[1]");
    final private By option2Input = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[2]/div[4]/div[1]/input[1]");
    final private By addOptionButton = By.xpath("//button[@class='sc-pbIaG jGPUoW margin']");

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

}
