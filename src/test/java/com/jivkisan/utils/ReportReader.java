package com.jivkisan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportReader {
    private static final Logger logger = LoggerFactory.getLogger(ReportReader.class);

    public static String readCucumberReport(String reportPath) {
        StringBuilder content = new StringBuilder();
        File file = new File(reportPath);

        if (!file.exists()) {
            logger.error("Report file not found at: " + reportPath);
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.error("Error reading the file: " + e.getMessage());
        }

        return content.toString();
    }
}