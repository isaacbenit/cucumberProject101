package Parent.Pages;

import Parent.constants.Endpoint;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    @FindBy(id = "billing_first_name")
    private WebElement firstNameField;

    @FindBy(id = "billing_last_name")
    private WebElement lastNameField;

    @FindBy(id = "billing_address_1")
    private WebElement addressField;

    @FindBy(id = "billing_city")
    private WebElement cityField;

    @FindBy(id = "billing_postcode")
    private WebElement postcodeField;

    @FindBy(id = "billing_phone")
    private WebElement phoneField;

    @FindBy(id = "billing_email")
    private WebElement emailField;

    @FindBy(id = "place_order")
    private WebElement placeOrderButton;

    @FindBy(css = ".woocommerce-notice.woocommerce-notice--success.woocommerce-thankyou-order-received")
    private WebElement orderReceivedMessage;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openCheckoutPage() throws IllegalAccessException {
        load(Endpoint.CHECKOUT.url);
    }

    public void fillBillingDetails(String firstName,
                                   String lastName,
                                   String address,
                                   String city,
                                   String postcode,
                                   String phone,
                                   String email) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        addressField.clear();
        addressField.sendKeys(address);

        cityField.clear();
        cityField.sendKeys(city);

        postcodeField.clear();
        postcodeField.sendKeys(postcode);

        phoneField.clear();
        phoneField.sendKeys(phone);

        emailField.clear();
        emailField.sendKeys(email);
    }

    public void placeOrder() {
        placeOrderButton.click();
    }

    public boolean isOrderReceivedMessageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(orderReceivedMessage));
        return orderReceivedMessage.isDisplayed();
    }
}

