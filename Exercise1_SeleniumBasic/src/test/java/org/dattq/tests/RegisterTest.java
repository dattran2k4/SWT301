package org.dattq.tests;

import org.dattq.pages.RegisterPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Register Tests for the-internet.herokuapp.com")
public class RegisterTest extends BaseTest {
    static WebDriverWait wait;
    static RegisterPage registerPage;

    @BeforeAll
    static void initPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Thêm WebDriverWait
        registerPage = new RegisterPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Should submit form successfully with full valid data")
    void testRegisterSuccess() {
        registerPage.navigate();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.selectGender("Male");
        registerPage.enterMobile("1234567890");
        registerPage.setDateOfBirth("06 Nov 1990");
        registerPage.enterSubject("Maths");
        registerPage.selectHobby("Reading");
        registerPage.uploadPicture("C:\\Users\\This PC\\Downloads\\pickleball.png");
        registerPage.enterCurrentAddress("123 Demo St");
        registerPage.selectState("NCR");
        registerPage.selectCity("Delhi");
        registerPage.clickSubmit();
        assertTrue(registerPage.isModalDisplayed(), "Modal should be displayed");
        registerPage.closeModal();
    }

    @Test
    @Order(2)
    @DisplayName("Should submit form successfully with not full valid data (firstName, lastName, gender, phone)")
    void testRegisterSuccess2() {
        registerPage.navigate();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.selectGender("Female");
        registerPage.enterMobile("1234567890");

        registerPage.clickSubmit();
        assertTrue(registerPage.isModalDisplayed(), "Modal should be displayed");
        registerPage.closeModal();
    }

    @Test
    @Order(3)
    @DisplayName("Should submit form unsuccessfully with invalid email")
    void testInvalidEmail() {
        registerPage.navigate();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.selectGender("Female");
        registerPage.enterEmail("john.doe.com");
        registerPage.enterMobile("1234567890");

        registerPage.clickSubmit();
        assertTrue(!registerPage.isModalDisplayed(), "Modal should be not displayed");
    }

    @Test
    @Order(4)
    @DisplayName("Should submit form unsuccessfully with invalid phone")
    void testInvalidPhone() {
        registerPage.navigate();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.selectGender("Female");
        registerPage.enterMobile("1234590");
        registerPage.clickSubmit();
        assertTrue(!registerPage.isModalDisplayed(), "Modal should be not displayed");
    }

    @Test
    @Order(4)
    @DisplayName("Should submit form unsuccessfully with not select gender")
    void testNoGenderSelected() {
        registerPage.navigate();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterMobile("1234567890");
        registerPage.clickSubmit();
        assertTrue(!registerPage.isModalDisplayed(), "Modal should be not displayed");
    }

    @Test
    @Order(5)
    @DisplayName("Should submit form unsuccessfully with Upload non-existent file")
    void testUploadInvalidFile() {
        registerPage.navigate();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.selectGender("Female");
        registerPage.enterMobile("1234567890");
        Assertions.assertThrows(Exception.class, () -> {registerPage.uploadPicture("C:\\Users\\This PC\\Downloads\\abc.png");});
    }

    @Order(6)
    @ParameterizedTest(name = "Register Test - {0} {1}, Email: {2}, Gender: {3} -> Expected: {4}")
    @CsvSource({
            "John, Doe, john@gmail.com, Male, success",            // all valid
            "Jane, , jane@gmail.com, Female, error",               // missing lastName
            ", Smith, smith@gmail.com, Male, error",              // missing firstName
            "Alice, Green, alicegmail.com, Female, error",        // invalid email
            "Bob, Brown, bob@gmail.com, , error"                  // missing gender
    })
    @DisplayName("Parameterized Test - Register Form")
    void testRegister(String firstName, String lastName, String email, String gender, String expectedResult) {

        lastName = lastName == null ? "" : lastName;
        firstName = firstName == null ? "" : firstName;
        gender = gender == null ? "" : gender;


        registerPage.navigate();
        registerPage.enterFirstName(firstName);
        registerPage.enterLastName(lastName);
        registerPage.enterEmail(email);
        if (gender != null && !gender.isBlank()) {
            registerPage.selectGender(gender);
        }
        registerPage.enterMobile("1234567890");
        registerPage.clickSubmit();


        if (expectedResult.equals("success")) {
            assertTrue(registerPage.isModalDisplayed(), "Form should be submitted successfully");
        } else {
            assertTrue(!registerPage.isModalDisplayed(), "Validation errors should be shown");
        }
    }
}
