package stepdefinition;

import io.cucumber.java.en.Then;
import main.CucumberRunner;
import org.testng.Assert;
import pages.SearchPage;

public class Clear extends CucumberRunner {

	SearchPage page = new SearchPage();

	@Then("^I clear search textbox$")
	public void clearSearchBox() {
		explicitWait(page.searchBox);
		page.searchBox.clear();
	}


	@Then("I clear search text box")
	public void iClearSearchTextBox() {
		Assert.assertEquals("Carlos","Carlos1");
	}

	@Then("I clear search text boxes")
	public void iClearSearchTextBoxes() {
		Assert.assertEquals("Carlos","Carlos1");
	}

}
