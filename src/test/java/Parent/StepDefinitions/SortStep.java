package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.pages.StorePage;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;


public class SortStep {
    protected WebDriver driver;
    protected StorePage storePage;


    @When("I Sort by price {string}")
    public void iSortByPrice(String sortOption) {
        driver = DriverFactory.getDriver();
        storePage = new StorePage(driver);
        storePage.selectPrice(sortOption);

    }
}
