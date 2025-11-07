package org.dattq.tests;

import org.dattq.utils.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUpBase() {
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void tearDownBase() {
        if (driver != null) {
            driver.quit();
        }
    }

}
