package com.flooid.engine;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContext {

    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private Scenario scenario;

    public WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            try {
                logger.info("Initializing WebDriver for scenario: {}", scenario.getName());
                driverThreadLocal.set(getDriverFromFactory());
            } catch (Exception e) {
                logger.error("Error initializing WebDriver for scenario: {}", scenario.getName(), e);
                throw new RuntimeException("Failed to initialize WebDriver", e);
            }
        }
        return driverThreadLocal.get();
    }

    private WebDriver getDriverFromFactory() {
        return WebDriverFactory.getInstance().getDriver(scenario.getName());
    }

    public void quitDriver() {
        if (driverThreadLocal.get() != null) {
            try {
                logger.info("Quitting WebDriver for scenario: {}", scenario.getName());
                driverThreadLocal.get().quit();
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        logger.info("Scenario set: {}", scenario.getName());
    }
}
