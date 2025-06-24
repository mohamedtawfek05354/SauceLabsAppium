package org.example.ReusePage;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import io.appium.java_client.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

import static java.lang.invoke.MethodHandles.lookup;

public class ScrollingPage {
    /**
     * Scrolls to the specified element using platform-appropriate method
     * @param locator The locator of the element to scroll to
     * @param maxSwipes Maximum number of swipe attempts (default: 5)
     */
    public static void scrollToElement(By locator, int maxSwipes, AndroidDriver driver) {

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    return; // Element is already visible
                }
            } catch (Exception e) {
                // Element not found yet, continue scrolling
            }

            if (driver instanceof AndroidDriver) {
                androidScroll(driver);
            }
        }
        throw new RuntimeException("Element not found after " + maxSwipes + " swipes");
    }

    private static void androidScroll(AppiumDriver driver) {
        // Get screen dimensions
        var size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        // Create scroll action
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 0);

        scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), startX, endY));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(scroll));
    }


}
