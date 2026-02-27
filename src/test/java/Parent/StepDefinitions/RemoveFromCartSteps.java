package Parent.StepDefinitions;

//public class RemoveFromCartSteps {
//}
//package parent.StepsDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Parent.constants.Endpoint;
import Parent.Injections.DriverFactory;
import Parent.Pages.StorePage;

import static org.junit.Assert.assertTrue;

public class RemoveFromCartSteps {
    private WebDriver driver;
    private StorePage storePage;
    @Given("I have product in the cart")
    public void iHaveProductInTheCart() throws IllegalAccessException {
        driver = DriverFactory.getDriver();
        storePage = new StorePage(driver);
        storePage.load(Endpoint.STORE.url);
        storePage.addToCart("Blue Shoes");
    }
    @When("I click remove button")
    public void iClickRemoveButton() {
        storePage.removeFromCart();
    }
    @Then("the product should no longer be in the cart")
    public void theProductShouldNoLongerBeInTheCart() {
        assertTrue(storePage.isSuccessMessageDisplayed());
    }

}