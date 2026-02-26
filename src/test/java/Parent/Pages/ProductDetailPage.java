package Parent.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".product_title.entry-title")
    private WebElement productTitle;

    @FindBy(css = "button.single_add_to_cart_button")
    private WebElement addToCartButton;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isProductTitleDisplayed() {
        return productTitle.isDisplayed();
    }

    public String getProductTitle() {
        return productTitle.getText();
    }

    public void addToCart() {
        addToCartButton.click();
    }
}
