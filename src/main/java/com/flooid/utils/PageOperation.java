package com.flooid.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * PageOperation class provides various methods to perform actions on web elements using Selenium WebDriver.
 * Refactored to fail on action errors and provide detailed logging.
 */
public class PageOperation {

    private final WebDriver driver;
    private final int DEFAULT_WAIT_SECONDS = 10;
    private static final Logger logger = LoggerFactory.getLogger(PageOperation.class);

    /**
     * Constructor for the PageOperation class.
     *
     * @param driver The WebDriver instance to interact with web elements.
     */
    public PageOperation(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Waits for a certain condition for a specified number of seconds.
     *
     * @param seconds The maximum number of seconds to wait.
     * @return A FluentWait object configured with the timeout and polling interval.
     */
    public FluentWait<WebDriver> waitFor(int seconds) {
        int explicitWaitSeconds = seconds != 0 ? seconds : DEFAULT_WAIT_SECONDS;
        int pollingSeconds = 1;
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(explicitWaitSeconds))
                .pollingEvery(Duration.ofSeconds(pollingSeconds))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    /**
     * Waits for a default period of time for a certain condition.
     *
     * @return A FluentWait object configured with the default timeout and polling interval.
     */
    public FluentWait<WebDriver> waitFor() {
        return waitFor(DEFAULT_WAIT_SECONDS);
    }

    /**
     * Retrieves the text of a web element.
     *
     * @param locator The locator of the web element to retrieve the text from.
     * @return The text content of the web element.
     */
    public String getText(By locator) {
        return getText(locator, DEFAULT_WAIT_SECONDS);
    }

    /**
     * Retrieves the text of a web element with a custom timeout.
     *
     * @param locator The locator of the web element to retrieve the text from.
     * @param seconds The number of seconds to wait for the element to be visible.
     * @return The text content of the web element.
     */
    public String getText(By locator, int seconds) {
        try {
            waitFor(seconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            return driver.findElement(locator).getText();
        } catch (Exception e) {
            logger.error("Error getting text from the element: {}", locator, e);
            throw new PageOperationException("Error getting text from the element: " + locator, e);
        }
    }

    /**
     * Clicks on the element located by the provided locator.
     *
     * @param locator The locator of the element to click.
     */
    public void click(By locator) {
        try {
            logger.info("Attempting to click on the element: {}", locator);
            WebElement element = waitFor().until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Successfully clicked on the element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click on the element: {}", locator, e);
            throw new RuntimeException("Error clicking on the element: " + locator, e);
        }
    }

    /**
     * Types text into a web element.
     *
     * @param by   The locator of the web element.
     * @param text The text to type into the web element.
     * @return The current instance of PageOperation for method chaining.
     */
    public PageOperation type(By by, String text) {
        return type(by, text, DEFAULT_WAIT_SECONDS);
    }

    /**
     * Types text into a web element with a custom timeout.
     *
     * @param by      The locator of the web element.
     * @param text    The text to type into the web element.
     * @param seconds The number of seconds to wait for the element to be clickable.
     * @return The current instance of PageOperation for method chaining.
     */
    public PageOperation type(By by, String text, int seconds) {
        try {
            waitFor(seconds).until(ExpectedConditions.elementToBeClickable(by));
            WebElement element = driver.findElement(by);
            element.clear();  // Clear the field before typing
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Error typing into the element: {}", by, e);
            throw new PageOperationException("Error typing into the element: " + by, e);
        }
        return this;
    }

    /**
     * Sends keys to a web element.
     *
     * @param by   The locator of the web element.
     * @param keys The keys to send to the web element.
     * @return The current instance of PageOperation for method chaining.
     */
    public PageOperation type(By by, Keys keys) {
        return type(by, keys, DEFAULT_WAIT_SECONDS);
    }

    /**
     * Sends keys to a web element with a custom timeout.
     *
     * @param by      The locator of the web element.
     * @param keys    The keys to send to the web element.
     * @param seconds The number of seconds to wait for the element to be clickable.
     * @return The current instance of PageOperation for method chaining.
     */
    public PageOperation type(By by, Keys keys, int seconds) {
        try {
            waitFor(seconds).until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).sendKeys(keys);
        } catch (Exception e) {
            logger.error("Error sending keys to the element: {}", by, e);
            throw new PageOperationException("Error sending keys to the element: " + by, e);
        }
        return this;
    }

    /**
     * Waits for the current URL to match the given URL.
     *
     * @param url The expected URL.
     * @return True if the current URL matches the expected URL, false otherwise.
     */
    public boolean currentUrlToBe(String url) {
        return currentUrlToBe(url, DEFAULT_WAIT_SECONDS);
    }

    /**
     * Waits for the current URL to match the given URL with a custom timeout.
     *
     * @param url     The expected URL.
     * @param seconds The number of seconds to wait for the URL to match.
     * @return True if the current URL matches the expected URL, false otherwise.
     */
    public boolean currentUrlToBe(String url, int seconds) {
        try {
            waitFor(seconds).until(ExpectedConditions.urlToBe(url));
            return true;
        } catch (Exception e) {
            logger.error("Error waiting for the URL to be: {}", url, e);
            throw new PageOperationException("Error waiting for the URL to be: " + url, e);
        }
    }

    /**
     * Checks if the specified web element is displayed.
     *
     * @param by The locator of the web element to check.
     * @return True if the element is displayed, false otherwise.
     */
    public boolean isDisplayed(By by) {
        return isDisplayed(by, DEFAULT_WAIT_SECONDS);
    }

    /**
     * Checks if the specified web element is displayed with a custom timeout.
     *
     * @param by      The locator of the web element to check.
     * @param seconds The number of seconds to wait for the element to be visible.
     * @return True if the element is displayed, false otherwise.
     */
    public boolean isDisplayed(By by, int seconds) {
        try {
            waitFor(seconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking if the element is displayed: {}", by, e);
            throw new PageOperationException("Error checking if the element is displayed: " + by, e);
        }
    }

    /**
     * Checks if the specified web element is not displayed.
     *
     * @param by The locator of the web element to check.
     * @return True if the element is not displayed, false otherwise.
     */
    public boolean isNotDisplayed(By by) {
        return isNotDisplayed(by, DEFAULT_WAIT_SECONDS);
    }

    /**
     * Checks if the specified web element is not displayed with a custom timeout.
     *
     * @param by      The locator of the web element to check.
     * @param seconds The number of seconds to wait for the element to be invisible.
     * @return True if the element is not displayed, false otherwise.
     */
    public boolean isNotDisplayed(By by, int seconds) {
        try {
            return waitFor(seconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            logger.error("Error checking if the element is not displayed: {}", by, e);
            throw new PageOperationException("Error checking if the element is not displayed: " + by, e);
        }
    }

    /**
     * Scrolls to the specified element.
     *
     * @param by The locator of the web element to scroll to.
     */
    public void scrollToElement(By by) {
        try {
            WebElement element = driver.findElement(by);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logger.error("Error scrolling to element: {}", by, e);
            throw new PageOperationException("Error scrolling to element: " + by, e);
        }
    }

    /**
     * Custom exception for handling page operation errors.
     */
    public static class PageOperationException extends RuntimeException {
        public PageOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
