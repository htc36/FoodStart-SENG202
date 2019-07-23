package nic.foody;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/features",
		 glue="nic.foody.stepdefs",
         plugin={"pretty"},
         snippets = SnippetType.CAMELCASE)
		 
public class RunCucumberTest {
}