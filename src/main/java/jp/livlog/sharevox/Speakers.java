package jp.livlog.sharevox;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Speakers {

    private final OkHttpClient client;

    private final Gson         gson;

    private final String       baseUrl;

    public Speakers() {

        this("http://localhost:50025"); // デフォルトのURL
    }


    public Speakers(final String baseUrl) {

        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.baseUrl = baseUrl;
    }


    public List <Speaker> fetchSpeakers() throws IOException {

        final var url = this.baseUrl + "/speakers"; // APIエンドポイント
        final var jsonResponse = this.getJsonFromUrl(url);
        final var listType = new TypeToken <List <Speaker>>() {
        }.getType();
        return this.gson.fromJson(jsonResponse, listType);
    }


    private String getJsonFromUrl(final String url) throws IOException {

        final var request = new Request.Builder().url(url).build();
        try (var response = this.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    @Data
    public static class Speaker {

        String  name;

        String  speaker_uuid;

        Style[] styles;

        String  version;
    }

    @Data
    public static class Style {

        String name;

        int    id;
    }
}
