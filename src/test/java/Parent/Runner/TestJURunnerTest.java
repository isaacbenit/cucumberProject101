package Parent.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import jdk.jshell.Snippet;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        tags= "@SCRUM-26",
        snippets = CAMELCASE,
        glue = {"Parent"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "junit:target/cucumber_report/CucumberXMLReport.xml",
                "json:target/cucumber-reports/Cucumber.json"}
)

public class TestJURunnerTest {
}
