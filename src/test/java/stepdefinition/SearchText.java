package stepdefinition;


import io.cucumber.java.en.Given;
import main.CucumberRunner;
import pages.SearchPage;

public class SearchText extends CucumberRunner {

	SearchPage page = new SearchPage();

	@Given("^I type \"(.*?)\"$")
	public void searchText(String text) throws Throwable {
		System.out.println("Title printed by Thread " + Thread.currentThread().getId());
		explicitWait(page.searchBox);
		page.searchBox.sendKeys(text);

	}

}
