package Parent.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import jdk.jshell.Snippet;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        snippets = CAMELCASE,
        glue = {"Parent"},
<<<<<<< ft/searchAndSortFeatures
        //tags = "@FullSearchResults",
=======
        snippets = CAMELCASE,
>>>>>>> dev
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-reports/Cucumber.json"}
//        tags = "@account"
)

public class TestJURunnerTest {
}
