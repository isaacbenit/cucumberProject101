package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.Pages.ProductDetailPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import Parent.Pages.HomePage;
import Parent.Pages.StorePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class filterSteps {
//    private WebDriver driver = DriverFactory.getDriver();
//    private  StorePage storePage = new StorePage(driver);
//    private HomePage homePage = new HomePage(driver);
    private boolean sortProductResult;
//    private String filterByPriceResponce;
    private boolean priceRangeResult;
    private String priceRangeResultMessage;

    protected WebDriver driver = DriverFactory.getDriver();;
    private ProductDetailPage productDetailPage;
    private WebDriverWait wait;
    protected HomePage homePage;
    protected StorePage storePage = new StorePage(driver);

    @When("I select and click category {string} from the category filter")
    public void selectCategoryFilter(String category) throws IllegalAccessException {
        storePage.openStorePage();
        String categoryValue = category.toLowerCase()
                .replace("'", "")
                .replace("'", "")
                .replace(" ", "-");
        storePage.selectCategoryByValue(categoryValue);
    }



    @Then("I should see {int} products in the {string} category")
    public void iShouldSeeProductsInCategory(int expectedCount, String category) {
        int actualCount = storePage.getDisplayedProductCountByCategory(category);
        assertEquals(
                expectedCount,
                actualCount,
                "Expected product count: " + expectedCount + ", but found: " + actualCount + " products for category: " + category
        );
//        assertEquals(actualCount, expectedCount,"Expected product count: " + expectedCount + ", but found: " + actualCount + " products for category: " + category);
    }

    @When("I filter products within the price range")
    public void i_filter_products_by_the_following_price_ranges(DataTable dataTable) {
        List<Map<String, String>> priceRanges = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : priceRanges) {
            int minPrice = Integer.parseInt(row.get("min_price"));
            int maxPrice = Integer.parseInt(row.get("max_price"));
            priceRangeResult = storePage.filterByPrice(minPrice, maxPrice);
            priceRangeResultMessage = "Should find products in price range " + minPrice + " to " + maxPrice;
            Assert.assertTrue(priceRangeResultMessage, priceRangeResult);
        }
    }

    @Then("I should see only products within the specified price range")
    public void iShouldSeeOnlyProductsWithinTheSpecifiedPriceRange() {
        Assert.assertTrue(priceRangeResultMessage, priceRangeResult);
    }

}

