package stepdefinition;

import driver.DriverManager;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class BaseStepDef extends DriverManager {

    public static List<String> storeId = new ArrayList<>();

    /*
    * If mvn command contains -Dbrowser={Chrome, Firefox, etc..}  it will run this browser  else it takes the browser properties into the config.properties file
    * */
    public void openBrowser() {
        if (System.getProperty("browser") != null) {
            createInstance(System.getProperty("browser").toUpperCase(), config.getProperty("headlessMode"), config.getProperty("urlHub"));
        } else {
            createInstance(config.getProperty("browserType").toUpperCase(), config.getProperty("headlessMode"), config.getProperty("urlHub"));
        }
    }

    public void maximizeWindow() {
        driver().manage().window().maximize();
    }

    public void implicitWait(int time) {
        driver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver(), 3000);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void pageLoad(int time) {
        driver().manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
    }

    public void deleteAllCookies() {
        driver().manage().deleteAllCookies();
    }

    public void getSystemInfo() {
        Capabilities cap = ((RemoteWebDriver) driver()).getCapabilities();
        String os = cap.getPlatform().toString();
        String browserName = cap.getBrowserName().toLowerCase();
        String version = cap.getVersion();
        System.out.println("| OS: "+os+"| BROWSER: "+browserName+"| VERSION: "+version+" |");
    }

    public void setEnv(){
        System.out.println("URL --- "+ config.getProperty("siteUrl"));
        driver().get(config.getProperty("siteUrl"));
    }

    public static String currentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String cal1;
        cal1 = dateFormat.format(cal.getTime());
        return cal1;
    }

    public static WebDriver driver()
    {
        return driver.get();
    }



    /** Hooks methods **/

    public void setUp() {
        openBrowser();
        maximizeWindow();
        implicitWait(30);
        deleteAllCookies();
        setEnv();
        pageLoad(30);
    }

    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            storeId.add(scenario.getId());
            scenario.attach(screenshot, "image/png", "Screen error!!! ");
            driver().close();

        } else {
            driver().quit();
            //forceQuitDriver("killall Safari");
            //getSystemInfo();
        }
    }






}
