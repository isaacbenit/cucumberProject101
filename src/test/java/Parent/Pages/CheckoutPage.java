package Parent.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Parent.domainObject.BillingDetails;

import java.time.Duration;
import java.util.List;

public class CheckoutPage extends BasePage {

    @FindBy(id = "billing_first_name") private WebElement firstnameField;
    @FindBy(id = "billing_last_name") private WebElement lastnameField;
    @FindBy(id = "billing_company") private WebElement companyField;
    @FindBy(id = "billing_country") private WebElement countrySelect;
    @FindBy(id = "billing_address_1") private WebElement streetField;
    @FindBy(id = "billing_address_2") private WebElement apartmentField;
    @FindBy(id = "billing_city") private WebElement cityField;
    @FindBy(id = "billing_state") private WebElement stateSelect;
    @FindBy(id = "billing_postcode") private WebElement zipField;
    @FindBy(id = "billing_phone") private WebElement phoneField;
    @FindBy(id = "billing_email") private WebElement emailField;
    @FindBy(id = "order_comments") private WebElement commentsField;

    @FindBy(css = "ul.wc_payment_methods") private WebElement paymentMethods;
    @FindBy(id = "place_order") private WebElement placeOrderButton;

    @FindBy(css = ".woocommerce-notice") private WebElement confirmationMessage;
    @FindBy(css = ".woocommerce-order-overview__order.order") private WebElement orderNumber;

    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void type(WebElement element, String value) {
        if (value != null && !value.isEmpty()) {
            element.sendKeys(value);
        }
    }

    public CheckoutPage setBillingDetails(BillingDetails billing) {

        // Fill basic info
        firstnameField.sendKeys(billing.getFirstname());
        lastnameField.sendKeys(billing.getLastname());
        companyField.sendKeys(billing.getCompany());

        // COUNTRY
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countrySelect);
        new Select(countrySelect).selectByVisibleText(billing.getCountry());

        // Address fields
        streetField.sendKeys(billing.getLastAddressOne());
        apartmentField.sendKeys(billing.getAdditionText());
        cityField.sendKeys(billing.getCity());

        // STATE - wait and fallback
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", stateSelect);
        try {
            wait.until(d -> stateSelect.findElements(By.tagName("option")).size() > 1);
            new Select(stateSelect).selectByVisibleText(billing.getStateName());
        } catch (Exception e) {
            // Fallback if dropdown not available or empty
            stateSelect.sendKeys(billing.getStateName());
        }

        // Zip, phone, email, comments
        zipField.sendKeys(billing.getZipCode());
        phoneField.sendKeys(billing.getPhone());
        emailField.sendKeys(billing.getEmail());
        commentsField.sendKeys(billing.getAdditionText());

        return this;
    }

    public CheckoutPage selectPaymentMethod(String method) {
        List<WebElement> options =
                paymentMethods.findElements(By.cssSelector("li.wc_payment_method label"));

        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(method)) {
                option.click();
                break;
            }
        }
        return this;
    }

    public CheckoutPage placeOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
        return this;
    }

    public String getConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
        return confirmationMessage.getText();
    }

    public String getOrderNumber() {
        wait.until(ExpectedConditions.visibilityOf(orderNumber));
        return orderNumber.getText();
    }
}