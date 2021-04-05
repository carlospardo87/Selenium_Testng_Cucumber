package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.List;
import java.util.Properties;

import static helpers.ReportHelper.generateCucumberReport;
import static stepdefinition.BaseStepDef.storeId;
import static driver.DriverManager.config;


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
		)
		//tags = { "@Regression,@JunitScenario,@TestngScenario","~@SoapUI" })
       //  Para Ignore Scenarios add ~ at the beginning of the tag

public class CucumberRunnerFailed extends AbstractTestNGCucumberTests {

	long timeStart, timeEnd, time;
	String screenReportDir = System.getProperty("user.dir") + "//report-output//";
	String rerunFilePath = System.getProperty("user.dir") + "//target//failedrerun.txt";


	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeSuite
	public void initEnv() {
		checkRerunFileExist(rerunFilePath);
		timeStart = System.currentTimeMillis();
		deleteScreenshots(screenReportDir, "png");
		LoadConfigProperty();
	}

	@AfterSuite(alwaysRun = true)
	public void generateReportHtml(){
		generateHTMLReports();
		getRegressionTime();
		writeFiledScenarios(storeId);
	}

	public void generateHTMLReports() {
		generateCucumberReport();

	}

	public void writeFiledScenarios(List <String> list) {
		FileWriter fichero = null;
		PrintWriter pw;
		try
		{
			fichero = new FileWriter("target/failedrerun.txt");
			pw = new PrintWriter(fichero);

			for (int i = 0; i <= list.size()-1; i++)
				pw.println(list.get(i));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();

				if (storeId.isEmpty()) {
					System.out.println("\n------------------------\n- REGRESSION STATUS SUCCESS\n------------------------");
				} else {
					System.out.println("\n------------------------\n- REGRESSION STATUS FAILED");
					System.out.println("Scenarios Failed for Retry :\n" + storeId);
					System.out.println("------------------------\n");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}


	}

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

	public void getRegressionTime() {
		timeEnd = System.currentTimeMillis();
		time = (timeEnd - timeStart) / 1000;

		System.out.print("------------------------\n");
		System.out.print("- REGRESSION TIME (sec) "+ time);
		System.out.print("\n------------------------");
	}

	public void checkRerunFileExist(String rerunFilePath) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(rerunFilePath));
			if (br.readLine() == null)
				throw new Exception("!! failedrerun.txt is empty !! Path: "+rerunFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void LoadConfigProperty() {
		config = new Properties();
		FileInputStream confPropertyFile;
		try {
			confPropertyFile = new FileInputStream(
					System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
			config.load(confPropertyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}




