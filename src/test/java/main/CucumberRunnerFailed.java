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
import org.testng.annotations.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@CucumberOptions(
		monochrome = true,
		features = "@target/failedrerun.txt",
		glue = "stepdefinition",
		plugin = {"pretty","json:target/cucumber.json",
				"timeline:target/timeline/",
				"html:target/cucumber-pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failedrerun.txt"
		}
		//plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		)
		//tags = { "@Regression,@JunitScenario,@TestngScenario" })

public class CucumberRunnerFailed extends AbstractTestNGCucumberTests {

	public static List<String> storeId = new ArrayList<>();
	public static Properties config = null;
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	String screenShotDir = System.getProperty("user.dir") + "//screenshot//";
	String screenReportDir = System.getProperty("user.dir") + "//report-output//";

	long timeStart, timeEnd, time;


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
		options.addArguments("--headless");
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

	//move to utils file
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

	@BeforeSuite(alwaysRun = true)
	public void initEnv() {
		timeStart = System.currentTimeMillis();
		deleteScreenshots(screenReportDir, "png");
	}

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
	public void tearDown(ITestResult result)  {
		if (!result.isSuccess()) {
			driver().close();
		} else {
			driver().quit();
		}

	}

	//move to utils file
	public void deleteScreenshots(String path, String extension){
		File directory = new File(path);
		if (!directory.exists()) {
			System.out.println("Screenshots file has not been created");
			return;
		}

		File[] screenFile = new File(path).listFiles(screens -> {
			if (screens.isFile())
			return screens.getName().endsWith('.' + extension);
			return false;
		});

		assert screenFile != null;
		for (File screens : screenFile) {
			screens.delete();
		}
		System.out.println("Screenshots has been deleted properly");

	}


	@AfterSuite(alwaysRun=true)
	public void generateHTMLReports() {
		ReportHelper.generateCucumberReport();
		timeEnd = System.currentTimeMillis();
		time = (timeEnd - timeStart)/1000;

		System.out.println("\n--- Regression execution time: " + time+ " seconds ---\n");
	}

	}




