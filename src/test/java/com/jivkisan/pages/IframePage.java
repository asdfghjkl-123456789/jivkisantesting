package com.jivkisan.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class IframePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(IframePage.class);

    private final By youtubeIframe = By.id("youtubeFrame");
    // Target the main canvas element of the player
    private final By mainPlayerBody = By.cssSelector("body.html5-video-player, #movie_player");

    public IframePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void switchToEmbeddedIframe() {
        logger.info("Locating YouTube tutorial frame...");
        WebElement frameElement = wait.until(ExpectedConditions.presenceOfElementLocated(youtubeIframe));
        driver.switchTo().frame(frameElement);
    }

    public void switchToMainContent() {
        driver.switchTo().defaultContent();
    }

    // NEW FALLBACK METHOD: Performs a real physical click on the video surface layout
    public void clickCenterOfVideoPlayer() {
        try {
            logger.info("Locating main player body container inside the frame...");
            WebElement player = wait.until(ExpectedConditions.presenceOfElementLocated(mainPlayerBody));
            
            logger.info("Simulating physical mouse click on the video canvas layer...");
            Actions actions = new Actions(driver);
            actions.moveToElement(player).click().perform();
            logger.info("Physical mouse action executed successfully.");
        } catch (Exception e) {
            logger.error("Physical interaction failed: " + e.getMessage());
        }
    }

    public boolean isIframePresent() {
        try {
            driver.switchTo().defaultContent();
            WebElement frame = wait.until(ExpectedConditions.presenceOfElementLocated(youtubeIframe));
            String srcAttribute = frame.getAttribute("src");
            driver.switchTo().frame(frame);
            return srcAttribute != null && srcAttribute.contains("youtube.com/embed/");
        } catch (Exception e) {
            return false;
        }
    }
}