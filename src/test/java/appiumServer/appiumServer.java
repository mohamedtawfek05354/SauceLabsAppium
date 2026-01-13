package appiumServer;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class appiumServer {
    public static final Logger log = LogManager.getLogger(lookup().lookupClass());
    private static AppiumDriverLocalService service;

    public appiumServer() {
        try {
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withArgument(() -> "--log-level", "debug").withArgument(() -> "--allow-insecure=adb_shell").withArgument(() -> "--allow-insecure=execute_driver_script");
            builder.withIPAddress("127.0.0.1");
            builder.usingAnyFreePort();
            builder.withLogOutput(System.out);
            builder.withTimeout(Duration.ofMinutes(20));
            builder.withArgument(() -> "--allow-insecure=adb_shell");
            builder.withArgument(() -> "--allow-insecure=execute_driver_script");
            builder.withArgument(() -> "--use-plugins", "images");
            service = AppiumDriverLocalService.buildService(builder);
            service.clearOutPutStreams();
            service.start();
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
}