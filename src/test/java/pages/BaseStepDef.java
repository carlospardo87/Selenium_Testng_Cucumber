package pages;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseStepDef {

    public static List<String> storeId = new ArrayList<>();
    public static Properties config = null;
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static String baseUrl = null;


    public void LoadConfigProperty() throws IOException {
        config = new Properties();
        FileInputStream ip = new FileInputStream(
                System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
        config.load(ip);
    }

    public void configureDriverPath() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");


        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 8.0.0;" +
                "iPhone X Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/67.0.3396.99 Mobile Safari/537.36");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        driver.set(new ChromeDriver(options));
    }

   public static WebDriver driver()
    {
        return driver.get();
    }


    public void openBrowser() throws Exception {
        LoadConfigProperty();
        configureDriverPath();
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

    public void setEnv() throws Exception {
        LoadConfigProperty();
        baseUrl = config.getProperty("siteUrl");
        driver().get(baseUrl);
    }

    public static String currentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String cal1;
        cal1 = dateFormat.format(cal.getTime());
        return cal1;
    }

    public void setUp() throws Exception {
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
        }
    }


}
