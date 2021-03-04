package main;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import static helpers.ReportHelper.generateCucumberReport;
import static pages.BaseStepDef.storeId;


@CucumberOptions(
		monochrome = true,
		features = "src/test/resources/features",
		glue = "stepdefinition",
		plugin = {"pretty","json:target/cucumber.json",
				"timeline:target/timeline/",
				"html:target/cucumber-pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failedrerun.txt"
		}
		)
		//tags = { "@Regression,@JunitScenario,@TestngScenario" })

public class CucumberRunner extends AbstractTestNGCucumberTests {

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
		timeStart = System.currentTimeMillis();
		deleteScreenshots(screenReportDir, "png");
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
			fichero = new FileWriter(rerunFilePath);
			pw = new PrintWriter(fichero);

			for (int i = 0; i <= list.size()-1; i++)
				pw.println(list.get(i));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
				System.out.println("\n-----------  RETRY FILE STATUS ----------------------------------");
				System.out.println("File is written successfully");
				System.out.println("-----------------------------------------------------------------");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		System.out.println("\n-----------  SCENARIOS FAILED -----------------------------------");
		if (storeId.isEmpty())
			System.out.println("There are not failed scenarios");
		else System.out.println(" "+ storeId +" ");
		System.out.println("-----------------------------------------------------------------");
	}

	public void deleteScreenshots(String path, String extension) {
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

		System.out.println("\n-----------  REGRESSION TIME ------------------------------------");
		System.out.println(time + " seconds");
		System.out.println("-----------------------------------------------------------------");
	}
}






