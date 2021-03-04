package stepdefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import pages.BaseStepDef;

public class SearchButton extends BaseStepDef {



	@Given("^I type \"(.*?)\"$")
	public void searchText(String text)  {
		explicitWait(driver().findElement(By.cssSelector("input[name='q']")));
		driver().findElement(By.cssSelector("input[name='q']")).sendKeys(text);

	}


	@When("^I click search button$")
	public void clickSearchButton()  {
		explicitWait(driver().findElement(By.cssSelector("input[name='q']")));
		driver().findElement(By.cssSelector("input[name='q']")).sendKeys(Keys.ENTER);

	}

}
