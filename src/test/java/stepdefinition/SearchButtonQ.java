package stepdefinition;


import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.BaseStepDef;

public class SearchButtonQ extends BaseStepDef {


	@Then("^I clear search textbox$")
	public void clearSearchBox() {
		explicitWait(driver().findElement(By.cssSelector("input[name='q']")));
		driver().findElement(By.cssSelector("input[name='q']")).clear();
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
