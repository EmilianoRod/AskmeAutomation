package resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;


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
        String browserName=System.getProperty("browser");  // Uncomment this line if you are sending parameter from Maven
      //  String browserName = prop.getProperty("browser"); //comment this line if you are sending parameter from Maven
        System.out.println(browserName);

        if (browserName.contains("chrome")) {
            if (System.getProperty("os.name").contains("Windows")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//drivers//chromedriverWindows.exe");
            } else if (System.getProperty("os.name").contains("Mac")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//drivers//chromedriverMac");
            }else{
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//drivers//chromedriverLinux");
            }
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver.set(new ChromeDriver(options));
            options.addArguments(" --no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else if (browserName.equals("firefox")) {
            if (System.getProperty("os.name").contains("Windows")) {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//main//java//resources//drivers//geckodriverWindows.exe");
            } else if (System.getProperty("os.name").contains("Mac")) {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//main//java//resources//drivers//geckodriverMac");
            }else{
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//main//java//resources//drivers//geckodriverLinux");
            }
            driver.set(new FirefoxDriver());
        } else if (browserName.equals("safari")) {
            driver.set(new SafariDriver());
        }
    }

    public WebDriver getDriver() { return driver.get(); }

    @AfterMethod
    public void tearDown() {
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
        System.out.println(System.getProperty("os.name"));
        try {
            try {
                log.info("Connecting to database...");
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(prop.getProperty("dbUrl"), prop.getProperty("username"), prop.getProperty("password"));
            if (connection != null) {
                log.info("Connected to PostgresSQL");
            } else {
                log.info("Failed to connect");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(6))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        WebElement foo = wait.until((WebDriver driver) -> {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        return foo;
    }

    public List<WebElement> fluentWaitForMultipleElements(final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(6))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        List<WebElement> foo = wait.until((WebDriver driver) -> {
            try {
                List<WebElement> elements = driver.findElements(locator);
                    return elements;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
            return null;
        });
        return foo;
    }

    public void moveToElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) getDriver();;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public String generateEmail(){
        Random rand = new Random();
        int random = rand.nextInt(9999);
        String email = "erodriguez+" + random + "@effectussoftware.com";
        return email;
    }


}
