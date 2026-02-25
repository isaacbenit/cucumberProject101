package Parent.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        snippets = CAMELCASE,
        glue = {"Parent"},
        //tags = "@FullSearchResults",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-reports/Cucumber.json"}
)

public class TestJURunnerTest {
}
