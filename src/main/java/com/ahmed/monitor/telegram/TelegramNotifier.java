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

    public void sendMessage(String message) throws IOException, InterruptedException {

        String url =
                "https://api.telegram.org/bot" +
                        botToken +
                        "/sendMessage?chat_id=" +
                        chatId +
                        "&text=" +
                        URLEncoder.encode(message, StandardCharsets.UTF_8);
        System.out.println(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
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
}
