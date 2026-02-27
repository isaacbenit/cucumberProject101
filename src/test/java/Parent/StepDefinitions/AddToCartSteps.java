package Parent.StepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import Parent.Injections.DriverFactory;
import Parent.Pages.CartPage;
import Parent.Pages.StorePage;


public class AddToCartSteps {
    protected WebDriver driver;
    protected StorePage storePage;
    protected CartPage cartPage;


    @When("I add a {string} to the cart")
    public void iAddAToTheCart(String product) {
        driver = DriverFactory.getDriver();
        storePage = new StorePage(driver);
        SearchProductStep.storePage.addToCart(product);
    }

    @Then("I should see {int} {string} in the cart")
    public void iShouldSeeInTheCart(int quantity,String product) {
        cartPage = new CartPage(driver);
        String names = cartPage.getProductNames();
        Assert.assertEquals(product,names);
        Assert.assertEquals(quantity,cartPage.getProductQuantity());
    }


}