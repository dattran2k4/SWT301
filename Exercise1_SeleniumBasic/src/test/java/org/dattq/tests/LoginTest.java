package org.dattq.tests;

import org.dattq.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Login Tests for the-internet.herokuapp.com")

public class LoginTest extends BaseTest {
    static WebDriverWait wait;
    static LoginPage loginPage;

    @BeforeAll
    static void initPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Thêm WebDriverWait
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Should login successfully with valid credentials")
    void testLoginSuccess() {
        loginPage.navigate();
        loginPage.login("tomsmith", "SuperSecretPassword!");
        // Chờ thông báo thành công hiển thị
        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.success"))
        );


        //String successMsg = driver.findElement(By.cssSelector(".flash.success")).getText();
        assertTrue(successMsg.getText().contains("You logged into a secure area!"));
    }

    @Test
    @Order(2)
    @DisplayName("Should display error when logging in with invalid credentials")
    void testLoginFail() {
        loginPage.navigate();
        loginPage.login("wronguser", "wrongpassword");

        // Chờ thông báo lỗi hiển thị
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.error"))
        );

        assertTrue(errorMsg.getText().contains("Your username is invalid!"));
    }

    @Order(3)
    @ParameterizedTest(name = "Test Login - Username: {0}, Password: {1}")
    @CsvSource({
            "tomsmith, SuperSecretPassword!, success",         // valid
            "wronguser, SuperSecretPassword!, error",           // invalid username
            "tomsmith, wrongpassword, error",                   // invalid password
            "'', '', error"                                     // empty credentials
    })
    @DisplayName("Multiple login attempts using @CsvSource")
    void testLoginWithMultipleParameters(String username, String password, String expectedResult) {
        loginPage.navigate();
        loginPage.login(username, password);

        By messageLocator = expectedResult.equals("success")
                ? By.cssSelector(".flash.success")
                : By.cssSelector(".flash.error");

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));

        if (expectedResult.equals("success")) {
            assertTrue(message.getText().contains("You logged into a secure area!"));
        } else {
            assertTrue(message.getText().toLowerCase().contains("invalid"));
        }
    }

    @ParameterizedTest(name = "CSV File: {0} / {1}")
    @Order(4)
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    void testLoginFromCSV(String username, String password, String expected) {
        loginPage.navigate();
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();

        loginPage.login(username, password);
        By resultLocator = expected.equals("success") ? loginPage.getSuccessLocator() : loginPage.getErrorLocator();
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(resultLocator));

        if (expected.equals("success")) {
            assertTrue(result.getText().contains("You logged into a secure area!"));
        } else {
            assertTrue(result.getText().toLowerCase().contains("invalid"));
        }
    }


    @AfterAll
    static void tearDown() {
        driver.quit();
    }


}
