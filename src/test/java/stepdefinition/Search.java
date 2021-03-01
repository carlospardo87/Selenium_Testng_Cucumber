package stepdefinition;

import io.cucumber.java.en.Given;
import org.testng.Assert;


import main.CucumberRunner;

public class Search extends CucumberRunner {

	@Given("^I am on \"(.*?)\" search page$")
	public void verifyPageTitle(String text) {

		String title = driver().getTitle();
		switch (text) {
			case "google":
				Assert.assertEquals(title, "Google");
				break;
			case "cucumber":
				Assert.assertEquals(title, "cucumber - Google Search");
				break;
			case "junit":
				Assert.assertEquals(title, "juni - Google Search");
				break;
		}
	}

}
