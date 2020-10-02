package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.JavascriptExecutor;


public class base {

    public WebDriver driver;
    public Properties prop;
    public static Logger log = LogManager.getLogger(base.class.getName());

    public WebDriver initializeDriver() throws IOException {

        prop = new Properties();
        //System.getProperty("user.dir")
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//resources//data.properties");
        prop.load(fis);
        //mvn test -Dbrowser=chrome
        //String browserName=System.getProperty("browser");  // Uncomment this line if you are sending parameter from Maven
        String browserName = prop.getProperty("browser"); //comment this line if you are sending parameter from Maven
        System.out.println(browserName);

        if (browserName.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//java//resources//chromedriver 7");
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            //execute in chrome driver

        } else if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/Users/emilianorodriguez/eclipse/EffeWeb/src/main/java/resources/geckodriver");
            driver = new FirefoxDriver();
            //firefox code
        } else if (browserName.equals("IE")) {
//	IE code
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
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



