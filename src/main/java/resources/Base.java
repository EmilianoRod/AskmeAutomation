package resources;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Base {


    public Properties prop;
    //protected static ThreadLocal<Map<ChromeDriver, FirefoxDriver>> dridver = new ThreadLocal<>();
    protected static ThreadLocal<Map<ChromeDriver, FirefoxDriver>> driver = ThreadLocal.withInitial(() -> new LinkedHashMap<ChromeDriver, FirefoxDriver>());

    @BeforeMethod
    public void setUp() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//resources//data.properties");
        prop.load(fis);
        String browserName = prop.getProperty("browser");
        System.out.println(browserName);
        if(browserName.contains("chrome")){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//chromedriver 7");
            Map map = new HashMap<ChromeDriver, FirefoxDriver>();
            map.put(1, new ChromeDriver());
            driver.set(map);
            ChromeOptions options = new ChromeOptions();
            if(browserName.contains("headless")){
                options.addArguments("headless");
            }
            getDriver().manage().window().maximize();
            //execute in chrome drive
        } else if(browserName.contains("firefox")){
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//main//java//resources//geckodriver");
            Map map = new HashMap<ChromeDriver, FirefoxDriver>();
            map.put(2, new FirefoxDriver());
            driver.set(map);
        }
    }

    public WebDriver getDriver(){
        WebDriver driver1 = null;
        if(driver.get().get(1) != null){
             driver1 = driver.get().get(1);
        }else if(driver.get().get(2) != null){
            driver1 = driver.get().get(2);
        }
        return driver1;
    }

    @AfterMethod
    public void tearDown(Integer key){
        getDriver().quit();
    }

    public Boolean isVisibleInViewport(WebElement element) {
        WebDriver driver = ((RemoteWebElement) element).getWrappedDriver();
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0],                 " +
                        "  box = elem.getBoundingClientRect(),    " +
                        "  cx = box.left + box.width / 2,         " +
                        "  cy = box.top + box.height / 2,         " +
                        "  e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {         " +
                        "  if (e === elem)                        " +
                        "    return true;                         " +
                        "}                                        " +
                        "return false;                            "
                , element);
    }
}
