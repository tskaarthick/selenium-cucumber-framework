//package com.flooid.stepDefinitions;
//
//import com.flooid.engine.TestContext;
//import com.flooid.pages.ContactPage;
//import com.flooid.pages.NewPage;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class ContactFormSteps {
//
//    private final TestContext testContext;
//    private static final Logger logger = LoggerFactory.getLogger(ContactFormSteps.class);
//
//    public ContactFormSteps(TestContext testContext) {
//        this.testContext = testContext;
//    }
//
//    @Given("I am on the Flooid contact page")
//    public void iAmOnTheFlooidContactPage() {
//        logger.info("Navigating to the Flooid contact page.");
//        new NewPage(testContext.getDriver()).navigateToContactUsPage();
//        logger.info("Successfully navigated to the Flooid contact page.");
//    }
//
//    @When("I enter an invalid email address {string}")
//    public void iEnterAnInvalidEmailAddress(String invalidEmail) {
//        logger.info("Entering invalid email address: {}", invalidEmail);
//        enterEmailAddress(invalidEmail);
//    }
//
//    private void enterEmailAddress(String emailAddress) {
//        logger.debug("Attempting to enter email address: {}", emailAddress);
//        new ContactPage(testContext.getDriver()).enterEmailAddress(emailAddress);
//        logger.debug("Email address entered: {}", emailAddress);
//    }
//
//    @When("I enter a valid email address {string}")
//    public void iEnterAValidEmailAddress(String validEmail) {
//        logger.info("Entering valid email address: {}", validEmail);
//        enterEmailAddress(validEmail);
//    }
//
//    @Then("I should see an error message {string}")
//    public void iShouldSeeAnErrorMessage(String expectedErrorMessage) {
//        logger.info("Checking for expected error message: {}", expectedErrorMessage);
//        String actualErrorMessage = new ContactPage(testContext.getDriver()).readErrorMessageDisplayedForInvalidEmailAddress();
//        assertEquals("The expected Error message: " + expectedErrorMessage + " is not displayed", expectedErrorMessage, actualErrorMessage);
//        logger.info("Error message validation passed: {}", expectedErrorMessage);
//    }
//
//    @Then("I should not see an error message {string}")
//    public void iShouldNotSeeAnErrorMessage(String errorMessage) {
//        logger.info("Checking that error message is not displayed: {}", errorMessage);
//        boolean isErrorMessageNotDisplayed = new ContactPage(testContext.getDriver()).isErrorMessageNotDisplayedForInvalidEmailAddress(errorMessage);
//        assertTrue("The error message: " + errorMessage + " was unexpectedly displayed.", isErrorMessageNotDisplayed);
//        logger.info("Error message is not displayed as expected.");
//    }
//
//    @When("I leave the email address field empty")
//    public void iLeaveTheEmailAddressFieldEmpty() {
//        logger.info("Leaving the email address field empty.");
//        enterEmailAddress("");
//        logger.info("Email address field left empty.");
//    }
//}

package com.flooid.stepDefinitions;

import com.flooid.engine.TestContext;
import com.flooid.pages.ContactPage;
import com.flooid.pages.NewPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactFormSteps {

    private final TestContext testContext;
    private static final Logger logger = LoggerFactory.getLogger(ContactFormSteps.class);

    public ContactFormSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I am on the Flooid contact page")
    public void iAmOnTheFlooidContactPage() {
        logger.info("Navigating to the Flooid contact page.");
        new NewPage(testContext.getDriver()).navigateToContactUsPage();
        logger.info("Successfully navigated to the Flooid contact page.");
    }

    @When("I enter an invalid email address {string}")
    public void iEnterAnInvalidEmailAddress(String invalidEmail) {
        logger.info("Entering invalid email address: {}", invalidEmail);
        enterEmailAddress(invalidEmail);
    }

    private void enterEmailAddress(String emailAddress) {
        logger.debug("Attempting to enter email address: {}", emailAddress);
        new ContactPage(testContext.getDriver()).enterEmailAddress(emailAddress);
        logger.debug("Email address entered: {}", emailAddress);
    }

    @When("I enter a valid email address {string}")
    public void iEnterAValidEmailAddress(String validEmail) {
        logger.info("Entering valid email address: {}", validEmail);
        enterEmailAddress(validEmail);
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedErrorMessage) {
        logger.info("Checking for expected error message: {}", expectedErrorMessage);
        String actualErrorMessage = new ContactPage(testContext.getDriver()).readErrorMessageDisplayedForInvalidEmailAddress();
        assertEquals("The expected Error message: " + expectedErrorMessage + " is not displayed", expectedErrorMessage, actualErrorMessage);
        logger.info("Error message validation passed: {}", expectedErrorMessage);
    }

    @Then("I should not see an error message {string}")
    public void iShouldNotSeeAnErrorMessage(String errorMessage) {
        logger.info("Checking that error message is not displayed: {}", errorMessage);

        // Use the method `isNotDisplayed` to ensure the error message is not present
        boolean isErrorMessageNotDisplayed = new ContactPage(testContext.getDriver()).isErrorMessageNotDisplayed();

        assertTrue("The error message: " + errorMessage + " was unexpectedly displayed.", isErrorMessageNotDisplayed);
        logger.info("Error message is not displayed as expected.");
    }


    @When("I leave the email address field empty")
    public void iLeaveTheEmailAddressFieldEmpty() {
        logger.info("Leaving the email address field empty.");
        enterEmailAddress("");
        logger.info("Email address field left empty.");
    }
}
