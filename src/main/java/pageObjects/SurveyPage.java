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

    private By addSurveyButton = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/button[1]");
    private By nameInput = By.tagName("input");
    private By saveButton = By.xpath("//button[contains(text(),'Guardar')]");
    private By backFromDetailSurveyToMainSurvey = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/button[1]/img[1]");
    private By table = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[3]/table[1]");
    private By tableRows = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr");
    private By editButtonInThreePoint = By.xpath("//body/div[@id='transitions-popper']/div[1]/div[1]");
    private By nameIsRequiredLabel = By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/label[2]");
    private By rowsLabelsAreaDropdown = By.xpath("//body/div[2]/div[3]/div[1]/ul[1]/div");
    private By surveyButtonLateralMenu = By.xpath("//body/div[@id='root']/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/button[3]/label[1]");

    public WebElement getAddSurveyButton(){ return fluentWait(addSurveyButton); }
    public WebElement getNameInput(){ return fluentWait(nameInput); }
    public WebElement getSaveButton(){return fluentWait(saveButton); }
    public WebElement getBackFromDetailSurveyToMainSurvey(){ return fluentWait(backFromDetailSurveyToMainSurvey); }
    public List<WebElement> getRowsTable(){ return driver.findElements(tableRows); }
    public WebElement getRowsTable(int i){ return fluentWait(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr["+i+"]/td[1]"));} //i=1 to cant of rows
    public WebElement getThreePointsButton(int i){ return fluentWait(By.xpath("//tbody/tr["+i+"]/td[3]/div[1]/button[1]")); }
    public WebElement getEditButtonInThreePoint(){ return fluentWait(editButtonInThreePoint); }
    public WebElement getTable(){ return fluentWait(table); }
    public WebElement getNameIsRequiredLabel(){ return fluentWait(nameIsRequiredLabel); }
    public WebElement getAssignAreaButton(int i, int j){ return fluentWait(By.xpath("//tbody/tr["+i+"]/td[2]/div["+j+"]/div[1]/div[1]/button[1]")); } //i for row and j for number of labels
    public WebElement getLabelsAreaDropdown(int i){ return fluentWait(By.xpath("//body/div[2]/div[3]/div[1]/ul[1]/div["+i+"]/div[1]/span[1]/div[1]/span[1]"));}
    public List<WebElement> getRowsLabelsAreaDropdown(){ return driver.findElements(rowsLabelsAreaDropdown); }
    public List<WebElement> getNumberOfLabels(int i){ return driver.findElements(By.xpath("//tbody/tr["+i+"]/td[2]/div")); }
    public WebElement getSurveyButtonLateralMenu(){return fluentWait(surveyButtonLateralMenu); }
    public WebElement getAreaLabelInTable(int numRow, int numLabels){ return fluentWait(By.xpath("//tbody/tr["+numRow+"]/td[2]/div["+numLabels+"]/div[1]/span[1]"));}
    public WebElement getBranchLabelInTable(int numRow, int numLabels){ return fluentWait(By.xpath("//tbody/tr["+numRow+"]/td[2]/div["+numLabels+"]/div[2]/span[1]"));}




}
