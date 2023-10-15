package jp.livlog.voicevox;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * VOICEVOX音声合成クラス.
 * <p>
 * このクラスはVOICEVOXエンジンを使用してテキストから音声合成を行います。
 * VOICEVOXのローカルサーバーにリクエストを送信し、音声データを取得します。
 * </p>
 * @author H. Aoshima
 * @version 1.0
 */
public final class VoicevoxSynthesis {

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build();

    /**
     * コンストラクタ. (このクラスはインスタンス化されません)
     */
    private VoicevoxSynthesis() {

    }


    /**
     * テキストから音声合成を行います.
     *
     * @param text テキスト
     * @param speaker スピーカーのID
     * @param enableInterrogativeUpspeak 疑問文の音調を有効にする場合はtrue、それ以外はfalse
     * @return 音声データのバイト配列
     * @throws IOException 音声合成に失敗した場合に発生する例外
     */
    public static byte[] synthesis(final String text, final int speaker, final boolean enableInterrogativeUpspeak) throws IOException {

        // audio_query
        final var queryUrlBuilder = HttpUrl.parse("http://localhost:50021/audio_query").newBuilder();
        queryUrlBuilder.addQueryParameter("text", text);
        queryUrlBuilder.addQueryParameter("speaker", String.valueOf(speaker));
        final var queryUrl = queryUrlBuilder.build().toString();

        final var queryRequest = new Request.Builder()
                .url(queryUrl)
                .post(RequestBody.create(new byte[0], MediaType.get("application/json"))) // 空のリクエストボディを指定
                .build();

        final var queryResponse = VoicevoxSynthesis.httpClient.newCall(queryRequest).execute();
        if (!queryResponse.isSuccessful()) {
            throw new IOException("audio_query failed: " + queryResponse);
        }

        final var queryData = queryResponse.body().string();

        final var synthUrlBuilder = HttpUrl.parse("http://localhost:50021/synthesis").newBuilder();
        synthUrlBuilder.addQueryParameter("speaker", String.valueOf(speaker));
        synthUrlBuilder.addQueryParameter("enable_interrogative_upspeak", String.valueOf(enableInterrogativeUpspeak));
        final var synthUrl = synthUrlBuilder.build().toString();

        final var mediaType = MediaType.get("application/json");
        final var synthRequestBody = RequestBody.create(queryData, mediaType);

        final var synthRequest = new Request.Builder()
                .url(synthUrl)
                .post(synthRequestBody)
                .addHeader("accept", "audio/wav")
                .addHeader("Content-Type", "application/json")
                .build();
        final var synthResponse = VoicevoxSynthesis.httpClient.newCall(synthRequest).execute();
        if (!synthResponse.isSuccessful()) {
            throw new IOException("synthesis failed: " + synthResponse);
        }

        return synthResponse.body().bytes();
    }
}
