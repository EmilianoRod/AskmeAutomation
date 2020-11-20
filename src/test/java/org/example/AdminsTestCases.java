package org.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.AdminsPage;
import pageObjects.InfoUserPage;
import pageObjects.ResultPage;

public class AdminsTestCases extends AdminsPage{

    @Test
    public void createCompanyAdmin() {
        AdminsPage adminsPage = basePageNavigation();
        adminsPage.getNewAdminButton().click();
        String email = generateEmail();
        adminsPage.getEmailInput().sendKeys(email);
        adminsPage.getPasswordInput().sendKeys("password");
        adminsPage.getConfirmPasswordInput().sendKeys("password");
        adminsPage.getConfirmButton().click();
        adminsPage.getAdminsTable(); //This is for wait
        int rowNum = adminsPage.getRowsTable().size();
        boolean esta = false;
        for (int i = 1; i <= rowNum; i++) {
            if (adminsPage.getRowColumnAdminTable(i,1).getText().equals(email)) {
                esta = true;
                break;
            }
        }
        Assert.assertTrue(esta);
    }

    @Test
    public void createVisualizatorAdmin(){
        AdminsPage adminsPage = basePageNavigation();
        adminsPage.getNewAdminButton().click();
        String email = generateEmail();
        adminsPage.getEmailInput().sendKeys(email);
        adminsPage.getPasswordInput().sendKeys("password");
        adminsPage.getConfirmPasswordInput().sendKeys("password");
        adminsPage.getOpenDropdownAcceso().click();
        adminsPage.getOptionsAccessDropdown(2).click();
        adminsPage.getConfirmButton().click();
        int rowNum = adminsPage.getRowsTable().size();
        boolean esta = false;
        for (int i = 1; i <= rowNum; i++) {
            if (adminsPage.getRowColumnAdminTable(i,2).getText().equals("Visualización") && adminsPage.getRowColumnAdminTable(i,1).getText().equals(email)) {
                esta = true;
                break;
            }
        }
        Assert.assertTrue(esta);
    }

    @Test
    public void createAnAdminWithoutEmail(){
        AdminsPage adminsPage = basePageNavigation();
        adminsPage.getNewAdminButton().click();
        adminsPage.getPasswordInput().sendKeys("password");
        adminsPage.getConfirmPasswordInput().sendKeys("password");
        adminsPage.getConfirmButton().click();
        Assert.assertTrue(isVisibleInViewport(adminsPage.getEmailIsRequiredLabel()));
    }

    @Test(dataProvider="getDataInvalidEmail")
    public void createAnAdminWithInvalidEmail(String email){
        AdminsPage adminsPage = basePageNavigation();
        adminsPage.getNewAdminButton().click();
        adminsPage.getEmailInput().sendKeys(email);
        adminsPage.getPasswordInput().sendKeys("password");
        adminsPage.getConfirmPasswordInput().sendKeys("password");
        adminsPage.getConfirmButton().click();
        Assert.assertTrue(isVisibleInViewport(adminsPage.getEmailInvalidLabel()));
    }

    @Test
    public void createAnAdminWithoutConfirmPassword(){
        AdminsPage adminsPage = basePageNavigation();
        adminsPage.getNewAdminButton().click();
        String email = generateEmail();
        adminsPage.getEmailInput().sendKeys(email);
        adminsPage.getPasswordInput().sendKeys("password");
        adminsPage.getConfirmButton().click();
        Assert.assertTrue(isVisibleInViewport(adminsPage.getPasswordsNotMatchLabel()));
    }

    @Test
    public void deleteAnCompanyAdmin() throws InterruptedException {
        AdminsPage adminsPage = basePageNavigation();
        int rowCount = adminsPage.getRowsTable().size();
        int rowSelected = (int)(Math.random() * (rowCount - 1 + 1) + 1);
        adminsPage.getThreePointsButton(rowSelected).click();
        adminsPage.getOptionsEditOrDelete(2).click();
        adminsPage.getConfirmDeleteAdmin().click();
        Thread.sleep(1000);
        Assert.assertTrue(rowCount == adminsPage.getRowsTable().size() + 1);
    }

    @Test
    public void myUserDontAppearsInTheList(){
         basePageNavigation();
        ResultPage resultPage = new ResultPage(getDriver());
        resultPage.getEmailButton().click();
        InfoUserPage infoPage = new InfoUserPage(getDriver());
        String myEmail = infoPage.getMyEmailText().getText();
        ResultPage resultPage2 = new ResultPage(getDriver());
        resultPage2.getLateralMenuButtons(4).click();
        AdminsPage adminsPage2 = new AdminsPage(getDriver());
        int rowCount = adminsPage2.getRowsTable().size();
        boolean esta = false;
        for(int i = 1 ; i <= rowCount ; i++){
            if(adminsPage2.getRowColumnAdminTable(i, 1).getText().equals(myEmail)){
                esta = true;
            }
        }
        Assert.assertFalse(esta);
    }

    @Test
    public void editEmailOfCompanyUser(){
        AdminsPage adminsPage = basePageNavigation();
        int rowCount = adminsPage.getRowsTable().size();
        int rowSelected = (int)(Math.random() * (rowCount - 1 + 1) + 1);
        while(!adminsPage.getRowColumnAdminTable(rowSelected, 2).getText().equals("Administrador")){
            rowSelected = (int)(Math.random() * (rowCount - 1 + 1) + 1);
        }
        adminsPage.getThreePointsButton(rowSelected).click();
        adminsPage.getOptionsEditOrDelete(1).click();
        adminsPage.getEmailInput().clear();
        String email = generateEmail();
        adminsPage.getEmailInput().sendKeys(email);
        adminsPage.getConfirmButton().click();
        boolean esta = false;
        for(int i = 1 ; i <= rowCount ; i++){
            if(adminsPage.getRowColumnAdminTable(i,1).getText().equals(email)){
                esta = true;
                break;
            }
        }
        Assert.assertTrue(esta);
    }

    @Test
    public void editEmailOfVisualizatorUser(){
        AdminsPage adminsPage = basePageNavigation();
        int rowCount = adminsPage.getRowsTable().size();
        int rowSelected = (int)(Math.random() * (rowCount - 1 + 1) + 1);
        while(!adminsPage.getRowColumnAdminTable(rowSelected, 2).getText().equals("Visualización")){
            rowSelected = (int)(Math.random() * (rowCount - 1 + 1) + 1);
        }
        adminsPage.getThreePointsButton(rowSelected).click();
        adminsPage.getOptionsEditOrDelete(1).click();
        adminsPage.getEmailInput().clear();
        String emailToEdit = generateEmail();
        adminsPage.getEmailInput().sendKeys(emailToEdit);
        adminsPage.getConfirmButton().click();
        boolean esta = false;
        for(int i = 1 ; i <= rowCount ; i++){
            if(adminsPage.getRowColumnAdminTable(i,1).getText().equals(emailToEdit)){
                esta = true;
                break;
            }
        }
        Assert.assertTrue(esta);
    }

    @DataProvider
    public Object[][] getDataInvalidEmail(){
        // Row stands for how many different data types test should run
        //coloumn stands for how many values per each test
        Object[][] data=new Object[4][1];
        data[0][0]="erodriguez@@effectussoftware.com";
        data[1][0]="erodriguez@effectussoftware..com";
        data[2][0]="erodriguez@effectussoftware..c";
        data[3][0]="erodriguez#effectussoftware.com";
        return data;
    }

}
