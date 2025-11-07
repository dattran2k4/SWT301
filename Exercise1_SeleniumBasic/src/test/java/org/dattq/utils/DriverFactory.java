package org.dattq.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public static WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\chrome-for-testing\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        options.setBinary("C:\\chrome-for-testing\\chrome-win64\\chrome.exe");
        String userDataDir = Paths.get("C:\\chrome-for-testing\\profile").toString();
        options.addArguments("user-data-dir=" + userDataDir);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.managed_default_content_settings.javascript", 1); //1: Cho phép (default), 2: Chặn JavaScript
        options.setExperimentalOption("prefs", prefs);
//        options.addArguments("--incognito"); // Ẩn danh

        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    };
}
