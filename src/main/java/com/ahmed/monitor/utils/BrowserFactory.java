package com.ahmed.monitor.utils;


import com.ahmed.monitor.config.Config;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

    public static Browser createBrowser(Playwright playwright) {

        return playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(Config.HEADLESS)
        );

    }

}