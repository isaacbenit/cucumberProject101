package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.Pages.CartPage;
import Parent.Pages.CheckoutPage;
import Parent.Pages.HomePage;
import Parent.Pages.ProductDetailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class OrderSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @Given("I have a product in the cart")
    public void iHaveAProductInTheCart() throws IllegalAccessException {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.clickOnProduct();

        productDetailPage = new ProductDetailPage(driver);
        Assert.assertTrue(productDetailPage.isProductTitleDisplayed());

        productDetailPage.addToCart();

        cartPage = new CartPage(driver);
        cartPage.openCartPage();
        cartPage.proceedToCheckout();
    }

    @When("I place the order with valid details")
    public void iPlaceTheOrderWithValidDetails() throws IllegalAccessException {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillBillingDetails(
                "John",
                "Doe",
                "123 Test Street",
                "Test City",
                "12345",
                "1234567890",
                "john.doe@example.com"
        );
        checkoutPage.placeOrder();
    }

    @Then("I should see the order confirmation")
    public void iShouldSeeTheOrderConfirmation() {
        Assert.assertTrue(checkoutPage.isOrderReceivedMessageDisplayed());
    }
}

