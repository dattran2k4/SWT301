package org.dattq.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("userEmail");

    private By genderRadio(String gender) {
        // có thể là: "Male", "Female", "Other"
        return By.xpath("//label[normalize-space()='" + gender + "']");
    }

    private By mobileField = By.id("userNumber");

    private By dateOfBirthInput = By.id("dateOfBirthInput");

    private By subjectsInput = By.id("subjectsInput");

    private By hobbiesCheckbox(String hobby) {
        // tìm input dựa vào label text
        return By.xpath("//label[normalize-space(text())='" + hobby + "']");
    }
    private By uploadPictureInput = By.id("uploadPicture");

    private By currentAddressArea = By.id("currentAddress");

    private By stateDropdown = By.id("state");

    private By stateOption(String state) {
        return By.xpath("//div[contains(@id,'react-select-3-option') and text()='" + state + "']");
    }

    private By cityDropdown = By.id("city");

    private By cityOption(String city) {
        return By.xpath("//div[contains(@id,'react-select-4-option') and text()='" + city + "']");
    }

    private By submitButton = By.id("submit");

    private By modalDialog = By.className("modal-content");

    private By closeModalButton = By.id("closeLargeModal");

    public void navigate() {
        navigateTo("https://demoqa.com/automation-practice-form");
    }

    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void selectGender(String gender) {
        click(genderRadio(gender));
    }

    public void enterMobile(String mobile) {
        type(mobileField, mobile);
    }

    public void setDateOfBirth(String date) {
        // đơn giản: click vào date input, rồi chọn date tùy tình huống
        click(dateOfBirthInput);
        driver.findElement(dateOfBirthInput).sendKeys(Keys.ESCAPE);
        // Logic chọn ngày — có thể bạn sẽ cần chọn tháng/năm rồi ngày
        // Ví dụ bạn có thể tiếp tục: choose year, choose month, choose day
    }

    public void enterSubject(String subject) {
        type(subjectsInput, subject);
        // sau đó gửi Enter
        driver.findElement(subjectsInput).sendKeys(Keys.ENTER);
    }

    public void selectHobby(String hobby) {
        click(hobbiesCheckbox(hobby));
    }

    public void uploadPicture(String filePath) {
        driver.findElement(uploadPictureInput).sendKeys(filePath);
    }

    public void enterCurrentAddress(String address) {
        type(currentAddressArea, address);
    }

    public void selectState(String state) {
        WebElement button = driver.findElement(stateDropdown); // ← tìm element thật
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        click(stateDropdown);
        click(stateOption(state));
    }

    public void selectCity(String city) {
        click(cityDropdown);
        click(cityOption(city));
    }

    public void clickSubmit() {
        WebElement button = driver.findElement(submitButton); // ← tìm element thật
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    public boolean isModalDisplayed() {
        try {
            return waitForVisibility(modalDialog).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeModal() {
        click(closeModalButton);
    }

    public void invalid() {

    }
}
