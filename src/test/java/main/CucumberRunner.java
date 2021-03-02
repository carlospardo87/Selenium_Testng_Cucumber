package main;

import helpers.ReportHelper;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@CucumberOptions(
		monochrome = true,
		features = "src/test/resources/features",
		glue = "stepdefinition",
		//plugin = {"pretty","json:target/cucumber.json", "timeline:target/timeline/" , "html:target/cucumber-pretty"}
		plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		tags = "@Regression"
		)
		//tags = { "@Regression,@JunitScenario,@TestngScenario" })

public class CucumberRunner extends AbstractTestNGCucumberTests {

	public static Properties config = null;
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

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
		// loads the config options
		LoadConfigProperty();
		// configures the driver path
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
		String baseUrl = config.getProperty("siteUrl");
		driver().get(baseUrl);
	}

	public static String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String cal1;
		cal1 = dateFormat.format(cal.getTime());
		return cal1;
	}


	/**
	 * Setting up Hooks
	 *
	 */

	@BeforeMethod(alwaysRun = true)           // Should be Before feature
	public void setUp() throws Exception {
		openBrowser();
		maximizeWindow();
		implicitWait(30);
		deleteAllCookies();
		setEnv();
		pageLoad(30);
	}


	@AfterMethod(alwaysRun = true)     // Should be After feature
	public void tearDown(ITestResult result) throws IOException {
		if (!result.isSuccess()) {
			File imageFile = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
			/*String failureImageFileName = result.getMethod().getMethodName()
					+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";*/
			String failureImageFileName = "image.png";
			File failureImageFile = new File(System.getProperty("user.dir") + "//report-output//Spark//" + failureImageFileName);
			FileUtils.copyFile(imageFile, failureImageFile);
			driver().close();
		} else {
			driver().quit();
		}

	}



	@AfterSuite(alwaysRun=true)
	public void generateHTMLReports() {
		ReportHelper.generateCucumberReport();
	}


}

/*
@After
    public void teardow(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "name");
        }
    }
	}*/

