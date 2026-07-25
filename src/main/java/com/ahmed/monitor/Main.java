package com.ahmed.monitor;

import com.ahmed.monitor.checker.CinemaChecker;
import com.ahmed.monitor.config.Config;
import com.ahmed.monitor.telegram.TelegramNotifier;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

            System.out.println("Checking... " + LocalDateTime.now());
            try (Playwright playwright = Playwright.create()) {

                Browser browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(true)
                );

                Page page = browser.newPage();
                System.out.println("BOOKING_URL = " + Config.BOOKING_URL);

                if (Config.BOOKING_URL == null || Config.BOOKING_URL.isBlank()) {
                    throw new RuntimeException("BOOKING_URL is null or blank");
                }

                page.navigate(String.valueOf(Config.BOOKING_URL));

                CinemaChecker checker = new CinemaChecker(page);

                // Print all available dates (optional)
                checker.printAvailableDates();

                // Check if Aug 1 is available
                boolean available = checker.checkBooking(
                        LocalDate.of(2026, 7, 29)
                );

                if (available) {
                    System.out.println("Send Telegram notification!");
                    TelegramNotifier notifier =
                            new TelegramNotifier(
                                    Config.BOT_TOKEN,
                                    Config.CHAT_ID);
                    TelegramNotifier notifier2 =
                            new TelegramNotifier(
                                    Config.BOT_TOKEN,
                                    Config.CHAT_ID_2);
                    checker.selectDateAndTakeScreenshot(
                            LocalDate.of(2026, 7, 29),
                            "screenshots/booking.png"
                    );
                    try {

                        notifier.sendPhoto("screenshots/booking.png", "🎉 Booking is available!");

                        notifier2.sendPhoto("screenshots/booking.png", "🎉 Booking is available!");

                        checker.deleteScreenshot("screenshots/booking.png");
                        System.out.println("Done");


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

                browser.close();
            }

    }

}
