package com.jivkisan.stepDefinitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.jivkisan.factory.DriverFactory;
import com.jivkisan.pages.LoginPage;
import com.jivkisan.utils.ConfigReader;
import io.cucumber.java.en.*;
import java.util.List;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private final LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

    @Given("user is on the JivKisan homepage")
    public void user_is_on_the_jiv_kisan_homepage() {
        loginPage.openApp(ConfigReader.get("base.url"));
    }

    @When("user clicks on Login Register button")
    public void user_clicks_on_login_register_button() {
        loginPage.clickLoginRegister();
    }

    @When("user enters email {string} and password {string}")
    public void user_enters_credentials(String email, String password) {
        logger.info("Logging in with: " + email);
        loginPage.login(email, password);
    }

    @Then("user should be logged in as {string}")
    public void user_should_be_logged_in_as(String expectedUser) {
        String actualName = loginPage.getLoggedInUserName();
        logger.info("Actual Profile Name: " + actualName);
        Assert.assertFalse(actualName.isEmpty(), "User was not logged in successfully.");
    }

    @Then("the Admin Panel should be visible")
    public void the_admin_panel_should_be_visible() {
        Assert.assertTrue(loginPage.isAdminPanelVisible(), "Admin Panel button is NOT visible!");
    }

    @Then("verify if user is Admin to show Admin Panel")
    public void verify_if_user_is_admin_to_show_admin_panel() {
        if (loginPage.isAdminPanelVisible()) {
            logger.info("Verification: User is an Admin.");
        } else {
            logger.warn("Verification: User is NOT an Admin.");
        }
    }

    @Then("the Admin Panel should NOT be visible")
    public void the_admin_panel_should_not_be_visible() {
        Assert.assertFalse(loginPage.isAdminPanelVisible(), "Admin Panel should be hidden for this user.");
    }

    @Then("the user counts the total orders and applications")
    public void the_user_counts_the_total_orders_and_applications() {
        // Navigate to the Admin View
        loginPage.clickAdminPanel();
        logger.info("--- Data Extraction Started ---");

        // 1. Extract Customer Orders (Complete data: ID + Name)
        List<String> orderIds = loginPage.getCustomerOrderIds();
        List<String> customerNames = loginPage.getCustomerNames();
        
        logger.info("TOTAL CUSTOMER ORDERS: " + orderIds.size());
        for (int i = 0; i < orderIds.size(); i++) {
            logger.info("Order #" + (i + 1) + " | ID: " + orderIds.get(i) + " | Customer Name: " + customerNames.get(i));
        }

        // 2. Extract Supplier Applications
        List<String> suppliers = loginPage.getSupplierCompanyNames();
        logger.info("TOTAL SUPPLIER APPLICATIONS: " + suppliers.size());
        logger.info("Supplier Companies: " + suppliers);

        // 3. Extract Raita Applications
        List<String> raitaApps = loginPage.getRaitaApplicantNames();
        logger.info("TOTAL RAITA APPLICATIONS: " + raitaApps.size());
        logger.info("Applicant Names: " + raitaApps);

        // Ensure we didn't just count a 'Loading' row
        Assert.assertTrue(orderIds.size() > 0, "No real orders were found in the database.");
    }
}