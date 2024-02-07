package jp.livlog.sharevox;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public final class SharevoxSynthesis {

    private static final OkHttpClient httpClient           = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build();

    private static final Gson         gson                 = new Gson();

    private static final MediaType    JSON                 = MediaType.get("application/json");

    private static final String       BASE_URL             = "http://localhost:50025";

    private static final String       AUDIO_QUERY_ENDPOINT = "/audio_query";

    private static final String       SYNTHESIS_ENDPOINT   = "/synthesis";

    private static final int          OUTPUT_SAMPLING_RATE = 16000;

    private SharevoxSynthesis() {

    }


    public static ResponseBody synthesis(final String text, final int speaker, final boolean enableInterrogativeUpspeak) throws IOException {

        final var queryData = SharevoxSynthesis.createAudioQuery(text, speaker);
        return SharevoxSynthesis.executeSynthesis(queryData, speaker, enableInterrogativeUpspeak);
    }


    private static String createAudioQuery(final String text, final int speaker) throws IOException {

        final var queryUrl = HttpUrl.parse(SharevoxSynthesis.BASE_URL + SharevoxSynthesis.AUDIO_QUERY_ENDPOINT)
                .newBuilder()
                .addQueryParameter("text", text)
                .addQueryParameter("speaker", String.valueOf(speaker))
                .build();
        final var queryRequest = new Request.Builder()
                .url(queryUrl)
                .post(RequestBody.create(new byte[0], SharevoxSynthesis.JSON)) // Empty body for POST request
                .build();

        try (var response = SharevoxSynthesis.httpClient.newCall(queryRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("audio_query failed: HTTP code " + response.code());
            }
            try (var responseBody = response.body()) {
                if (responseBody == null) {
                    throw new IOException("audio_query failed: response body is null");
                }
                final var queryData = responseBody.string();
                final Map <String, Object> map = SharevoxSynthesis.gson.fromJson(queryData, new TypeToken <Map <String, Object>>() {
                }.getType());
                map.put("outputSamplingRate", SharevoxSynthesis.OUTPUT_SAMPLING_RATE);
                return SharevoxSynthesis.gson.toJson(map);
            }
        }
    }


    private static ResponseBody executeSynthesis(final String queryData, final int speaker, final boolean enableInterrogativeUpspeak)
            throws IOException {

        final var synthUrl = HttpUrl.parse(SharevoxSynthesis.BASE_URL + SharevoxSynthesis.SYNTHESIS_ENDPOINT)
                .newBuilder()
                .addQueryParameter("speaker", String.valueOf(speaker))
                .addQueryParameter("enable_interrogative_upspeak", String.valueOf(enableInterrogativeUpspeak))
                .build();
        final var synthRequestBody = RequestBody.create(queryData, SharevoxSynthesis.JSON);
        final var synthRequest = new Request.Builder()
                .url(synthUrl)
                .post(synthRequestBody)
                .addHeader("accept", "audio/wav")
                .addHeader("Content-Type", "application/json")
                .build();

        final var response = SharevoxSynthesis.httpClient.newCall(synthRequest).execute();
        if (!response.isSuccessful()) {
            throw new IOException("synthesis failed: HTTP code " + response.code());
        }
        return response.body(); // Note: The caller is responsible for closing this ResponseBody.
    }
}
