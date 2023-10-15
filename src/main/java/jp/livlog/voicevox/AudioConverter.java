package jp.livlog.voicevox;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

/**
 * 音声データ変換ユーティリティクラス。
 * <p>
 * このクラスは、音声データのフォーマット変換をサポートします。
 * 主にWAV形式からMP3形式への変換を行います。
 * </p>
 */
public final class AudioConverter {

    /**
     * コンストラクタ. (このクラスはインスタンス化されません)
     */
    private AudioConverter() {

    }


    /**
     * WAVデータをMP3フォーマットに変換します。
     * <p>
     * このメソッドは、入力としてWAV形式の音声データをバイト配列として受け取り、
     * それをMP3形式に変換した後、新しいバイト配列として返します。
     * </p>
     *
     * @param wavData WAVデータを含むバイト配列。nullであってはなりません。
     * @return MP3データを含むバイト配列。変換が成功した場合のみnull以外が返されます。
     * @throws EncoderException エンコードプロセス中に問題が発生した場合にスローされます。
     * @throws IOException      I/O操作中に問題が発生した場合にスローされます。
     */
    public static byte[] convertWavToMp3(final byte[] wavData) throws EncoderException, IOException {

        // 変換プロセスのための一時ファイルを作成します。
        final var fileName = UUID.randomUUID().toString();
        final var sourcePath = Files.createTempFile(fileName, ".wav");
        final var targetPath = Files.createTempFile(fileName, ".mp3");

        try {
            // WAVデータをソースファイルに書き込みます。
            Files.write(sourcePath, wavData);

            // オーディオ属性を設定します: コーデック、ビットレート、チャンネル、サンプリングレート。
            final var audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            // エンコーディング属性を設定します: 入力フォーマット、出力フォーマット、オーディオ属性。
            final var attrs = new EncodingAttributes();
            attrs.setInputFormat("wav");
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(audio);

            // エンコーダーを作成し、変換を実行します。
            final var encoder = new Encoder();
            encoder.encode(new MultimediaObject(sourcePath.toFile()), targetPath.toFile(), attrs);

            // ターゲットファイルからMP3データを読み込み、それを返します。
            return Files.readAllBytes(targetPath);
        } finally {
            // 一時ファイルをクリーンアップします。
            Files.deleteIfExists(sourcePath);
            Files.deleteIfExists(targetPath);
        }
    }
}
