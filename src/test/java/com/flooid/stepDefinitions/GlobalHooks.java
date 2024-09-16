package com.flooid.stepDefinitions;

import com.flooid.engine.TestContext;
import com.flooid.utils.Screenshot;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalHooks {
    private static final Logger logger = LoggerFactory.getLogger(GlobalHooks.class);
    private final TestContext testContext;

    public GlobalHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void before(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        testContext.setScenario(scenario);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String screenshotName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp;

                logger.error("Scenario failed: {}. Capturing screenshot...", scenario.getName());
                Screenshot.captureAndSaveScreenshot(
                        testContext.getDriver(),
                        scenario,
                        screenshotName
                );
            } else {
                logger.info("Scenario passed: {}", scenario.getName());
                // Optionally, capture screenshot on success based on config.
            }
        } finally {
            testContext.quitDriver();
            logger.info("Driver quit for scenario: {}", scenario.getName());
        }
    }
}
