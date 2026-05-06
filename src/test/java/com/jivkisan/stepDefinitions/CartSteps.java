package com.jivkisan.stepDefinitions;
 
import com.jivkisan.factory.DriverFactory;
import com.jivkisan.pages.CartPage;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
 
public class CartSteps {
    private static final Logger logger = LoggerFactory.getLogger(CartSteps.class);
    private final CartPage cartPage = new CartPage(DriverFactory.getDriver());
 
    @And("user navigates to all products page")
    public void user_navigates_to_all_products_page() {
        cartPage.navigateToAllProducts();
    }
 
    @And("user adds any two products to the cart and displays their names")
    public void user_adds_any_two_products() {
        List<String> names = cartPage.addAnyTwoProducts();
        logger.info("Products added: " + String.join(", ", names));
    }
 
    @And("user navigates to my cart menu")
    public void user_navigates_to_cart_menu() throws InterruptedException {
        logger.info("Opening cart menu...");
        cartPage.openCartMenu();
    }
 
    @Then("user should see product details and the final total value in the console")
    public void user_sees_details_in_console() {
        cartPage.printCartSummary();
    }
}