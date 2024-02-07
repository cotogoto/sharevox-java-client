package jp.livlog.sharevox;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Speakers {

    private static final OkHttpClient client   = new OkHttpClient();

    private static final Gson         gson     = new Gson();

    private static final String       BASE_URL = "http://localhost:50025"; // デフォルトのURL

    // インスタンス化を防ぐためのプライベートコンストラクタ
    private Speakers() {

    }


    /**
     * ShareVoxからスピーカー情報のリストをフェッチします。
     *
     * @return スピーカー情報のリスト
     * @throws IOException ネットワークリクエストが失敗した場合にスローされます
     */
    public static List <Speaker> fetchSpeakers() throws IOException {

        final var url = Speakers.BASE_URL + "/speakers"; // APIエンドポイント
        final var jsonResponse = Speakers.getJsonFromUrl(url);
        final TypeToken <List <Speaker>> listType = new TypeToken <>() {
        };
        return Speakers.gson.fromJson(jsonResponse, listType.getType());
    }


    /**
     * 指定されたURLからJSON応答を取得します。
     *
     * @param url リクエストURL
     * @return 応答のJSON文字列
     * @throws IOException ネットワークリクエストが失敗した場合にスローされます
     */
    private static String getJsonFromUrl(final String url) throws IOException {

        final var request = new Request.Builder().url(url).build();
        try (var response = Speakers.client.newCall(request).execute()) {
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
