package Parent.Pages;

import Parent.constants.Endpoint;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class StorePage extends BasePage {
    public StorePage (WebDriver driver){ super (driver);}


    @FindBy(id = "woocommerce-product-search-field-0") private WebElement searchElement;
    @FindBy(css = "button[value='Search']") private WebElement searchButton;
    private By productTitle = By.cssSelector("h1.entry-title");
    private By errorMessage = By.cssSelector(".woocommerce-no-products-found");
    private By sort = By.cssSelector(".orderby");
    private By deletedPrice = By.cssSelector(".price del bdi");
    private By realPrice = By.cssSelector(".price bdi");
    @FindBy(id = "product_cat")
    private WebElement productCategoryDropdown;

    @FindBy(css = "ul.products li.product")
    private List<WebElement> productItems;

    @FindBy(css = "ul.products.columns-4")
    private WebElement productsContainer;
    private final By sliderSelector = By.className("ui-slider-handle");
    private By filterButton = By.cssSelector("button[type='submit']");
    private By storeListPrice = By.cssSelector(".astra-shop-summary-wrap bdi");

    @FindBy(css = "a[title='View cart']") private WebElement ViewCartLink;
    @FindBy(css = ".woocommerce-message") private WebElement successMessage;

    public void addToCart(String productName){
        By addToCartButton = By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ViewCartLink)).click();

    }
    public void removeFromCart(){
        By removeButton = By.cssSelector(".remove");
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }
    public boolean isSuccessMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
    }


    public void enterProductName(String productName){
        searchElement.sendKeys(productName);
    }
    public void searchButton(){
        searchButton.click();
    }

    public String getProductTitle(){
        return driver.findElement(productTitle).getText();

    }
    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();

    }
    public void sortByPrice(String sortOption){
        if (sortOption.equals("low to high")) {
            new Select(driver.findElement(sort)).selectByValue("price");
        } else if(sortOption.equals("high to low")){
            new Select(driver.findElement(sort)).selectByValue("price-desc");
        }
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
//                .replace("'", "")
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

    // we use the locator by because it avoids StaleElementReferenceException

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

    public String getPriceSortOrder() {
        List<Double> prices = driver.findElements(storeListPrice).stream()
                .filter(val -> !driver.findElements(By.cssSelector("del bdi")).contains(val))
                .map(val -> Double.parseDouble(val.getText().replace("$", "")))
                .toList();

        boolean ascending = true;
        boolean descending = true;

        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) < prices.get(i - 1)) {
                ascending = false;
            }
            if (prices.get(i) > prices.get(i - 1)) {
                descending = false;
            }
        }

        if (ascending) return "low to high";
        if (descending) return "high to low";
        return "unsorted";
    }


}