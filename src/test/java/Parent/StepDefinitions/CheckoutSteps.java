package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.Pages.CartPage;
import Parent.Pages.CheckoutPage;
import Parent.Pages.StorePage;
import Parent.domainObject.BillingDetails;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class CheckoutSteps {
    protected WebDriver driver;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    @When("I check out add my billing detail and place order")
    public void iCheckOutAddMyBillingDetailAndPlaceOrder(io.cucumber.datatable.DataTable table) {
        driver = DriverFactory.getDriver();
        cartPage = new CartPage(driver);
        cartPage.clickCheckoutButton();
        checkoutPage = new CheckoutPage(driver);

        List<Map<String, String>> data = table.asMaps(String.class, String.class);

        Map<String, String> row = data.get(0); // first row of data

        BillingDetails billing = new BillingDetails(
                row.get("First name"),
                row.get("Last name"),
                row.get("Company name"),
                row.get("Country/Region"),
                row.get("Street address"),
                row.get("City"),
                row.get("State"),
                row.get("ZIP code"),
                row.get("Email address"),
                row.get("Phone"),
                "Automation order"
        );


        checkoutPage.setBillingDetails(billing);
        checkoutPage.placeOrder();
    }

    @Then("the order should be placed successfully")
    public void theOrderShouldBePlacedSuccessfully() {
        Assert.assertTrue("Order confirmation message not displayed",
                checkoutPage.getConfirmationMessage().contains("Thank you")
        );
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedMessage) {
        String actualMessage = checkoutPage.getErrorMessage();
        Assert.assertEquals("Error message mismatch!", expectedMessage, actualMessage);
    }

}
