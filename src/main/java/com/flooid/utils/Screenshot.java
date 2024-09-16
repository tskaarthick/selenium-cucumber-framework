//package com.flooid.utils;
//
//import com.flooid.engine.Configuration;
//
//import org.openqa.selenium.OutputType;
//import org.slf4j.LoggerFactory;
//import io.cucumber.java.Scenario;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.slf4j.Logger;
//import org.apache.commons.io.FileUtils;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class Screenshot {
//
//        private static final Logger log = LoggerFactory.getLogger(Screenshot.class);
//        private static final String PATH = Configuration.getInstance().getProperty("screenshots.path", "target/screenshots");
//
//        public static void captureAndSaveScreenshot(WebDriver driver, Scenario scenario, String filename) {
//            try {
//                // Ensure the target directory exists
//                Path screenshotDirectory = Paths.get(PATH);
//                Files.createDirectories(screenshotDirectory);
//
//                // Take a screenshot of the entire page
//                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//
//                // Save the screenshot in the target directory
//                File screenshotFile = new File(screenshotDirectory.toFile(), filename);
//                Files.copy(screenshot.toPath(), screenshotFile.toPath());
//                if (scenario != null) {
//                    scenario.attach(FileUtils.readFileToByteArray(screenshotFile), "image/png", String.format("%s - %s", filename, scenario.getName()));
//                }
//                log.info("Screenshot saved to {}", screenshotFile.getAbsolutePath());
//            } catch (
//                    IOException e) {
//                log.error("Could not save screen shot", e);
//            }
//        }
//    }

package com.flooid.utils;

import com.flooid.engine.Configuration;
import org.openqa.selenium.OutputType;
import org.slf4j.LoggerFactory;
import io.cucumber.java.Scenario;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing screenshots in Selenium WebDriver tests.
 */
public class Screenshot {

    private static final Logger log = LoggerFactory.getLogger(Screenshot.class);
    private static final String PATH = Configuration.getInstance().getProperty("screenshots.path", "target/screenshots");

    /**
     * Captures a screenshot and saves it to the specified directory with an optional filename.
     * Attaches the screenshot to the Cucumber scenario report if available.
     *
     * @param driver   The WebDriver instance used to take the screenshot.
     * @param scenario The Cucumber scenario in which the screenshot is being taken.
     * @param filename The filename for the screenshot.
     */
    public static void captureAndSaveScreenshot(WebDriver driver, Scenario scenario, String filename) {
        try {
            // Ensure the target directory exists
            Path screenshotDirectory = Paths.get(PATH);
            Files.createDirectories(screenshotDirectory);

            // Add timestamp to filename to ensure uniqueness
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotFilename = filename + "_" + timestamp + ".png";

            // Take a screenshot of the entire page
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save the screenshot in the target directory
            File screenshotFile = new File(screenshotDirectory.toFile(), screenshotFilename);
            Files.copy(screenshot.toPath(), screenshotFile.toPath());

            // Attach the screenshot to the Cucumber report if a scenario is provided
            if (scenario != null) {
                scenario.attach(FileUtils.readFileToByteArray(screenshotFile), "image/png", String.format("%s - %s", screenshotFilename, scenario.getName()));
                log.info("Screenshot attached to scenario: {}", scenario.getName());
            }

            log.info("Screenshot saved to {}", screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Could not save screenshot", e);

            if (scenario != null) {
                scenario.log("Screenshot could not be saved due to an error.");
            }
        }
    }
}
