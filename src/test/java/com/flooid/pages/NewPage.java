package com.flooid.pages;

import com.flooid.engine.Configuration;
import com.flooid.utils.PageOperation;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewPage {

    private WebDriver driver;
    private PageOperation pageOperation;
    private static final Logger logger = LoggerFactory.getLogger(NewPage.class);

    public NewPage(WebDriver driver) {
        this.driver = driver;
        this.pageOperation = new PageOperation(driver);
    }

    /**
     * Navigates to the Contact Us page.
     *
     * @return a new instance of ContactPage.
     */
    public ContactPage navigateToContactUsPage() {
        String URL = Configuration.getInstance().getProperty("application.contact.page.url");
        logger.info("Navigating to Contact Us page: {}", URL);
        driver.get(URL);
        return new ContactPage(driver);
    }
}
