package com.ahmed.monitor.config;


public class Config {

    public static final String BOT_TOKEN =
            System.getenv("BOT_TOKEN");

    public static final String CHAT_ID =
            System.getenv("CHAT_ID");

    public static final String BOOKING_URL =
            System.getenv("BOOKING_URL");

    public static final String CHAT_ID_2 =
            System.getenv("CHAT_ID_2");

    public static final int CHECK_INTERVAL =
            Integer.parseInt(System.getenv("CHECK_INTERVAL"));

    public static final boolean HEADLESS =
            Boolean.parseBoolean(System.getenv("HEADLESS"));

}