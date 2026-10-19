# Testing_Framework — Hybrid UI + API Test Automation Framework

A hybrid Selenium + REST-assured test automation framework built with **Java, TestNG, and Maven**. It automates the [Sauce Demo](https://www.saucedemo.com) e-commerce site end-to-end (login, products, cart, logout) and the [Swagger Petstore](https://petstore.swagger.io) public API (POST/PUT/GET/DELETE), with HTML reporting via ExtentReports and CI wiring for Jenkins.

## Tech Stack

| Layer | Tool |
|---|---|
| Test runner | TestNG 7.12 |
| UI automation | Selenium WebDriver 4.49 |
| API automation | REST-assured 6.0 + Jackson (for request/response models) |
| Reporting | ExtentReports 5.1 |
| Logging | Log4j2 |
| Build | Maven |
| CI | Jenkins |

## Project Structure

```
Testing_Framework/
├── src/main/java/io/learn/
│   ├── Pages/              # Page Object classes (LoginPage, ProductsPage, CartPage)
│   ├── utils/               # ConfigReader, WebDriverFactory, Browser enum, ScreenshotUtility
│   └── exception/           # Custom exceptions (LoginFailedException, ItemNotInCartException, ...)
├── src/main/resources/
│   └── log4j.xml             # Logging configuration
├── src/test/java/io/learn/
│   ├── AppTest/              # UI test classes (BaseTest, LoginTest, ProductTest, CartTest, Logout)
│   ├── AppTest/API/          # API test class (APITest) + Pet request model
│   ├── API/                  # ApiClient (REST-assured wrapper)
│   ├── Listener/             # TestListener & APITestListener (ExtentReports + screenshots)
│   └── dataProvider/         # UserData (TestNG @DataProvider source)
├── src/test/resources/
│   └── application.properties  # App URL, credentials, API base URI
├── testng.xml                 # Full UI regression suite
├── testng-smoke-suite.xml     # Smoke tests only (group = "smoke")
├── testng-parallel.xml        # Full UI suite, run in parallel (4 threads, by class)
├── testng-datadriven.xml      # Data-driven login suite (DataProviderExample)
├── API-Testng.xml             # API test suite (Petstore)
├── Jenkinsfile                 # CI pipeline definition
└── pom.xml
```

## Prerequisites

- Java 17+
- Maven 3.6+
- Microsoft Edge (default browser for the UI suites — see `application.properties`/suite `<parameter>` tags to change it)

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Kavya-Goyal/Testing_Framework.git
   cd Testing_Framework
   ```
2. Install dependencies:
   ```bash
   mvn clean install -DskipTests
   ```
3. Review `src/test/resources/application.properties` for the app URL, test credentials, and API base URI:
   ```properties
   app.url=https://www.saucedemo.com/
   username=standard_user
   password=secret_sauce
   api.base.uri=https://petstore.swagger.io
   ```

## Running the Tests

Each suite below is a separate TestNG XML file, so run whichever fits what you need:

```bash
# Full UI regression suite (Login, Logout, Product, Cart)
mvn clean test -DsuiteXmlFile=testng.xml

# Smoke tests only
mvn clean test -DsuiteXmlFile=testng-smoke-suite.xml

# Full UI suite, run in parallel across classes
mvn clean test -DsuiteXmlFile=testng-parallel.xml

# Data-driven login tests (multiple user types via @DataProvider)
mvn clean test -DsuiteXmlFile=testng-datadriven.xml

# API tests (Swagger Petstore: POST → PUT → GET → DELETE → GET)
mvn clean test -DsuiteXmlFile=API-Testng.xml
```

The browser used by the UI suites is passed as a TestNG `<parameter name="browser" .../>` in each suite XML (currently `edge`); update it there to switch browsers, provided `WebDriverFactory`/`Browser` support the target browser.

## Reports & Logs

- **ExtentReports**: an HTML report is generated on each run via `TestListener` (UI) / `APITestListener` (API), including a screenshot attached automatically on UI test failure.
- **Log4j2**: execution logs are controlled by `src/main/resources/log4j.xml`.

## CI/CD

The included `Jenkinsfile` checks out the repo, runs `mvn clean install -DskipTests`, then `mvn clean test`, and publishes the ExtentReports HTML output as a Jenkins build artifact.

## Notes

- The API suite runs against the public Swagger Petstore sandbox (`petstore.swagger.io`), a shared demo server. `APITest` includes a `@BeforeClass` cleanup step (`idCheck`) that deletes any leftover pet with the test's ID before starting, to keep runs idempotent — but as a public shared server, occasional flakiness from concurrent external users is possible.
- Test methods in `APITest` are chained with `dependsOnMethods` (POST → PUT → GET → DELETE → GET) so that a failure in one step cleanly skips the dependent steps instead of producing unrelated failures further down the chain.
