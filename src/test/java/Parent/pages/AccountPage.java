package Parent.Pages;//package Parent.pages;

import Parent.StepDefinitions.RegisterSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Parent.constants.Endpoint;

import java.time.Duration;
import java.util.UUID;

public class AccountPage extends BasePage {

//    fields for login form

    @FindBy(id = "username")
    private WebElement usernameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(css = "button[value='Log in']")
    private WebElement loginButton;
    @FindBy(css = "div.woocommerce-MyAccount-content p strong")
    private WebElement welcomeUsername;
    @FindBy(css = "ul.woocommerce-error li")
    private WebElement errorMessage;

// fields for registration form

    @FindBy(id = "reg_username")
    private WebElement username_regField;
    @FindBy(id = "reg_email")
    private WebElement email_regField;
    @FindBy(id = "reg_password")
    private WebElement password_regField;
    @FindBy(css = "button[name='register']")
    private WebElement registerButton;


    //    fields for update user information form
    @FindBy(id ="account_first_name")
    private WebElement firstNameField_updateField;
    @FindBy(id = "account_last_name")
    private WebElement lastNameField_updateField;
    @FindBy(id ="account_display_name")
    private WebElement displayName_updateField;
    @FindBy(id = "account_email")
    private WebElement emailField_updateField;
    @FindBy(id = "password_current")
    private WebElement currentPasswordField_updateField;
    @FindBy(id = "password_1")
    private WebElement passwordField1_updateField;
    @FindBy(id = "password_2")
    private WebElement passwordField2_updateField;
    @FindBy(css = "button[value='Save changes'] ")
    private WebElement saveChangesButton_UpdateField;
    @FindBy(css = "a[href='https://askomdch.com/account/edit-account/']")
    private WebElement AccountDetailLink;
    @FindBy(css = "[class=\"woocommerce-message\"]")
    private WebElement successUpdateMessage;
    @FindBy(css = "[class=\"woocommerce-error\"]")
    private WebElement ErrorPasswordMessageForUpdate;


    public AccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openAccountPage() throws IllegalAccessException {
        load(Endpoint.ACCOUNT.url);
    }

    //registration method
    public void registerUsername(String username) {
        username_regField.sendKeys(username);
    }

    public void registerEmail(String email) {
        email_regField.sendKeys(email);
    }

    public void registerPassword(String password) {
        password_regField.sendKeys(password);
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public void settingAllRegFields(String username, String email, String password) {
        registerUsername(username);
        registerEmail(email);
        registerPassword(password);
    }
    public static String generateRandomEmail(String email){
        String[] part = email.split("@");
        return part[0] + "_"+ UUID.randomUUID() + "@" + part[1];
    }

    public static String generateUniqueUsername(String username) {
        return username + "_" + UUID.randomUUID().toString().substring(0,4);
    }

//   Login methods

    public void fillUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void fillPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void settingAllLoginFields(String username, String password) {
        fillPassword(password);
        fillUsername(username);
        clickLoginButton();
    }

    public String isWelcomeMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msg = wait.until(ExpectedConditions.visibilityOf(welcomeUsername));
        return msg.getText().trim();
    }

    public String isErrorMessage() {
        try {
            return driver.findElement(By.cssSelector("ul.woocommerce-error li")).getText().trim();
        } catch (NoSuchElementException e) {
            return driver.findElement(By.cssSelector(".woocommerce-error")).getText().trim();
        }
    }
//edit account methods

    public void UpdateFirstName(String firstName) {
        firstNameField_updateField.clear();
        firstNameField_updateField.sendKeys(firstName);
    }
    public void UpdateLastName(String lastName) {
        lastNameField_updateField.clear();
        lastNameField_updateField.sendKeys(lastName);
    }
//    public  void UpdateDisplayName(String displayName) {
//        displayName_updateField.clear();
//        displayName_updateField.sendKeys(displayName);
//    }
//    public void UpdateEmail(String email) {
//        emailField_updateField.clear();
//        email_regField.sendKeys(email);
//    }
    public void EnterPassword(String password) {
        currentPasswordField_updateField.sendKeys(password);
    }
    public void UpdateCurrentPassword(String newPassword) {
        passwordField1_updateField.sendKeys(newPassword);
        passwordField2_updateField.sendKeys(newPassword);
    }
    public void clickSaveChangesButton() {
        saveChangesButton_UpdateField.click();
    }
    public void clickAccountDetailLink() {
        AccountDetailLink.click();
    }
    public String getSuccessUpdateMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(successUpdateMessage));
        return successUpdateMessage.getText();
    }
    public String getErrorPasswordMessageForUpdate() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(ErrorPasswordMessageForUpdate));
        return ErrorPasswordMessageForUpdate.getText();
    }
    public void settingAllUpdateFields(String firstName,String lastName,String password, String newPassword) {
        UpdateFirstName(firstName);
        UpdateLastName(lastName);
        EnterPassword(password);
        UpdateCurrentPassword(newPassword);
        clickSaveChangesButton();
    }

}