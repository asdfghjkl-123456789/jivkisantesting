package com.jivkisan.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.Desktop;
import java.io.File;

public class AutoItReportTest { 
    private static final Logger logger = LoggerFactory.getLogger(AutoItReportTest.class);

    @Test
    public void executeAutoItReportReader() {
        logger.info("LOG: --- Starting Set J: Launching TestNG Emailable Report ---");
        
        // 1. Give TestNG 3 seconds to fully write and finalize the HTML report file
        try { 
            Thread.sleep(3000); 
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        }

        // 2. Define the new target path requested
        String targetReportPath = "C:/Users/harsha.k3/Downloads/Test_Jivkisan/test-output/emailable-report.html";
        File reportFile = new File(targetReportPath);
        
        // 3. Verify that the report file exists
        Assert.assertTrue(reportFile.exists(), "TestNG Emailable Report is missing at: " + targetReportPath);

        // 4. Automatically open the emailable report in your default web browser
        try {
            logger.info("Launching TestNG Emailable Report in your default browser...");
            Desktop.getDesktop().browse(reportFile.toURI());
            logger.info("Report launched successfully: " + reportFile.toURI());
        } catch (Exception e) {
            logger.error("Failed to automatically open browser window: " + e.getMessage());
            Assert.fail("Set J failed to open the emailable report.");
        }
    }
}