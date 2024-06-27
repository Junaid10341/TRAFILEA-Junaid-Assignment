# Shapermint Checkout Automation

This project contains a Selenium-based test automation suite for validating the Shapermint checkout page. The tests are written in Java and use TestNG as the test framework.

## Project Structure

- **src/main/java**: Contains the main Java classes.
- **src/test/java**: Contains the test classes.
- **pom.xml**: Maven configuration file.
- **.gitignore**: Specifies files and directories to be ignored by Git.

## Prerequisites

- Java 11
- Maven 3.6+
- A browser driver (e.g., ChromeDriver, GeckoDriver)

## Dependencies

The project uses the following dependencies:

- Selenium Java: 3.141.59
- TestNG: 7.7.0

These dependencies are defined in the `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>3.141.59</version>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.7.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>

Setting Up the Project

git clone https://github.com/Junaid10341/TRAFILEA-Junaid-Assignment

Install Dependencies

mvn clean install

Configure Browser Driver:

Ensure that the appropriate browser driver (e.g., ChromeDriver for Chrome) is installed and its path is set in the system environment variables.

Running Tests
To run the tests, use the following command:
mvn test

