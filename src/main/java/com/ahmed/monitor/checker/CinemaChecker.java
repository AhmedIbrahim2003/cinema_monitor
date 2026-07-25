package com.ahmed.monitor.checker;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CinemaChecker {

    private final Page page;

    public CinemaChecker(Page page) {
        this.page = page;
    }

    /**
     * Checks whether the target booking date is available.
     *
     * @param targetDate The date to look for.
     * @return true if the booking date exists; otherwise false.
     */
    public boolean checkBooking(LocalDate targetDate) {

        // Wait until the list of available dates is loaded.
        page.waitForSelector(".glxDaysList");

        // Format the date to match the HTML id:
        // Example: data-01-08-2026
        String targetId = "data-" +
                targetDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Locator targetDateLocator = page.locator("#" + targetId);

        boolean available = targetDateLocator.count() > 0;

        if (available) {
            System.out.println("✅ Booking available for " + targetDate);
        } else {
            System.out.println("❌ Booking not available for " + targetDate);
        }

        return available;
    }

    /**
     * Prints all currently available booking dates.
     * Useful for debugging.
     */
    public void printAvailableDates() {

        Locator dates = page.locator(".glxDaysList li");

        int count = dates.count();

        System.out.println("Available dates:");

        for (int i = 0; i < count; i++) {

            String id = dates.nth(i).getAttribute("id");

            System.out.println(id);
        }
    }

    public boolean selectDateAndTakeScreenshot(LocalDate targetDate, String screenshotPath) {

        String targetId = "data-" +
                targetDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Locator dateLocator = page.locator("#" + targetId);

        if (dateLocator.count() == 0) {
            System.out.println("❌ Date not found: " + targetDate);
            return false;
        }

        // Click the date
        dateLocator.click();

        // Wait for the page to update after clicking
        page.waitForLoadState();

        // Optional: Wait until the date becomes active
        page.waitForFunction(
                "id => document.getElementById(id)?.classList.contains('active')",
                targetId
        );

        // Take a screenshot
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(screenshotPath))
                .setFullPage(true));

        System.out.println("📸 Screenshot saved to " + screenshotPath);

        return true;
    }
    public void deleteScreenshot(String screenshotPath) {
        try {
            boolean deleted = Files.deleteIfExists(Path.of(screenshotPath));

            if (deleted) {
                System.out.println("🗑️ Screenshot deleted: " + screenshotPath);
            } else {
                System.out.println("⚠️ Screenshot not found: " + screenshotPath);
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to delete screenshot: " + e.getMessage());
        }
    }
}
