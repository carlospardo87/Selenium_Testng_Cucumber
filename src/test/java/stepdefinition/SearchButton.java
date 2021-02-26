package stepdefinition;


import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;

import main.CucumberRunner;
import pages.SearchPage;

public class SearchButton extends CucumberRunner {

	SearchPage page = new SearchPage();

	@When("^I click search button$")
	public void clickSearchButton() throws Throwable {
		explicitWait(page.searchBox);
		page.searchBox.sendKeys(Keys.ENTER);

	}

}
