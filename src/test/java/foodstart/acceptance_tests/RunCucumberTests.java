package foodstart.acceptance_tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = "step_definitions")

public class RunCucumberTests {
}
