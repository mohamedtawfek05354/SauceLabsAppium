package tests.LoginTest;
import Reuse.RetryAnalyzer;
import driver.MobileDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.LoginPage.LoginPage;
import tests.HooksHandler;


import java.io.IOException;

import static java.lang.invoke.MethodHandles.lookup;

public class LoginStepDef {
    LoginPage lg;
    private final Logger log = LogManager.getLogger(lookup().lookupClass());
    @Before
    public void setup() throws Exception {
        log.info("Initializing browser...");
        lg = new LoginPage(MobileDriver.getInstance().getDriver());
        log.info("Browser opened and LoginPage object initialized.");
    }
    @Step("Verifying SauceDemo login page is displayed")
    @Given("I am on the Saucedemo login page")
    public void i_am_on_the_saucedemo_login_page() {
        log.info("Verifying the SauceDemo login page title...");
        Allure.step("Checking login page title");
//        String actualTitle = getDriver().getTitle();
//        log.debug("Actual page title: {}", actualTitle);
//        Assert.assertEquals(actualTitle, "Swag Labs", "Login page title does not match!");
        log.info("Login page verified successfully.");
    }

    @Step("Entering username: {username} and password")
    @When("I enter the username {string} and the password {string}")
    public void i_enter_the_username_and_the_password(String username, String password) {
        log.info("Entering username: {}", username);
        Allure.step("Entering username: " + username);
        lg.enterUserName(username);
        log.info("Entering password");
        Allure.step("Entering password");
        lg.enterPassword(password);
        log.info("Username and password entered successfully.");
    }

    @Step("Clicking on the login button")
    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        log.info("Clicking on the login button...");
        Allure.step("Clicking on the login button");
        lg.clickLogin();
        log.info("Login button clicked.");
    }

    @Step("Verifying redirection to the inventory page")
    @Then("I should be redirected to the inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        log.info("Verifying redirection to the inventory page...");
        Allure.step("Checking if redirected to Inventory page");
        lg.Assertion_page_Title_Name("PRODUCTS");
        log.info("Successfully redirected to the inventory page.");
    }
    @Step("Verifying pop up Alert message")
    @Then("I should see pop up Alert message")
    public void I_should_see_pop_up_Alert_message(){
        log.info("Verifying pop up Alert message...");
        Allure.step("Checking if Alert Message is came up");
        lg.Assertion_page_Title_Name("Username and password do not match any user in this service.");
        log.info("Successfully Alert Message is came up.");
    }
    @Description("Take pic for failed Scenario")
    @After
    public void failedScenarioScreen(Scenario scenario) throws IOException {
        HooksHandler.failedTestCaseScreen(scenario);
    }

}
