package resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Base{

    public Properties prop;
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static Logger log = LogManager.getLogger(Base.class.getName());
    public Connection connection = null;



    @BeforeMethod
    public void setUp() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//resources//data.properties");
        prop.load(fis);
        //String browserName=System.getProperty("browser");  // Uncomment this line if you are sending parameter from Maven
        String browserName = prop.getProperty("browser"); //comment this line if you are sending parameter from Maven
        System.out.println(browserName);

        if (browserName.contains("chrome")){
            if(System.getProperty("os.name").contains("Windows")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//chromedriverWindows.exe");
            } else if(System.getProperty("os.name").contains("Mac")){
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//chromedriverMac");
                }
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver.set(new ChromeDriver(options));
        } else if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//main//java//resources//geckodriver");
            driver.set(new FirefoxDriver());
        } else if (browserName.equals("safari")) {
            driver.set(new SafariDriver());
        }
    }

    public WebDriver getDriver(){
        return driver.get();
    }

    @AfterMethod
    public void tearDown(){
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

    public void connect(){
        try{
            try {
                log.info("Connecting to database...");
                Class.forName("org.postgresql.Driver");
            }
            catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(prop.getProperty("dbUrl"),prop.getProperty("username"), prop.getProperty("password"));
            if(connection != null){
                log.info("Connected to PostgresSQL");
            } else{
                log.info("Failed to connect");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Waiting 30 seconds for an element to be present on the page, checking
    // for its presence once every 5 seconds.
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(30, SECONDS)
            .pollingEvery(5, SECONDS)
            .ignoring(NoSuchElementException.class);

    protected Boolean waitForIsClickable(WebElement locator, Integer... timeout) {
        try {
            wait(isVisibleInViewport(locator).booleanValue() == true,
                    (timeout.length > 0 ? timeout[0] : null));
        } catch (TimeoutException exception) {
            return false;
        }
        return true;
    }
}
