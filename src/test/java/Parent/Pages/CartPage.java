package Parent.Pages;

import Parent.constants.Endpoint;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object for the shopping cart (/cart).
 * User can review items and proceed to checkout.
 */
public class CartPage extends BasePage {

    @FindBy(css = "a.checkout-button, .checkout-button a, a[href*='checkout']")
    private WebElement proceedToCheckoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openCartPage() throws IllegalAccessException {
        load(Endpoint.CART.url);
    }

    /** Clicks "Proceed to checkout" to go to the checkout page. */
    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
        proceedToCheckoutButton.click();
    }
}
