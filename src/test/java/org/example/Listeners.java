package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import resources.Base;
import resources.ExtentManager;


public class Listeners extends Base implements ITestListener {

    private static final ExtentReports extent = ExtentManager.createInstance();
    private static final ThreadLocal<ExtentTest> extentTest =new ThreadLocal<ExtentTest>();

    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " +
                                                           result.getMethod().getMethodName());
        extentTest.set(test);
    }

    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Successful</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, m);
        log.info("Test completed");

    }

    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font color=red>" +
                         "Exception Ocurred, click to see details: "+ "</font></b></summary>" +
                          exceptionMessage.replaceAll(",", "<br>") + "</details> \n");

        WebDriver driver = ((LoginPageCases)result.getInstance()).getDriver();
        String path = takeScreenshot(driver, result.getMethod().getMethodName());
        try{
            extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e){
            extentTest.get().fail("Test failed, cannot attach screenshot");
        }

        String logText = "<b>Test Method " + methodName + " Failed</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, m);
        log.info("Test failed");
    }

    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Skipped</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP, m);
        log.info("Test skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    public void onFinish(ITestContext context) {
        if(extent != null) {
            extent.flush();
        }

    }

    public String takeScreenshot(WebDriver driver, String methodName){
        String fileName = getScreenshotName(methodName);
        String directory = System.getProperty("user.dir") + "/screenshots/";
        new File(directory).mkdirs();
        String path = directory + fileName;

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
            System.out.println("**************");
            System.out.println("Screenshot stored at: " + path);
            System.out.println("**************");
        } catch(Exception e){
                e.printStackTrace();
            }
            return path;
    }

    public static String getScreenshotName(String methodName){
        Date d = new Date();
        String fileName = methodName + "-" + d.toString().replace(":", "_").replace(" ", "_") + ".png";
        return fileName;
    }
}