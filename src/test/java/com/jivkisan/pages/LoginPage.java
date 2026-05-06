package com.jivkisan.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators (Based on your index5.html structure) ---
    private final By loginRegisterBtn = By.id("showAuthModalBtn");
    private final By emailField = By.id("loginEmail");
    private final By passwordField = By.id("loginPassword");
    private final By submitLoginBtn = By.cssSelector("#loginForm button[type='submit']");
    private final By profileName = By.id("profileName");
    private final By adminPanelBtn = By.id("adminPanelBtn");
    private final By adminDashboardHeader = By.xpath("//h2[text()='Admin Panel']");
    
    // --- Table Column Locators ---
    // Customer Orders Table
    private final By customerOrderIdList = By.cssSelector("#allOrdersTableBody tr td:nth-child(1)"); 
    private final By customerNameList = By.cssSelector("#allOrdersTableBody tr td:nth-child(3)"); 
    
    // Supplier Applications Table
    private final By supplierCompanyNameList = By.cssSelector("#supplierAdminTableBody tr td:nth-child(1)");
    
    // Raita Applications Table
    private final By raitaApplicantNameList = By.cssSelector("#adminTableBody tr td:nth-child(2)");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openApp(String url) {
        driver.get(url);
    }

    public void clickLoginRegister() {
        driver.findElement(loginRegisterBtn).click();
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitLoginBtn).click();
    }

    public String getLoggedInUserName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileName));
        return driver.findElement(profileName).getText();
    }

    public void clickAdminPanel() {
        wait.until(ExpectedConditions.elementToBeClickable(adminPanelBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminDashboardHeader));
    }

    public boolean isAdminPanelVisible() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(adminPanelBtn)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * INTERNAL SYNC LOGIC:
     * This method ensures that we wait until the Firebase data has actually rendered 
     * in the table, replacing the "Loading..." placeholder rows.
     */
    private void waitForTableDataToLoad(By locator) {
        wait.until(d -> {
            List<WebElement> elements = d.findElements(locator);
            if (elements.isEmpty()) return false;
            String text = elements.get(0).getText();
            // Wait until the text is not "Loading..." and not empty
            return !text.toLowerCase().contains("loading") && !text.trim().isEmpty();
        });
    }

    // --- Data Extraction Methods ---

    public List<String> getCustomerOrderIds() {
        waitForTableDataToLoad(customerOrderIdList);
        return driver.findElements(customerOrderIdList).stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }

    public List<String> getCustomerNames() {
        waitForTableDataToLoad(customerNameList);
        return driver.findElements(customerNameList).stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }

    public List<String> getSupplierCompanyNames() {
        waitForTableDataToLoad(supplierCompanyNameList);
        return driver.findElements(supplierCompanyNameList).stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }

    public List<String> getRaitaApplicantNames() {
        waitForTableDataToLoad(raitaApplicantNameList);
        return driver.findElements(raitaApplicantNameList).stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }
}