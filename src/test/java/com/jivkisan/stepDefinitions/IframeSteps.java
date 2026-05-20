package com.jivkisan.stepDefinitions;

import com.jivkisan.factory.DriverFactory;
import com.jivkisan.pages.IframePage;
import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class IframeSteps {
    private static final Logger logger = LoggerFactory.getLogger(IframeSteps.class);

    private IframePage getPage() {
        return new IframePage(DriverFactory.getDriver());
    }

    @When("user focuses on the embedded iframe window frame")
    public void user_focuses_on_the_embedded_iframe_window_frame() {
        WebDriver driver = DriverFactory.getDriver();
        
        driver.switchTo().defaultContent();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Scrolls down smoothly to prevent sticky headers from cutting off our element view
        js.executeScript("window.scrollBy(0,350);");
        
        getPage().switchToEmbeddedIframe();
    }

    @Then("verify that the internal player is fully loaded and visible")
    public void verify_that_the_internal_player_is_fully_loaded_and_visible() {
        boolean isFrameValid = getPage().isIframePresent();
        Assert.assertTrue(isFrameValid, "Iframe structural validation failed.");
        
        // Execute the native mouse action to break past code restrictions
        getPage().clickCenterOfVideoPlayer();
        
        // Holds the viewport context for 10 seconds so you can watch it buffer and play live
        try {
            logger.info("Holding execution thread for 10 seconds to watch player playback...");
            Thread.sleep(4000); 
        } catch (InterruptedException e) {
            logger.error("Thread wait interrupted: " + e.getMessage());
        }
    }

    @And("user switches focus back to the main application container")
    public void user_switches_focus_back_to_the_main_application_container() {
        getPage().switchToMainContent();
    }
}