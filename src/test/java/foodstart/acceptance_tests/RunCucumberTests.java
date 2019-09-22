package foodstart.acceptance_tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.*;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = "src/main/resource/foodstart/features",
        tags = {"not @skip_scenario"},
        glue = "foodstart.acceptance_tests.step_definitions")

public class RunCucumberTests {
}
