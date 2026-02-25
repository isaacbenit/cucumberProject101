package Parent.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class StorePage extends BasePage{
    public StorePage (WebDriver driver){ super (driver);}

    @FindBy(id = "woocommerce-product-search-field-0") private WebElement searchElement;
    @FindBy(css = "button[value='Search']") private WebElement searchButton;
    private By productTitle = By.cssSelector("h1.entry-title");
    private By errorMessage = By.cssSelector(".woocommerce-no-products-found");
    private By sort = By.cssSelector(".orderby");
    private By deletedPrice = By.cssSelector(".price del bdi");
    private By realPrice = By.cssSelector(".price bdi");


    public void enterProductName(String productName){
        searchElement.sendKeys(productName);
    }
    public void searchButton(){
        searchButton.click();
    }

    public String getProductTitle(){
        //wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
       return driver.findElement(productTitle).getText();

    }
    public String getErrorMessage(){
           return driver.findElement(errorMessage).getText();

    }
    public void selectPrice(String sortOption){
        if (sortOption.equals("low to high")) {
            new Select(driver.findElement(sort)).selectByValue("price");
        } else if(sortOption.equals("high to low")){
            new Select(driver.findElement(sort)).selectByValue("price-desc");
        }
    }
    //public boolean isOrdered()

}
