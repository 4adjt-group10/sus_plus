package br.com.susmanager.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features/manager.feature",
        glue = "com.susmanager.bdd",
        plugin = {"html", "html:target/cucumber-reports"}
)
public class CucumberTest {
}
