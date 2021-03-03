package stepdefinition;


import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import main.CucumberRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.SearchPage;

import java.util.ArrayList;
import java.util.List;

public class SearchText extends CucumberRunner {

	SearchPage page = new SearchPage();

	@Given("^I type \"(.*?)\"$")
	public void searchText(String text) throws Throwable {
		System.out.println("Title printed by Thread " + Thread.currentThread().getId());
		explicitWait(page.searchBox);
		page.searchBox.sendKeys(text);

	}

	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver();
			byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			storeId.add(scenario.getId());
			scenario.attach(screenshot, "image/png", "Screen error!!! ");
		}
	}

}
