package stepdefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.BaseStepDef;

public class Hooks extends BaseStepDef {

    @Before
    public void  setupTest(Scenario scenario) throws Exception {
        setUp();
    }

    @After
    public void afterScenario(Scenario scenario) {
        tearDown(scenario);
    }

}
