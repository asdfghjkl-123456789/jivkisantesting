package com.jivkisan.pages;
 
import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.util.ArrayList;

import java.util.List;
 
public class CartPage {

    private final WebDriver driver;

    private final WebDriverWait wait;
 
    // Locators

    private final By allProductsLink = By.xpath("/html/body/header/div/nav/a[2]");

    private final By productsGrid = By.id("all-products-grid");

    private final By productCards = By.xpath("//*[@id='all-products-grid']/div");

    // More robust locators for the cart button

    private final By cartIconBtn = By.xpath("//*[@id=\"myCartBtn\"]"); 

    private final By cartItemsInMenu = By.cssSelector("#cartItemsContainer");

    private final By subtotalElement = By.id("cartSubtotal");

    private final By authModal = By.id("authModal");
 
    public CartPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    }
 
    public void navigateToAllProducts() {

        try {

            wait.until(ExpectedConditions.invisibilityOfElementLocated(authModal));

        } catch (Exception e) { }
 
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(allProductsLink));

        try {

            link.click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        }

        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(productsGrid));

    }
 
    public List<String> addAnyTwoProducts() {

        List<String> addedProductNames = new ArrayList<>();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));

        List<WebElement> cards = driver.findElements(productCards);
 
        for (int i = 0; i < 2 && i < cards.size(); i++) {

            WebElement card = cards.get(i);

            String name = card.findElement(By.tagName("h3")).getText();

            addedProductNames.add(name);
 
            WebElement addButton = card.findElement(By.xpath(".//button[contains(translate(., 'ADD', 'add'), 'add')]"));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", addButton);

            try {

                addButton.click();

            } catch (Exception e) {

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);

            }

            System.out.println("LOG: Added to cart -> " + name);

            try { Thread.sleep(1500); } catch (InterruptedException e) { }

        }

        return addedProductNames;

    }
 
    public void openCartMenu() throws InterruptedException {

        // Scroll to top first to ensure header elements are not intercepted by floating elements

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        // Find cart button using a more flexible approach

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cartIconBtn));

        Thread.sleep(2000);

        try {

            btn.click();

        } catch (Exception e) {

            // Fallback to JS click if the icon is physically obstructed

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        }

        // Wait for cart content to be visible

        wait.until(ExpectedConditions.visibilityOfElementLocated(subtotalElement));

    }

 
    public void printCartSummary() {

        System.out.println("\n========== Jivkisan Bill ==========");

        List<WebElement> items = driver.findElements(cartItemsInMenu);

        if (items.isEmpty()) {

            System.out.println("No items found in the cart UI!");

        }
 
        
 
        String totalValue = driver.findElement(subtotalElement).getText();

        System.out.println("----------------------------------");

        System.out.println("FINAL TOTAL VALUE: " + totalValue);

        System.out.println("==================================\n");

    }

}
 