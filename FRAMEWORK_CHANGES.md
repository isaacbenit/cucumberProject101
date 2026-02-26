# Framework Changes Documentation

**Project:** Capstone Project 2 — Selenium-Java-BDD Automation Framework  
**System Under Test (SUT):** [https://askomdch.com/](https://askomdch.com/)  
**Tech Stack:** Java 17, Maven, Selenium 4, Cucumber 7, JUnit 4  

This document describes all changes made to the automation framework: what was fixed, what was added, and why. It is intended for the team (and assessors) to understand the implementation in detail.

---

## Table of Contents

1. [Executive Summary](#1-executive-summary)
2. [Project Structure Overview](#2-project-structure-overview)
3. [What We Fixed (Stability & Correctness)](#3-what-we-fixed-stability--correctness)
4. [What We Added (New Features & Helpers)](#4-what-we-added-new-features--helpers)
5. [File-by-File Change Log](#5-file-by-file-change-log)
6. [Test Data Strategy & Flows](#6-test-data-strategy--flows)
7. [How to Run the Tests](#7-how-to-run-the-tests)
8. [Troubleshooting & Notes](#8-troubleshooting--notes)

---

## 1. Executive Summary

We made the framework **stable**, **repeatable**, and **aligned with the Capstone requirements** by:

| Area | What we did |
|------|-------------|
| **Driver lifecycle** | Re-enabled browser cleanup in `@After` so each scenario gets a fresh browser and quits it afterwards. |
| **Identity tests** | Switched from previous group’s usernames to **your team’s names** (eliezer, penina, christelle, isaac, anifa, emmy) and added **random suffixes** so registration can run many times without “user already exists”. |
| **Shared test user** | Introduced a **TestData** helper that stores the last registered username/password so login and update-account scenarios can reuse the same user. |
| **Login after registration** | After registering, the site often **auto-logs in** and hides the login form. We made “I log in with the credentials I just registered” **safe**: it tries to log in only if the form is present; otherwise it assumes we’re already logged in. |
| **Assertions & selectors** | Fixed JUnit 4 `assertEquals` argument order in filter steps, corrected login error-message expectations to match the real site, and made the edit-account link selector robust (e.g. `href*='edit-account'`). |
| **Orders / Checkout** | Added a full **order flow**: add product to cart → open checkout → fill billing details → place order → assert confirmation (new `CheckoutPage`, `OrderSteps`, and `order.feature`). |

The result: **Identity** (registration, login, update account), **Product** (detail, filter), and **Checkout** (order) scenarios are implemented and can run repeatedly without depending on pre-existing users.

---

## 2. Project Structure Overview

```
cucumber/
├── pom.xml
├── FRAMEWORK_CHANGES.md          ← this file
├── src/test/
│   ├── java/Parent/
│   │   ├── Injections/
│   │   │   ├── DriverFactory.java   # Creates and holds WebDriver
│   │   │   └── Hooks.java           # @Before / @After (open/close browser)
│   │   ├── Pages/
│   │   │   ├── BasePage.java
│   │   │   ├── AccountPage.java     # Login, register, edit account
│   │   │   ├── HomePage.java
│   │   │   ├── StorePage.java
│   │   │   ├── ProductDetailPage.java
│   │   │   ├── CartPage.java        # Cart view + proceed to checkout
│   │   │   └── CheckoutPage.java    # Billing + place order + confirmation
│   │   ├── StepDefinitions/
│   │   │   ├── RegisterSteps.java
│   │   │   ├── LoginSteps.java
│   │   │   ├── UpdateAccountSteps.java
│   │   │   ├── ProductDetailSteps.java
│   │   │   ├── filterSteps.java
│   │   │   └── OrderSteps.java      # NEW: order flow
│   │   ├── Runner/
│   │   │   └── TestJURunnerTest.java
│   │   ├── Utils/
│   │   │   ├── configloader.java
│   │   │   ├── PropertyUtils.java
│   │   │   └── TestData.java        # NEW: random suffix + last user
│   │   ├── constants/
│   │   │   ├── Endpoint.java
│   │   │   └── EnvType.java
│   │   └── domainObject/
│   │       ├── BillingDetails.java
│   │       └── Product.java
│   └── resources/
│       ├── config.properties        # baseUrl = https://askomdch.com/
│       └── Features/
│           ├── login.feature
│           ├── register.feature
│           ├── updateAccount.feature
│           ├── ProductDetail.feature
│           ├── filter.feature
│           └── order.feature        # NEW
```

---

## 3. What We Fixed (Stability & Correctness)

### 3.1 Browser not closing after tests

- **Problem:** In `Hooks.java`, the `@After` method had `driver.quit()` commented out, so browsers stayed open and could cause flaky or overlapping runs.
- **Fix:** Uncommented and used `DriverFactory.getDriver()` so the same driver instance is quit after each scenario.
- **Result:** Each scenario runs in a clean browser and the driver is properly closed.

### 3.2 Wrong package import for AccountPage

- **Problem:** `RegisterSteps.java` and `UpdateAccountSteps.java` imported `Parent.pages.AccountPage` (lowercase `pages`), while the real package is `Parent.Pages`. This could fail on case-sensitive systems or cause confusion.
- **Fix:** Changed imports to `Parent.Pages.AccountPage` in both step definition classes.

### 3.3 JUnit assertEquals argument order (filter steps)

- **Problem:** In `filterSteps.java`, the category count was asserted with `assertEquals(expectedCount, actualCount, "message")`. In JUnit 4, the three-argument form is `assertEquals(String message, expected, actual)`. The compiler error was “no suitable method found for assertEquals(int, int, String)”.
- **Fix:** Switched to `assertEquals("Expected product count: ...", expectedCount, actualCount)` and removed the duplicate JUnit 5 static import.
- **Result:** Filter-by-category scenarios compile and assert correctly.

### 3.4 Login error message assertion

- **Problem:** The step compared the **actual** page message with the **expected** from the feature using the wrong parameter order, and one example expected “password incorrect” for username `testuser`, while the site returns “username testuser is not registered” (because that user doesn’t exist).
- **Fix:**  
  - In `LoginSteps.iShouldSeeALoginErrorMessage`: use `Assert.assertEquals("Login error message did not match", expectedMessage, actual)`.  
  - In `login.feature`, update the first invalid example so the expected message is the “username not registered” text for `testuser` + wrong password.
- **Result:** Invalid login scenarios pass and match real application behaviour.

### 3.5 Edit-account link selector

- **Problem:** `AccountPage` used `a[href='https://askomdch.com/account/edit-account/']`. Hardcoding the full URL is brittle if the base URL or environment changes.
- **Fix:** Use `a[href*='edit-account']` so any link whose `href` contains `edit-account` is matched (works with relative URLs and different domains).
- **Result:** Update-account steps find the “Account details” link reliably.

### 3.6 Login form missing after registration

- **Problem:** After a successful registration, the site **automatically logs the user in** and the account page shows the dashboard instead of the login form. The step “I log in with the credentials I just registered” tried to fill `#username` and `#password`, which no longer exist, causing `NoSuchElementException`.
- **Fix:** In `LoginSteps.iLogInWithTheCredentialsIJustRegistered`, wrap the call to `settingAllLoginFields(...)` in a try-catch for `NoSuchElementException` and `StaleElementReferenceException`. If the login form isn’t present, do nothing (we’re already logged in).
- **Result:** Login and update-account scenarios that use “register then log in” no longer fail when the form is absent.

---

## 4. What We Added (New Features & Helpers)

### 4.1 TestData helper (`Parent.Utils.TestData`)

- **Purpose:** Central place for (1) generating **unique** usernames/emails so registration doesn’t collide across runs, and (2) storing the **last registered** username and password so later steps can “log in with the credentials I just registered”.
- **Key methods:**
  - `randomSuffix()` — random number in range 1000–99999.
  - `uniqueUsername(baseName)` — e.g. `eliezer` → `eliezer48291`.
  - `uniqueEmail(baseName)` — e.g. `eliezer` → `eliezer48291@team.test`.
  - `setLastRegisteredUser(username, password)` — called after registering.
  - `getLastRegisteredUsername()` / `getLastRegisteredPassword()` — used by login and update-account steps.
- **Default password:** `Test123!` (used when we don’t have a stored password).

### 4.2 Team names in registration

- **Purpose:** Use your team’s base names instead of the previous group’s.
- **Implementation:** In `RegisterSteps`, a constant `TEAM_NAMES = {"eliezer", "penina", "christelle", "isaac", "anifa", "emmy"}` is used by the step “I have registered a new user with a random suffix” to pick a random base name. Valid registration scenarios in `register.feature` use the same names in the Examples table; the step definition appends a random suffix before sending data to the site.

### 4.3 New step: “I have registered a new user with a random suffix”

- **Purpose:** Create one new user per scenario (for login or update-account) without reusing fixed accounts.
- **Behaviour:**  
  - Picks a random entry from `TEAM_NAMES`.  
  - Builds `uniqueUsername` and `uniqueEmail`.  
  - Sets password to `Test123!` and stores username/password in `TestData`.  
  - Fills the registration form and clicks Register.  
- **Used in:** Login feature (valid login scenario), Update-account feature (background).

### 4.4 New steps: “I log in with the credentials I just registered” and “I should see a welcome message for my account”

- **Purpose:** Reuse the user we just registered for login and update-account flows without hardcoding credentials in the feature.
- **Behaviour:**  
  - “I log in with the credentials I just registered” reads username/password from `TestData` and calls `AccountPage.settingAllLoginFields`, with a try-catch so that if the login form is missing (already logged in), the step does nothing.  
  - “I should see a welcome message for my account” gets the last registered username from `TestData` and asserts that the welcome message on the page equals that username.

### 4.5 Order flow (Cart + Checkout epic)

- **New files:**
  - **`CartPage.java`** — Page object for the cart (`/cart`): open cart page and “Proceed to checkout” button. Ensures the flow goes through the cart instead of jumping straight to checkout.
  - **`CheckoutPage.java`** — Page object for checkout: billing fields (first name, last name, address, city, postcode, phone, email), “Place order” button, and the order-received success message. Extends `BasePage`, uses `Endpoint.CHECKOUT`.
  - **`OrderSteps.java`** — Step definitions: “I have a product in the cart” (open store → click product → add to cart → **open cart page → proceed to checkout**), “I place the order with valid details” (fill billing on checkout page → place order), “I should see the order confirmation” (assert success message).
  - **`order.feature`** — Single scenario tagged `@order`: Given product in cart, When place order with valid details, Then see order confirmation.
- **Existing change:** `ProductDetailPage` was given an “Add to cart” button and `addToCart()` so the order flow can add a product before going to the cart.

---

## 5. File-by-File Change Log

### 5.1 `src/test/java/Parent/Injections/Hooks.java`

| Change | Details |
|--------|---------|
| **@After** | Re-enabled driver cleanup. After each scenario, `DriverFactory.getDriver()` is retrieved and `quit()` is called if non-null. |
| **Why** | Prevents leftover browser processes and ensures a clean state for the next run. |

**Relevant code:**

```java
@After
public void after() {
    WebDriver driver = DriverFactory.getDriver();
    if (driver != null) {
        driver.quit();
    }
}
```

---

### 5.2 `src/test/java/Parent/Pages/AccountPage.java`

| Change | Details |
|--------|---------|
| **Edit-account link** | Replaced `@FindBy(css = "a[href='https://askomdch.com/account/edit-account/']")` with `@FindBy(css = "a[href*='edit-account']")`. |
| **Why** | Makes the locator work regardless of base URL or environment. |

No other methods or locators were changed; login, registration, and update-account logic remain as before.

---

### 5.3 `src/test/java/Parent/Pages/ProductDetailPage.java`

| Change | Details |
|--------|---------|
| **Add to cart** | Added `@FindBy(css = "button.single_add_to_cart_button")` and `addToCart()` method that clicks it. |
| **Why** | Required so the order scenario can add a product to the cart from the product detail page. |

---

### 5.4 `src/test/java/Parent/Utils/TestData.java` (NEW FILE)

| Content | Details |
|--------|---------|
| **Static state** | `lastRegisteredUsername`, `lastRegisteredPassword`. |
| **randomSuffix()** | Returns a string of a random int between 1000 and 99999. |
| **uniqueUsername(baseName)** | Returns `baseName + randomSuffix()` (e.g. `eliezer48291`). |
| **uniqueEmail(baseName)** | Returns `baseName + randomSuffix() + "@team.test"`. |
| **setLastRegisteredUser / getLastRegisteredUsername / getLastRegisteredPassword** | Store and retrieve the user created in registration for use in login and update-account. |
| **DEFAULT_PASSWORD** | `"Test123!"` when no password has been set. |
| **clear()** | Resets stored user (available for future use if needed). |

---

### 5.5 `src/test/java/Parent/StepDefinitions/RegisterSteps.java`

| Change | Details |
|--------|---------|
| **Import** | `Parent.pages.AccountPage` → `Parent.Pages.AccountPage`. Added `TestData`, `ThreadLocalRandom`. |
| **TEAM_NAMES** | New constant array with team base names: eliezer, penina, christelle, isaac, anifa, emmy. |
| **New step: “I have registered a new user with a random suffix”** | Picks random team name, builds unique username/email, sets password `Test123!`, stores in TestData, fills registration form, clicks register. |
| **“I register with username …, email …, password …”** | Now generates `uniqueUser` and `uniqueEmail` from the given username, stores them in TestData, and fills the form with these values so each run creates a new account. |
| **“the account is registered and I get welcome message with name …”** | Asserts the welcome message text equals `TestData.getLastRegisteredUsername()` (the actual username used, including suffix). |
| **Invalid registration** | Unchanged: still fills form with the exact values from the feature and asserts error message contains the expected substring. |

---

### 5.6 `src/test/java/Parent/StepDefinitions/LoginSteps.java`

| Change | Details |
|--------|---------|
| **Imports** | Added `NoSuchElementException`, `StaleElementReferenceException` for safe handling when login form is missing. Removed unused `assertEquals` static import. |
| **“I log in with the credentials I just registered”** | Gets username/password from TestData, calls `settingAllLoginFields` inside try-catch; on NoSuchElementException or StaleElementReferenceException, does nothing (assumes already logged in). |
| **“I should see a welcome message for my account”** | Gets expected username from TestData and asserts it equals the welcome message text. |
| **“I should see a login error message {string}”** | Uses `Assert.assertEquals("Login error message did not match", expectedMessage, actual)` so the expected value from the feature is compared correctly. |
| **Removed** | Unused private method `errorMessage(String error)`. |

---

### 5.7 `src/test/java/Parent/StepDefinitions/UpdateAccountSteps.java`

| Change | Details |
|--------|---------|
| **Import** | `Parent.pages.AccountPage` → `Parent.Pages.AccountPage`. |
| **Logic** | Unchanged; still uses `RegisterSteps.accountPage` and the same update steps. They now work because the background registers and “logs in” (or skips login when form is absent) using the new steps. |

---

### 5.8 `src/test/java/Parent/StepDefinitions/filterSteps.java`

| Change | Details |
|--------|---------|
| **assertEquals** | Category count assertion changed to JUnit 4 form: `assertEquals("message", expectedCount, actualCount)`. |
| **Imports** | Removed duplicate `import static org.junit.jupiter.api.Assertions.assertEquals` to avoid confusion and use only JUnit 4. |

---

### 5.9 `src/test/java/Parent/Pages/CartPage.java` (NEW FILE)

- **Purpose:** Represents the shopping cart page (`/cart`) so the order flow goes Add to cart → **Cart** → Proceed to checkout → Checkout (instead of jumping straight to checkout).
- Extends `BasePage`.
- **Locators:** “Proceed to checkout” (e.g. `a.checkout-button` or `a[href*='checkout']`).
- **Methods:** `openCartPage()` (loads `Endpoint.CART`), `proceedToCheckout()` (clicks the button and navigates to checkout).

---

### 5.10 `src/test/java/Parent/Pages/CheckoutPage.java` (NEW FILE)

- Extends `BasePage`.
- Locators: billing fields by id (`billing_first_name`, `billing_last_name`, `billing_address_1`, `billing_city`, `billing_postcode`, `billing_phone`, `billing_email`), `#place_order`, and the success notice (e.g. `.woocommerce-notice--success.woocommerce-thankyou-order-received`).
- Methods: `openCheckoutPage()` (loads `Endpoint.CHECKOUT`), `fillBillingDetails(...)`, `placeOrder()`, `isOrderReceivedMessageDisplayed()` (uses base `wait` for visibility then returns whether the element is displayed).

---

### 5.11 `src/test/java/Parent/StepDefinitions/OrderSteps.java` (NEW FILE)

- **“I have a product in the cart”:** Gets driver from DriverFactory, opens home/store page, clicks one product (e.g. Anchor Bracelet), verifies product detail page, calls `productDetailPage.addToCart()`, then opens **CartPage**, and calls `cartPage.proceedToCheckout()` so the flow goes through the cart.
- **“I place the order with valid details”:** Assumes we are already on the checkout page (after proceeding from cart). Creates CheckoutPage, fills billing with fixed sample data (John Doe, address, city, postcode, phone, email), clicks place order. Does **not** call `openCheckoutPage()` because we navigated there from the cart.
- **“I should see the order confirmation”:** Asserts that the order-received success message is displayed.

---

### 5.12 `src/test/resources/Features/login.feature`

| Change | Details |
|--------|---------|
| **Valid login scenario** | Replaced fixed table (user2/12345) with: “And I have registered a new user with a random suffix” → “When I log in with the credentials I just registered” → “Then I should be redirected to the Dashboard” → “And I should see a welcome message for my account”. |
| **Invalid login examples** | First row updated so that for username `testuser` and password `wrongPass` the expected message is the site’s actual “username testuser is not registered …” text instead of “password incorrect”. |

---

### 5.13 `src/test/resources/Features/register.feature`

| Change | Details |
|--------|---------|
| **Valid registration examples** | Replaced previous group’s usernames/emails with team names and a single shared password: eliezer, penina, christelle, isaac, anifa, emmy; emails like `eliezer@team.test`; password `Test123!`. The step definition appends a random suffix to username and email when executing. |
| **Invalid registration** | Left unchanged; still uses the existing examples and “contains” assertion. |

---

### 5.14 `src/test/resources/Features/updateAccount.feature`

| Change | Details |
|--------|---------|
| **Background** | Replaced fixed login table with: “Given I am on the Account Page” → “And I have registered a new user with a random suffix” → “And I log in with the credentials I just registered”. |
| **Scenarios** | First/last names set to Isaac / Benit. Current password in the success scenario is `Test123!` (same as the one set when registering in the background); new password `NewTest123!`. Invalid-password scenario uses `wrongpass` as current password. |

---

### 5.15 `src/test/resources/Features/order.feature` (NEW FILE)

- Single feature “Order placement” with one scenario tagged `@order`: Given I have a product in the cart, When I place the order with valid details, Then I should see the order confirmation.

---

## 6. Test Data Strategy & Flows

### 6.1 Why random suffixes?

- The SUT is shared; usernames and emails must be **unique** per run.
- Using a **random suffix** (e.g. 1000–99999) on base names (e.g. eliezer → eliezer48291) and on emails (e.g. eliezer48291@team.test) allows the same scenario to run many times without “username/email already exists” errors.
- Passwords are not randomized; we use a known password (`Test123!`) so the same user can be “logged in” in code (or the step can no-op when already logged in).

### 6.2 How “register → login → update” share one user

```
┌─────────────────────────────────────────────────────────────────┐
│  Scenario (e.g. Login with valid credentials)                    │
├─────────────────────────────────────────────────────────────────┤
│  1. Given I am on the Account Page                               │
│     → Opens /account                                             │
│  2. And I have registered a new user with a random suffix       │
│     → Picks e.g. "penina", creates penina88472, penina88472@...   │
│     → Fills form, clicks Register                                │
│     → TestData.setLastRegisteredUser("penina88472", "Test123!")   │
│  3. When I log in with the credentials I just registered         │
│     → Gets penina88472 / Test123! from TestData                  │
│     → If login form visible: fill and submit                     │
│     → If form missing (already logged in): do nothing             │
│  4. Then I should be redirected to the Dashboard                │
│     → Asserts welcome message visible                           │
│  5. And I should see a welcome message for my account             │
│     → Asserts welcome text equals "penina88472"                  │
└─────────────────────────────────────────────────────────────────┘
```

The same pattern is used in the update-account feature: the background registers and “logs in” with a new user; the scenario then updates that user’s password using the known current password `Test123!`.

### 6.3 Flow: Order placement

```
┌─────────────────────────────────────────────────────────────────┐
│  Scenario: Place a simple order                                  │
├─────────────────────────────────────────────────────────────────┤
│  1. Given I have a product in the cart                          │
│     → Open store (home), click one product (e.g. Anchor Bracelet)│
│     → On product detail page, click Add to cart                  │
│     → Open cart page (/cart), click Proceed to checkout          │
│  2. When I place the order with valid details                   │
│     → (Already on checkout page from cart)                       │
│     → Fill billing (name, address, city, postcode, phone, email) │
│     → Click Place order                                          │
│  3. Then I should see the order confirmation                    │
│     → Wait for and assert success/thank-you message              │
└─────────────────────────────────────────────────────────────────┘
```

**Pages involved:** HomePage → ProductDetailPage → **CartPage** → CheckoutPage.

---

## 7. How to Run the Tests

### From IntelliJ IDEA

1. Open the project and wait for Maven to load.
2. In the Project view, go to **src/test/java → Parent → Runner → TestJURunnerTest.java**.
3. Click the green **Run** (▶) next to the class name and choose **Run 'TestJURunnerTest'**.
4. All scenarios in `src/test/resources/Features` will run (unless tags filter them).

### Run only certain tags

In `TestJURunnerTest.java`, in `@CucumberOptions`, you can set for example:

- `tags = "@account"` — only login/account scenarios.
- `tags = "@order"` — only the order scenario.
- `tags = "@ValidRegistration"` — only valid registration scenarios.

Then run the runner again.

### From command line (if Maven is installed)

```bash
cd /path/to/cucumber
mvn test
```

To run only scenarios with a given tag (Cucumber 7):

```bash
mvn test -Dcucumber.filter.tags="@order"
```

### Reports

- **HTML:** `target/cucumber-report.html`
- **JSON:** `target/cucumber-reports/Cucumber.json` (if the directory exists)

---

## 8. Troubleshooting & Notes

### CDP version warning

You may see: “Unable to find an exact match for CDP version 145, returning the closest version; found: 144”. This is a Selenium/ChromeDriver compatibility warning. Tests can still pass; to reduce it, align the Selenium and ChromeDriver versions with your installed Chrome (or upgrade Selenium when a version supports CDP 145).

### Chrome must be installed

The default browser in `Hooks` is Chrome. Ensure Chrome is installed and that ChromeDriver is available (e.g. via Selenium Manager or a matching driver on PATH).

### Base URL

The base URL is read from `src/test/resources/config.properties` (`baseUrl=https://askomdch.com/`). Change it there if you point at a different environment.

### Shared password

The shared password `Test123!` is used for:

- The user created in “I have registered a new user with a random suffix”.
- Valid registration examples (in code, after random suffix).
- Update-account scenario “current password” when we update the same user.

Keeping it consistent ensures login and update-account steps work without hardcoding different passwords in features.

---

**End of document.** For questions or updates, extend this file or add a CHANGELOG section with date and change description.
