package driver;

import Reuse.ConfigLoader;
import appiumServer.appiumServer;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.invoke.MethodHandles.lookup;

public class MobileDriver {
    public static final Logger log = LogManager.getLogger(lookup().lookupClass());
    private static AndroidDriver driver;
    private ConfigLoader configLoader;
    private static UiAutomator2Options options;
    private static appiumServer server;
    private static MobileDriver mobileDriver=null;

    private MobileDriver(){
        setAndroidDriver();
    }
    public static MobileDriver getInstance(){
        if (mobileDriver==null)
            mobileDriver=new MobileDriver();
        return mobileDriver;
    }
    public static void setDriver(){
        driver=null;
    }
    private void setAndroidDriver() {
        try {
            loadProperties();
            if (server == null) {
                server = new appiumServer();
            }
            setUiAutomator2Options();
            setAppPackageAndActivity();

            log.info("Connecting to Appium server at: {}", server.getCurrentServerUrl());

            log.info("Driver options: {}", options.asMap());

            driver = new AndroidDriver(new URL(server.getCurrentServerUrl()), options);

            if (driver == null) {
                throw new RuntimeException("Failed to create AndroidDriver instance");
            }

            log.info("✅ Android driver initialized successfully");
        } catch (Exception e) {
            log.error("❌ Driver initialization failed", e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }
    public static void initializeDriver() {
        getInstance(); // This will call setAndroidDriver()
    }
    private void setUiAutomator2Options() {
        options = new UiAutomator2Options();
        options.setPlatformName(configLoader.getProperty("platformName"));
        options.setDeviceName(configLoader.getProperty("deviceName"));
        options.setAutomationName(configLoader.getProperty("automationName"));
        options.setApp(configLoader.getProperty("appPath"));
        log.info("Platform: {}, Device: {}, App: {}",
                configLoader.getProperty("platformName"),
                configLoader.getProperty("deviceName"),
                configLoader.getProperty("appPath"));

        log.info("✅ Android driver initialized.");
    }
    private static void setAppPackageAndActivity(){
        options.setAppPackage("com.swaglabsmobileapp")
                .setAppActivity("com.swaglabsmobileapp.MainActivity")
                .setAppWaitPackage("com.swaglabsmobileapp")
                .setAppWaitActivity("com.swaglabsmobileapp.MainActivity");;
    }
    private void loadProperties() {
        configLoader = new ConfigLoader("src/main/resources/config.properties");
    }
    public static AndroidDriver getDriver(){
        return driver;
    }
    public static void closeDriver(){
        getDriver().quit();
    }
}
