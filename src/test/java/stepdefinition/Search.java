package stepdefinition;

import io.cucumber.java.en.Given;
import org.testng.Assert;


import main.CucumberRunner;

public class Search extends CucumberRunner {

	@Given("^I am on \"(.*?)\" search page$")
	public void verifyPageTitle(String text) throws Throwable {

		String title = driver.get().getTitle();
			if(text.equals("google")) {
				Assert.assertEquals(title, "Google");
			} else if(text.equals("cucumber")) {
				Assert.assertEquals(title, "cucumber - Google Search");
			} else if(text.equals("junit")) {
				Assert.assertEquals(title, "juni - Google Search");
			}
	}

}
