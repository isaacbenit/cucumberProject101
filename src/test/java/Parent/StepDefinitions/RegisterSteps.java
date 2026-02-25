//package Parent.StepDefinitions;
//
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class RegisterSteps {
//    @When("I paste the link of my website in the search bar")
//    public void i_paste_the_link_of_my_website_in_the_search_bar() {
//    }
//    @Then("I should be directed to the home page")
//    public void i_should_be_directed_to_the_home_page() {
//    }
//
//}
package Parent.StepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import Parent.Injections.DriverFactory;
import Parent.Injections.Hooks;
import Parent.Pages.AccountPage;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RegisterSteps {
    protected static WebDriver driver;
    protected static AccountPage accountPage;

    @Given("I am on the Account Page")
    public void iAmOnTheAccountPage() throws IllegalAccessException {
        driver = DriverFactory.getDriver();
        accountPage = new AccountPage(driver);
        accountPage.openAccountPage();
    }

    @When("I register with username {string}, email {string}, and password {string}")
    public void iRegisterWithValidData(String username, String email, String password) {
        username = AccountPage.generateUniqueUsername(username);
        email = AccountPage.generateRandomEmail(email);
        accountPage.settingAllRegFields(username, email, password);
    }

    @Then("the account is registered and I get welcome message with name {string}")
    public void theAccountIsRegisteredAndIGetWelcomeMessageWithName(String expectedUsername) {
        String actualMessage = accountPage.isWelcomeMessage();
        Assert.assertTrue(actualMessage.startsWith(expectedUsername));

    }

    @When("I register with invalid {string},{string} and {string}")
    public void iRegisterWithInvalidAnd(String username, String email, String password) {
        accountPage.settingAllRegFields(username, email, password);
    }

    @When("clicks the register button")
    public void clicksTheRegisterButton() {
        accountPage.clickRegisterButton();
    }

    @Then("the user should see an error message {string}")
    public void theUserShouldSeeAnErrorMessage(String expectedMessage) {
        String actualMessage = accountPage.isErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}