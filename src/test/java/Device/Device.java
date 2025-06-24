package Device;

import Reuse.ConfigLoader;
import driver.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class Device {
    public SoftAssert softAssert=new SoftAssert();
    public ConfigLoader configLoader=new ConfigLoader("src/main/resources/config.properties");
    public Device(){
    }
    public void clickBack(){
        try {
            Runtime.getRuntime().exec("adb shell input keyevent 4");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void activateApp() {
        AndroidDriver driver = MobileDriver.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized! Call MobileDriver.initializeDriver() first");
        }
        driver.activateApp(configLoader.getProperty("appPackage"));
    }

    // Similar checks for other methods
    public void installApp() {
        AndroidDriver driver = MobileDriver.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized!");
        }
        driver.installApp(configLoader.getProperty("appPath"));
    }
    public static void closeApp(){
        AndroidDriver driver = MobileDriver.getDriver();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized!");
        }

        try {
            // Proper way to close Appium session
            driver.quit();  // Always use quit() instead of close()
        } catch (Exception e) {
            System.err.println("Error while closing app: " + e.getMessage());
            throw new RuntimeException("Failed to close application", e);
        } finally {
            // Ensure driver reference is cleared
            MobileDriver.setDriver();
        }
    }

}
