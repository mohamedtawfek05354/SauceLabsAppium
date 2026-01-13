package org.example.ProductDetails;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.bs.A;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ReusePage.BasePage;
import org.example.ReusePage.ScrollingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class ProductPage extends BasePage {
    private String productName;
    public ProductPage(AndroidDriver myBasePageDriver) {
        super(myBasePageDriver);
    }
    private final By productDetailsPage= AppiumBy.accessibilityId("test-Description");
    private final By productPriceDetails=AppiumBy.accessibilityId("test-Price");
    private final By openMenu=AppiumBy.accessibilityId("test-Menu");
    private final By allItems=AppiumBy.accessibilityId("test-ALL ITEMS");
    private final By addToCartButton = AppiumBy.xpath("//android.widget.TextView[@text=\"ADD TO CART\"]");
    private final By cartIcon=AppiumBy.accessibilityId("test-Cart");
    private final By checkOut= AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT\")");
    private final By firstName=AppiumBy.accessibilityId("test-First Name");
    private final By lastName=AppiumBy.accessibilityId("test-Last Name");
    private final By zipPostalCode=AppiumBy.accessibilityId("test-Zip/Postal Code");
    private final By continueBtn=AppiumBy.accessibilityId("test-CONTINUE");
    private final By finishBtn=AppiumBy.accessibilityId("test-FINISH");
    private final By endProcess=AppiumBy.androidUIAutomator("new UiSelector().text(\"THANK YOU FOR YOU ORDER\")");

    public void assertDetailsPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailsPage));
        BasePageDriver.findElement(productDetailsPage).isDisplayed();
    }
    public void clickOnProduct(String productName){
        findElement(productName,BasePageDriver).click();
        this.productName=productName;
    }
    public String getProductName() {
        return findElement(productName,BasePageDriver).getText();
    }

    public String getProductPrice() {
        return BasePageDriver.findElement(productPriceDetails).getText();
    }

    public void clickOpenMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(openMenu));
        BasePageDriver.findElement(openMenu).click();
    }

    public void clickAllItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(allItems));
        BasePageDriver.findElement(allItems).click();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        BasePageDriver.findElement(addToCartButton).click();
    }
    public void clickOnCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon));
        BasePageDriver.findElement(cartIcon).click();
    }

    public void clickOnCheckOut(){
        ScrollingPage.scrollToElement(checkOut,5,BasePageDriver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkOut));
        BasePageDriver.findElement(checkOut).click();
    }
    public void enterFirstName(String fName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        BasePageDriver.findElement(firstName).clear();
        BasePageDriver.findElement(firstName).sendKeys(fName);
    }
    public void enterInvalidFirstName(String fName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("First Name")));
        BasePageDriver.findElement(firstName).clear();
        BasePageDriver.findElement(firstName).sendKeys(fName);
    }
    public void enterLastName(String lName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        BasePageDriver.findElement(lastName).clear();
        BasePageDriver.findElement(lastName).sendKeys(lName);
    }
    public void enterZipCode(String code){
        wait.until(ExpectedConditions.visibilityOfElementLocated(zipPostalCode));
        BasePageDriver.findElement(zipPostalCode).clear();
        BasePageDriver.findElement(zipPostalCode).sendKeys(code);
    }
    public void clickOnContinue(){
        ScrollingPage.scrollToElement(continueBtn,5,BasePageDriver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueBtn));
        BasePageDriver.findElement(continueBtn).click();
    }
    public void clickOnFinish(){
        ScrollingPage.scrollToElement(finishBtn,5,BasePageDriver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishBtn));
        BasePageDriver.findElement(finishBtn).click();
    }
    public void assertThanksMessage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(endProcess));
        BasePageDriver.findElement(endProcess).isDisplayed();
    }

}
