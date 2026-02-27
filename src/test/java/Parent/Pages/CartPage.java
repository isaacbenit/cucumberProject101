package Parent.Pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class CartPage extends BasePage{
    public CartPage(WebDriver driver) {
        super(driver);

    }

    @FindBy(css = "td[class='product-name'] a") private WebElement productNames;
    @FindBy(css = "input[type ='number']") private WebElement productQuantityField;
    @FindBy(css = ".checkout-button") private WebElement checkoutButton;


    public String getProductNames(){
        wait.until(ExpectedConditions.visibilityOf(productNames));
        return productNames.getText();
    }


    public int getProductQuantity(){
        wait.until(ExpectedConditions.visibilityOf(productQuantityField));
        return Integer.parseInt(Objects.requireNonNull(productQuantityField.getAttribute("value")));
    }

    public CheckoutPage clickCheckoutButton(){
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
