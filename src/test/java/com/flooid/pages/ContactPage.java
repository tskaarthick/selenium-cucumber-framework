//package com.flooid.pages;
//
//import com.flooid.engine.Configuration;
//import com.flooid.utils.PageOperation;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//
//public class ContactPage {
//    private final WebDriver driver;
//    private final PageOperation pageOperation;
//    private static final Configuration configuration = Configuration.getInstance();
//    private final String URL = configuration.getProperty("application.contact.page.url");
//
//    //    page objects
//    private final By emailAddressTextField = By.name("yourEmail");
//    private final By emailAddressErrorMessageLabel = By.xpath("//div[contains(@class,'email-container')]" +
//            "//div[@role='alert']//div");
//    private final By validEmailAddressErrorMessageLabel = By.xpath("//div[contains(@class,'email-container')]" +
//            "//div[@role='alert']//div[text()='Please enter a valid email address!']");
//
//    public ContactPage(WebDriver driver) {
//        this.driver = driver;
//        pageOperation = new PageOperation(driver);
//        if (!isLoaded()) {
//            throw new IllegalStateException("It's is not contact page. The expected page url is " + URL +
//                    "but the current page url is " + driver.getCurrentUrl());
//        }
//    }
//
//    //    actions
//    private boolean isLoaded() {
//        return pageOperation.currentUrlToBe(URL);
//    }
//
//    public ContactPage enterEmailAddress(String emailAddress) {
//        if (emailAddress == null) {
//            emailAddress = "";
//        }
//        pageOperation.type(emailAddressTextField, emailAddress);
//        pageOperation.type(emailAddressTextField, Keys.TAB);
//        return this;
//    }
//
//    public String readErrorMessageDisplayedForInvalidEmailAddress() {
//        return pageOperation.getText(emailAddressErrorMessageLabel);
//    }
//
//    public boolean isErrorMessageNotDisplayedForInvalidEmailAddress(String errorMessage) {
//        return pageOperation.isNotDisplayed(validEmailAddressErrorMessageLabel);
//    }
//}

package com.flooid.pages;

import com.flooid.engine.Configuration;
import com.flooid.utils.PageOperation;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ContactPage {

    private final WebDriver driver;
    private final PageOperation pageOperation;
    private static final Configuration configuration = Configuration.getInstance();
    private final String URL = configuration.getProperty("application.contact.page.url");
    private static final Logger logger = LoggerFactory.getLogger(ContactPage.class);

    // Page objects
    private final By emailAddressTextField = By.name("yourEmail");
    private final By emailAddressErrorMessageLabel = By.xpath("//div[contains(@class,'email-container')]" +
            "//div[@role='alert']//div");
    private final By validEmailAddressErrorMessageLabel = By.xpath("//div[contains(@class,'email-container')]" +
            "//div[@role='alert']//div[text()='Please enter a valid email address!']");

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        pageOperation = new PageOperation(driver);
        if (!isLoaded()) {
            throw new IllegalStateException("This is not the Contact page. Expected URL: " + URL +
                    " but found: " + driver.getCurrentUrl());
        }
    }

    // Page loading check
    private boolean isLoaded() {
        return pageOperation.currentUrlToBe(URL);
    }

    // Actions

    /**
     * Enters an email address into the email address text field.
     *
     * @param emailAddress The email address to enter.
     * @return The current instance of ContactPage for method chaining.
     */
    public ContactPage enterEmailAddress(String emailAddress) {
        emailAddress = Objects.requireNonNullElse(emailAddress, "");
        logger.info("Entering email address: {}", emailAddress);
        pageOperation.type(emailAddressTextField, emailAddress);
        pageOperation.type(emailAddressTextField, Keys.TAB); // Simulate leaving the field
        return this;
    }

    /**
     * Reads the error message displayed for an invalid email address.
     *
     * @return The error message displayed.
     */
    public String readErrorMessageDisplayedForInvalidEmailAddress() {
        logger.info("Reading error message for invalid email address");
        return pageOperation.getText(emailAddressErrorMessageLabel);
    }

    /**
     * Checks if the error message for an invalid email address is not displayed.
     *
     * @return True if the error message is not displayed, false otherwise.
     */
    public boolean isErrorMessageNotDisplayed() {
        logger.info("Checking if error message for invalid email address is not displayed");
        return pageOperation.isNotDisplayed(validEmailAddressErrorMessageLabel);
    }

    /**
     * Checks if a specific error message is displayed.
     *
     * @param errorMessage The error message to check for.
     * @return True if the error message is displayed, false otherwise.
     */
    public boolean isSpecificErrorMessageDisplayed(String errorMessage) {
        logger.info("Checking for specific error message: {}", errorMessage);
        By dynamicErrorLocator = By.xpath("//div[contains(@class,'email-container')]//div[@role='alert']//div[text()='" + errorMessage + "']");
        return pageOperation.isDisplayed(dynamicErrorLocator);
    }
}
