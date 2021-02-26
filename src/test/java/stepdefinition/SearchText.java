package stepdefinition;


import io.cucumber.java.en.Given;
import main.CucumberRunner;
import pages.SearchPage;

public class SearchText extends CucumberRunner {

	SearchPage page = new SearchPage();

	@Given("^I type \"(.*?)\"$")
	public void searchText(String text) throws Throwable {
		explicitWait(page.searchBox);
		page.searchBox.sendKeys(text);

	}

}
