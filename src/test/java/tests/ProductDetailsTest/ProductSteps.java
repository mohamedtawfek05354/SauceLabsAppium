package tests.ProductDetailsTest;

import Reuse.jsonFileManager;
import driver.MobileDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ProductDetails.ProductPage;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import Reuse.AllureLog4jListener;
import Reuse.BaseTest;
import tests.HooksHandler;
import java.io.IOException;
import static java.lang.invoke.MethodHandles.lookup;

@Listeners(AllureLog4jListener.class)
public class ProductSteps extends BaseTest {
    ProductPage productPage;
    SoftAssert softAssert;
    private final Logger log = LogManager.getLogger(lookup().lookupClass());
    jsonFileManager jsonFileManager;

    @Before
    public void setup() {
        log.info("Initializing ProductPage...");
        productPage = new ProductPage(MobileDriver.getInstance().getDriver());
        log.info("ProductPage initialized successfully.");
        jsonFileManager=new jsonFileManager("src/main/resources/infoData.json");
    }

    @Step("Verifying user is logged in and on the Products page")
    @Given("I am logged in with valid credentials")
    public void i_am_logged_in_with_valid_credentials() {
        log.info("Verifying that the user is on the Products page...");
        Allure.step("Checking if user is on the Products page");
        productPage.Assertion_page_Title_Name("PRODUCTS",MobileDriver.getInstance().getDriver());
        log.info("Successfully logged in and on the Products page.");
    }

    @Step("Clicking on product: {productName} in inventory")
    @When("I click on a product {string} on the inventory page")
    public void i_click_on_a_product_on_the_inventory_page(String productName) {
        log.info("Clicking on product: {}", productName);
        Allure.step("Clicking on product: " + productName);
        productPage.clickOnProduct(productName);
        log.info("Product clicked successfully.");
    }

    @Step("Verifying the product details page is displayed")
    @Then("I should see the product details page")
    public void i_should_see_the_product_details_page() {
        log.info("Verifying the product details page...");
        Allure.step("Checking if product details page is displayed");
        productPage.assertDetailsPage();
        log.info("Product details page verified successfully.");
    }

    @Step("Verifying product details: name, description, price, and image")
    @Then("I should see the product name, description, price, and image")
    public void i_should_see_the_product_name_description_price_and_image() {
        log.info("Validating product details (name, description, price)...");
        Allure.step("Validating product details");
        softAssert = new SoftAssert();
        String name = productPage.getProductName();
        String price = productPage.getProductPrice();

        softAssert.assertFalse(name.isEmpty(), "Product name should not be empty");
        softAssert.assertFalse(price.isEmpty(), "Product price should not be empty");

        log.debug("Product Name: {}", name);
        log.debug("Product Price: {}", price);

        Allure.step("Product Name: " + name);
        Allure.step("Product Price: " + price);

        softAssert.assertAll();
        log.info("Product details validation completed successfully.");
    }

    @Step("Opening slide menu and selecting 'All Items'")
    @Then("Click on view all items from slide menu")
    public void Click_on_view_all_items_from_slide_menu() {
        log.info("Opening slide menu...");
        Allure.step("Opening slide menu");
        productPage.clickOpenMenu();
        log.info("Clicking on 'All Items'...");
        Allure.step("Clicking on 'All Items'");
        productPage.clickAllItems();
        log.info("'All Items' clicked successfully.");
    }

//    @Step("Navigating to product details page for: {productName}")
//    @Given("I am on the product {string} details page")
//    public void i_am_on_the_product_details_page(String productName) {
//        log.info("Navigating to product details page for: {}", productName);
//        Allure.step("Navigating to product details page: " + productName);
//        productPage.clickOnProduct(productName);
//        log.info("Successfully navigated to product details page.");
//    }
//
    @Step("Clicking 'Add to Cart' button")
    @Then("Click on add to cart")
    public void click_on_add_to_cart() {
        log.info("Clicking 'Add to Cart' button...");
        Allure.step("Clicking 'Add to Cart' button");
        productPage.clickAddToCart();
        log.info("Product added to cart.");
    }

    @When("I click on the cart icon")
    public void iClickOnTheCartIcon() {
        productPage.clickOnCart();
    }

    @And("I proceed to checkout")
    public void iProceedToCheckout() {
        productPage.clickOnCheckOut();
    }

    @And("I enter {string} as first name")
    public void iEnterAsFirstName(String firstName) {
        productPage.enterFirstName((String) jsonFileManager.getValueByKey(firstName));
    }

    @And("I enter {string} as last name")
    public void iEnterAsLastName(String lastName) {
        productPage.enterLastName((String) jsonFileManager.getValueByKey(lastName));
    }

    @And("I enter {string} as zip code")
    public void iEnterAsZipCode(String zipCode) {
        productPage.enterZipCode((String) jsonFileManager.getValueByKey(zipCode));
    }

    @And("I continue to payment")
    public void iContinueToPayment() {
        productPage.clickOnContinue();
    }

    @And("I complete the purchase")
    public void iCompleteThePurchase() {
        productPage.clickOnFinish();
    }

    @Then("I should see the order confirmation message")
    public void iShouldSeeTheOrderConfirmationMessage() {
        productPage.assertThanksMessage();
    }

    @Description("Take pic for failed Scenario")
    @After
    public void failedScenarioScreen(Scenario scenario) throws IOException {
        HooksHandler.failedTestCaseScreen(scenario);
    }
}
