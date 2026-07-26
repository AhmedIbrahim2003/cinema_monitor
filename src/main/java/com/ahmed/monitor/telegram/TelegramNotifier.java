package com.ahmed.monitor.telegram;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TelegramNotifier {

    private final String botToken;
    private final String chatId;
    private final HttpClient client;
    private final OkHttpClient client2 = new OkHttpClient();

    public TelegramNotifier(String botToken, String chatId) {
        this.botToken = botToken;
        this.chatId = chatId;
        this.client = HttpClient.newHttpClient();
    }
    public void sendPhoto(String imagePath, String caption) throws IOException {

        File file = new File(imagePath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("caption", caption)
                .addFormDataPart(
                        "photo",
                        file.getName(),
                        RequestBody.create(file, MediaType.parse("image/png"))
                )
                .build();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + botToken + "/sendPhoto")
                .post(requestBody)
                .build();

        try (Response response = client2.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }
    public void sendVoice(String voicePath) throws IOException {

        File file = new File(voicePath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart(
                        "voice",
                        file.getName(),
                        RequestBody.create(file, MediaType.parse("audio/ogg"))
                )
                .build();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + botToken + "/sendVoice")
                .post(requestBody)
                .build();

        try (Response response = client2.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Telegram Error: " + response.code());
            }

            System.out.println(response.body() != null ? response.body().string() : "");
        }
    }
}
