package com.flooid.engine;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverFactory {

    private static final WebDriverFactory instance = new WebDriverFactory();
    private final Configuration configuration = Configuration.getInstance();
    private final String seleniumUrl = configuration.getProperty("webdriver.remote_grid.url");
    private final String browserName = configuration.getProperty("browser").toLowerCase();
    private final String executionMode = configuration.getProperty("execution").toLowerCase();
    private final boolean isHeadless = Boolean.parseBoolean(configuration.getProperty("browser.headless", "false"));

    public static WebDriverFactory getInstance() {
        return instance;
    }

    public WebDriver getDriver(String testName) {
        return createDriver(testName);
    }

    private WebDriver createDriver(String testName) {
        WebDriver driver = null;

        switch (executionMode) {
            case "local":
                driver = createLocalDriver();
                break;
            case "remote":
                driver = createRemoteDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid execution mode: " + executionMode);
        }

        configureDriver(driver);
        return driver;
    }

    private WebDriver createLocalDriver() {
        switch (browserName) {
            case "chrome":
                return createLocalChromeDriver();
            case "firefox":
                return createLocalFirefoxDriver();
            case "edge":
                return createLocalEdgeDriver();
            case "safari":
                return createLocalSafariDriver();
            default:
                throw new IllegalArgumentException("Invalid browser type: " + browserName);
        }
    }

    private WebDriver createRemoteDriver() {
        switch (browserName) {
            case "chrome":
                return createRemoteChromeDriver();
            case "firefox":
                return createRemoteFirefoxDriver();
            case "edge":
                return createRemoteEdgeDriver();
            case "safari":
                return createRemoteSafariDriver();
            default:
                throw new IllegalArgumentException("Invalid browser type: " + browserName);
        }
    }

    private WebDriver createLocalChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return new ChromeDriver(options);
    }

    private WebDriver createLocalFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return new FirefoxDriver(options);
    }

    private WebDriver createLocalEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return new EdgeDriver(options);
    }

    private WebDriver createLocalSafariDriver() {
        SafariOptions options = new SafariOptions();
        return new SafariDriver(options);
    }

    private WebDriver createRemoteChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return createRemoteWebDriver(options);
    }

    private WebDriver createRemoteFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return createRemoteWebDriver(options);
    }

    private WebDriver createRemoteEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return createRemoteWebDriver(options);
    }

    private WebDriver createRemoteSafariDriver() {
        SafariOptions options = new SafariOptions();
        return createRemoteWebDriver(options);
    }

    private WebDriver createRemoteWebDriver(Capabilities browserOptions) {
        try {
            return new RemoteWebDriver(new URL(seleniumUrl), browserOptions);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Selenium Grid URL: " + seleniumUrl, e);
        }
    }

    private void configureDriver(WebDriver driver) {
        long timeout = Long.parseLong(configuration.getProperty("webdriver.timeout", "30"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }
}
