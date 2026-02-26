package Parent.StepDefinitions;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Parent.Pages.AccountPage;

import java.util.Map;

import static Parent.StepDefinitions.RegisterSteps.driver;
import static org.junit.Assert.assertEquals;


public class LoginSteps {


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
        assertEquals("Usernames are not identical",actual,expectedUsername);
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        RegisterSteps.accountPage.settingAllLoginFields(username, password);

    }

    @Then("I should see a login error message {string}")
    public void iShouldSeeALoginErrorMessage(String expectedMessage) {
        String actual = new AccountPage(driver).isErrorMessage();
        System.out.println(actual);
        System.out.println(expectedMessage);
        assertEquals("Error message did not match", expectedMessage, actual);
    }

    private String errorMessage(String error) {

        if (error == null) {
            return "No error";
        }
        if (error.contains("Unknown email address. Check again or try your username.") ||
                error.contains("Username is required.") ||
                error.contains("The password field is empty.")) {
            return "One of your username or password is wrong or empty";
        }
        return "Nothing matching";

    }

}