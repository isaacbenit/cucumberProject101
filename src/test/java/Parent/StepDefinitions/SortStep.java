package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.Pages.StorePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;


public class SortStep {
    protected WebDriver driver;
    protected StorePage storePage;


    @When("I Sort by price {string}")
    public void iSortByPrice(String sortOption) {
        driver = DriverFactory.getDriver();
        storePage = new StorePage(driver);
        storePage.sortByPrice(sortOption);
    }

    @Then("The products should be sorted by price from {string}")
    public void theProductsShouldBeSortedByPriceFrom(String expectedOrder) {
        String actualOrder = storePage.getPriceSortOrder();
        assertEquals("Products are not sorted correctly",expectedOrder, actualOrder);

    }
}
