package Parent.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"Parent"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-reports/Cucumber.json"}
)

public class TestJURunnerTest {
}
