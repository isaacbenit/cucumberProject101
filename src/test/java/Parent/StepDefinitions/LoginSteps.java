package Parent.StepDefinitions;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import Parent.constants.Endpoint;
import Parent.Injections.DriverFactory;
import Parent.Pages.AccountPage;

import java.util.Map;

import static Parent.StepDefinitions.RegisterSteps.driver;


public class LoginSteps {
//    private WebDriver driver;
//    public static AccountPage accountPage;


    @When("I log in with valid credentials")
    public void iLogInWithValidCredentials(DataTable table) {
        Map<String, String> credentials = table.asMap(String.class, String.class);
        RegisterSteps.accountPage.settingAllLoginFields(credentials.get("username"), credentials.get("password"));
    }

    @Then("I should be redirected to the Dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        RegisterSteps.accountPage.isWelcomeMessage();
    }

    @Then("I should see a welcome message with {string}")
    public void iShouldSeeAWelcomeMessageWith(String expectedUsername) {
        String actual = new AccountPage(driver).isWelcomeMessage();
        org.junit.Assert.assertEquals("Usernames are not identical", expectedUsername, actual);
    }

    @When("I log in with the credentials I just registered")
    public void iLogInWithTheCredentialsIJustRegistered() {
        String username = Parent.Utils.TestData.getLastRegisteredUsername();
        String password = Parent.Utils.TestData.getLastRegisteredPassword();
        try {
            // If the login form is still visible, perform an explicit login.
            // After registration we are often already logged in and the login form disappears,
            // in that case this step becomes a no-op.
            RegisterSteps.accountPage.settingAllLoginFields(username, password);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            // Login form not present; assume we are already logged in.
        }
    }

    @Then("I should see a welcome message for my account")
    public void iShouldSeeAWelcomeMessageForMyAccount() {
        String expectedUsername = Parent.Utils.TestData.getLastRegisteredUsername();
        String actual = new AccountPage(driver).isWelcomeMessage();
        org.junit.Assert.assertEquals("Welcome message should show registered username", expectedUsername, actual);
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        RegisterSteps.accountPage.settingAllLoginFields(username, password);

    }

    @Then("I should see a login error message {string}")
    public void iShouldSeeALoginErrorMessage(String expectedMessage) {
        String actual = new AccountPage(driver).isErrorMessage();
        org.junit.Assert.assertEquals("Login error message did not match", expectedMessage, actual);
    }

}