package Parent.Pages;

import Parent.Injections.DriverFactory;
import Parent.constants.Endpoint;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;


//    @FindBy(id = "menu-item-1227")
//    private WebElement storeMenu;

    @FindBy(linkText = "Anchor Bracelet")
    private WebElement anchorBracelet;

    public HomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    public void openHomePage() throws IllegalAccessException{
        load(Endpoint.STORE.url);
    }


    private void clickMenu(WebElement menuItem) {
        wait.until(ExpectedConditions.elementToBeClickable(menuItem)).click();
    }



    public void clickOnProduct() {
        anchorBracelet.click();
    }
}
