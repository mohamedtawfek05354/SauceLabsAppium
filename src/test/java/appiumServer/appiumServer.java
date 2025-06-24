package appiumServer;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static java.lang.invoke.MethodHandles.lookup;

public class appiumServer {
    public static final Logger log = LogManager.getLogger(lookup().lookupClass());
    private static AppiumDriverLocalService service;

    public appiumServer() {
        try {
            // 1. Set the path to your main.js (Appium 2.x)
            String nodePath = "C:\\Program Files\\nodejs\\node.exe";
            String appiumMainPath = "C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

            // 2. Configure the service builder
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File(appiumMainPath))
                    .usingDriverExecutable(new File(nodePath))
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "warn")
                    .build();

            // 3. Start the service
            service.start();

            // 4. Verify server is running
            if (!service.isRunning()) {
                throw new RuntimeException("Appium server failed to start");
            }

            log.info("✅ Appium server started at: {}", service.getUrl());
        } catch (Exception e) {
            log.error("❌ Appium server startup failed", e);
            throw new RuntimeException("Appium server initialization failed", e);
        }
    }

    public String getCurrentServerUrl() {
        return service.getUrl().toString();
    }

    public void closeAppiumServer() {
        try {
            if (service != null && service.isRunning()) {
                service.stop();
                log.info("🛑 Appium service stopped");
            }
        } catch (Exception e) {
            log.error("❌ Error stopping Appium server", e);
        }
    }

    // Add a robust shutdown hook to ensure clean server termination
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (service != null) {
                    if (service.isRunning()) {
                        log.info("🛑 Shutting down Appium server...");
                        service.stop();
                        log.info("✅ Appium server stopped successfully");
                    } else {
                        log.info("ℹ️ Appium server was not running");
                    }
                } else {
                    log.warn("⚠️ Appium service reference was null");
                }
            } catch (Exception e) {
                log.error("❌ Error while shutting down Appium server", e);
                // Add any additional cleanup if needed
            } finally {
                // Ensure any other resources are cleaned up
                service = null;
            }
        }));
    }
}