package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.constants.Endpoint;
import Parent.Pages.StorePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class SearchProductStep {
    protected WebDriver driver;
    protected static StorePage storePage;

    @Given("I am on the store page")
    public void iAmOnTheStorePage() throws IllegalAccessException{
        driver = DriverFactory.getDriver();
        storePage = new StorePage(driver);
        storePage.load(Endpoint.STORE.url);

    }
    @When("I enter {string} into the search bar")
    public void iEnterProductIntoTheSearchBar(String product){
        storePage.enterProductName(product);
        storePage.searchButton();
    }
    @Then("I should see results showing product with its names")
    public void iShouldSeeResultsShowingProductName(){
        String actualProductName = storePage.getProductTitle();
        Assert.assertEquals("Product name doesn't exist","Blue Shoes",actualProductName);
    }
    @Then ("I should see error message: {string}")
    public void errorMessage(String error){
        String actualErrorMessage = storePage.getErrorMessage();
        Assert.assertEquals("This product might exist",error,actualErrorMessage);
    }


}
