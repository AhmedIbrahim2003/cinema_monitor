package com.ahmed.monitor.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    public static final Dotenv dotenv = Dotenv.load();

    public static final String BOT_TOKEN =
            dotenv.get("BOT_TOKEN");

    public static final String CHAT_ID =
            dotenv.get("CHAT_ID");

    public static final String BOOKING_URL =
            dotenv.get("BOOKING_URL");

    public static final String CHAT_ID_2 =
            dotenv.get("CHAT_ID_2");

    public static final int CHECK_INTERVAL =
            Integer.parseInt(dotenv.get("CHECK_INTERVAL"));

    public static final boolean HEADLESS =
            Boolean.parseBoolean(dotenv.get("HEADLESS"));

}