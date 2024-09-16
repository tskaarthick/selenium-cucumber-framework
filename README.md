
# Cucumber JUnit Functional Tests

This project contains functional tests written in Cucumber with JUnit framework. These tests automate the validation of web-based functionalities using Selenium WebDriver.

## Prerequisites
Before running the tests, ensure the following are installed on your machine:

- **Java**: Version 11 or higher
- **Maven**: Version 3.6 or higher
- **Browser**: Chrome, Firefox, or any other browser to run local tests

## How to Run Tests

You can run the Cucumber JUnit functional tests in different ways depending on your setup.

### 1. Running Tests Locally

To run tests locally, ensure the desired browser (e.g., Firefox or Chrome) is installed on your machine. Set the following properties in config.properties:
```bash
browser=firefox
execution=local
```

Then, run the following Maven command:
`mvn clean test`

This will execute all tests using the local browser specified in `config.properties`.

### 2. Running Tests on Selenium Grid

To run tests on Selenium Grid, set the following properties in config.properties:
```bash
browser=firefox
execution=remote
```

Run the following Maven command:
`mvn clean test`

### 3. Running Specific Feature or Tags

To run a specific feature file or test tag, use the Maven `-Dcucumber.filter.tags` flag.

- **Run specific tag**:  
  `mvn test -Dcucumber.filter.tags="@yourTag"` -> example `mvn test -Dcucumber.filter.tags=@flooidFT`

## Test Reports

Upon running the tests, reports will be generated under the target directory:

- **target/cucumber-reports/**: Contains the HTML report for the Cucumber tests, including test results and failure details.

To view the report, open the generated `cucumber-html-reports.html` file in a browser.

## Installing Selenium Grid via Docker

To set up a standalone Selenium Grid using Docker, follow the steps below:

1. Create a Docker network named "grid" for communication between the hub and the nodes.
   `docker network create grid`
2. Start the Selenium Hub. The hub will manage the nodes and distribute tests.
   `docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub:4.24.0-20240907`
3. Add a Firefox browser node to the Selenium Grid.
   `docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-firefox:4.24.0-20240907`
4. Once the hub and nodes are running, you can access the Selenium Grid's management console by navigating to http://localhost:4444.