package game.game;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty"}, glue="step_definitions",
features ="src/test/resources/features")
public class CucumberSetup {
}
