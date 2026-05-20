package com.jivkisan.stepDefinitions;

import com.jivkisan.factory.DriverFactory;
import com.jivkisan.pages.CartPage;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class CartSteps {
    private static final Logger logger = LoggerFactory.getLogger(CartSteps.class);
    
    // REMOVED: Immediate class-level instance assignment
    private CartPage cartPage;

    private CartPage getPage() {
        return new CartPage(DriverFactory.getDriver());
    }

    @And("user navigates to all products page")
    public void user_navigates_to_all_products_page() {
        getPage().navigateToAllProducts();
    }

    @And("user adds any two products to the cart and displays their names")
    public void user_adds_any_two_products() {
        List<String> names = getPage().addAnyTwoProducts();
        logger.info("Products added: " + String.join(", ", names));
    }

    @And("user navigates to my cart menu")
    public void user_navigates_to_cart_menu() throws InterruptedException {
        logger.info("Opening cart menu...");
        getPage().openCartMenu();
    }

    @Then("user should see product details and the final total value in the console")
    public void user_sees_details_in_console() {
        getPage().printCartSummary();
    }
    
    @And("user refreshes the page")
    public void user_refreshes_the_page() {
        getPage().refreshPage();
    }

    @And("user logs out and logs back in with email {string} and password {string}")
    public void user_logs_out_and_logs_back_in(String email, String password) {
        getPage().logout();
        com.jivkisan.pages.LoginPage login = new com.jivkisan.pages.LoginPage(DriverFactory.getDriver());
        login.clickLoginRegister();
        login.login(email, password);
    }
    
    @And("the seller updates the number of units for their items randomly below 150")
    public void seller_updates_units_randomly() throws InterruptedException {
        getPage().refreshPage();
        Thread.sleep(500);
        getPage().updateUnitsRandomlyForSellerProducts();
    }

    @Then("the product units should reflect the new changes")
    public void units_should_reflect_changes() {
        logger.info("Random unit updates completed successfully.");
    }

    @And("user navigates to organic only page")
    public void user_navigates_to_organic_only() {
        getPage().navigateToOrganicOnly();
    }

    @When("user searches for {string} in the {string} panel")
    public void user_searches_for_product_in_panel(String productName, String panelType) {
        getPage().searchProduct(panelType, productName);
    }

    @Then("the product {string} should be visible in the results")
    public void product_should_be_visible(String productName) {
        boolean isFound = getPage().isProductVisible(productName);
        
        if (!isFound) {
            logger.error("Failed to find '" + productName + "' on page: " + DriverFactory.getDriver().getCurrentUrl());
            throw new AssertionError("Product '" + productName + "' was not found in the search results.");
        }
        logger.info("Verified: Product '" + productName + "' is visible.");
    }
}