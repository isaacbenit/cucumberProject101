//package Parent.StepDefinitions;
//
//import io.cucumber.datatable.DataTable;
//import Parent.Pages.AccountPage;
//import io.cucumber.java.PendingException;
//import io.cucumber.java.Transpose;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.junit.Assert;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.Map;
//
//public class UpdateAccountSteps {
//    private WebDriver driver;
//    public static AccountPage accountPage;
//
//
//    @When("I update my password")
//    public void iUpdateMyPassword(DataTable table) {
//        RegisterSteps.accountPage.clickAccountDetailLink();
//        Map<String, String> credentials = table.transpose().asMap(String.class, String.class);
//        RegisterSteps.accountPage.settingAllUpdateFields(credentials.get("firstName"),credentials.get("lastName"),credentials.get("password"), credentials.get("newPassword"));
//        RegisterSteps.accountPage.clickSaveChangesButton();
//    }
//
//    @Then("I should see a confirmation message")
//    public void iShouldSeeAConfirmationMessage() {
////        Assert.assertTrue(RegisterSteps.accountPage.getSuccessUpdateMessage().contains("Account details changed successfully."));
//        Assert.assertEquals("Account details changed successfully.", RegisterSteps.accountPage.getSuccessUpdateMessage());
//    }
//
//
//    @When("I update my password using an invalid password")
//    public void iUpdateMyPasswordUsingAnInvalidPassword(DataTable table) {
//        RegisterSteps.accountPage.clickAccountDetailLink();
//        Map<String, String> credentials = table.transpose().asMap(String.class, String.class);
//        RegisterSteps.accountPage.settingAllUpdateFields(credentials.get("firstName"),credentials.get("lastName"),credentials.get("password"), credentials.get("newPassword"));
//        RegisterSteps.accountPage.clickSaveChangesButton();
//    }
//
//    @Then("I should see an error message")
//    public void iShouldSeeAnErrorMessage() {
//        Assert.assertEquals("Your current password is incorrect.",RegisterSteps.accountPage.getErrorPasswordMessageForUpdate());
//    }
//}

package Parent.StepDefinitions;

import io.cucumber.datatable.DataTable;
import Parent.Pages.AccountPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class UpdateAccountSteps {
    private WebDriver driver;
    public static AccountPage accountPage;


    @When("I update my password")
    public void iUpdateMyPassword(DataTable table) {
        RegisterSteps.accountPage.clickAccountDetailLink();
        Map<String, String> credentials = table.transpose().asMap(String.class, String.class);
        RegisterSteps.accountPage.settingAllUpdateFields(credentials.get("firstName"),credentials.get("lastName"),credentials.get("password"), credentials.get("newPassword"));
    }

    @Then("I should see a confirmation message")
    public void iShouldSeeAConfirmationMessage() {
        Assert.assertEquals("Account details changed successfully.", RegisterSteps.accountPage.getSuccessUpdateMessage());
    }


    @When("I update my password using an invalid password")
    public void iUpdateMyPasswordUsingAnInvalidPassword(DataTable table) {
        RegisterSteps.accountPage.clickAccountDetailLink();
        Map<String, String> credentials = table.transpose().asMap(String.class, String.class);
        RegisterSteps.accountPage.settingAllUpdateFields(credentials.get("firstName"),credentials.get("lastName"),credentials.get("password"), credentials.get("newPassword"));
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        Assert.assertEquals("Your current password is incorrect.",RegisterSteps.accountPage.getErrorPasswordMessageForUpdate());
    }
}