package Parent.Pages;

import Parent.constants.Endpoint;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class StorePage extends BasePage {

    protected WebDriverWait wait;

    @FindBy(id = "product_cat")
    private WebElement productCategoryDropdown;

    @FindBy(css = "ul.products li.product")
    private List<WebElement> productItems;


    @FindBy(css = "ul.products.columns-4")
    private WebElement productsContainer;

    @FindBy(css = ".products .product")
    private List<WebElement> allProducts;

    @FindBy(css = ".woocommerce-loop-product__title")
    private List<WebElement> productTitles;

    @FindBy(css = ".ast-woo-product-category")
    private List<WebElement> productCategories;


    @FindBy(className = "product_title")
    private List<WebElement> productTitleElements;

    private final By sliderSelector = By.className("ui-slider-handle");
    private By filterButton = By.cssSelector("button[type='submit']");
    private By storeListPrice = By.cssSelector(".astra-shop-summary-wrap bdi");

    public StorePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openStorePage() throws IllegalAccessException{
        load(Endpoint.STORE.url);
    }

    public void selectCategoryByValue(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(productCategoryDropdown));
        dropdown.click();
        Select select = new Select(dropdown);
        select.selectByValue(value);
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        dropdown.sendKeys(Keys.ENTER);

    }

    public List<WebElement> getVisibleProductsByCategory(String category) {
        String categorySlug = category.toLowerCase()
                .replace("'", "")
                .replace("'", "")
                .replace(" ", "-");

        wait.until(ExpectedConditions.visibilityOf(productsContainer));
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));

        return productItems.stream()
                .filter(WebElement::isDisplayed)
                .filter(product -> {
                    String productClass = product.getAttribute("class").toLowerCase();
                    return productClass.contains("product_cat-" + categorySlug);
                })
                .collect(Collectors.toList());
    }

    public int getDisplayedProductCountByCategory(String category) {
        return getVisibleProductsByCategory(category).size();
    }


    public boolean filterByPrice(int startingPrice, int endingPrice) {
        while (Integer.parseInt(driver.findElement(By.className("from")).getText().replace("$", "")) < startingPrice) {
            driver.findElements(sliderSelector).get(0).sendKeys(Keys.ARROW_RIGHT);
        }

        while (Integer.parseInt(driver.findElement(By.className("to")).getText().replace("$", "")) > endingPrice) {
            driver.findElements(sliderSelector).get(1).sendKeys(Keys.ARROW_LEFT);
        }
        while (Integer.parseInt(driver.findElement(By.className("from")).getText().replace("$", "")) > startingPrice) {
            driver.findElements(sliderSelector).get(0).sendKeys(Keys.ARROW_LEFT);
        }

        while (Integer.parseInt(driver.findElement(By.className("to")).getText().replace("$", "")) < endingPrice) {
            driver.findElements(sliderSelector).get(1).sendKeys(Keys.ARROW_RIGHT);
        }

        WebElement elementBeforeClick = driver.findElement(storeListPrice);

        driver.findElements(filterButton).get(1).click();
        wait.until(ExpectedConditions.stalenessOf(elementBeforeClick));

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(storeListPrice));

        List<String> currentPrices = driver.findElements(storeListPrice).stream()
                .filter(val -> !driver.findElements(By.cssSelector("del bdi")).contains(val))
                .map(val -> val.getText().replace("$", ""))
                .toList();

        return currentPrices.stream()
                .allMatch(val -> {
                    double price = Double.parseDouble(val);
                    return price >= startingPrice && price <= endingPrice;
                });
    }
}