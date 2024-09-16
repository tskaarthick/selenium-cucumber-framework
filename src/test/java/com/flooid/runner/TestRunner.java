package com.flooid.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com/flooid/stepDefinitions"},
        plugin = {"json:target/cucumber-reports/cucumber-json-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber-junit-reports/cucumberJunit.xml",
                "html:target/cucumber-reports/cucumber-html-report.html"
        },
        dryRun = false,
        monochrome = true,
        tags = "@flooidFT and not @wip"
)

public class TestRunner {  // Renamed the class to include 'Test'

    private static long duration;
    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    @BeforeClass
    public static void before() {
        duration = System.currentTimeMillis();
        log.info("Thread Id  | Scenario Num       | Step Count");
    }

    @AfterClass
    public static void after() {
        duration = System.currentTimeMillis() - duration;
        long seconds = (duration / 1000) % 60;
        long minutes = (duration / 1000) / 60;
        log.info("Total duration: {} minutes {} seconds", minutes, seconds);
    }
}
