package Parent.StepDefinitions;

import Parent.Injections.DriverFactory;
import Parent.Pages.BasePage;
import Parent.Pages.HomePage;
import Parent.Pages.ProductDetailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailSteps {

    protected WebDriver driver;
    private ProductDetailPage productDetailPage;
    private WebDriverWait wait;
    protected HomePage homePage;


    @Given("I am on the store page")
    public void iAmOnTheStorePage() throws IllegalAccessException {
        driver = DriverFactory.getDriver();
       homePage = new HomePage(driver);
       homePage.openHomePage();
    }
    @When("I click on any product on store page")
    public void iClickOnAnyProductOnStorePage() {
        homePage.clickOnProduct();

    }
    @Then("I should see all product information")
    public void iShouldSeeAllProductInformation() {
        productDetailPage = new ProductDetailPage(driver);
        Assert.assertTrue(productDetailPage.isProductTitleDisplayed());
        String productName = productDetailPage.getProductTitle();
        Assert.assertEquals("Anchor Bracelet",  productName);


    }

}


