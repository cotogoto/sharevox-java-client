package jp.livlog.voicevox;

import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * 音声再生クラス.
 *
 * このクラスは与えられた音源データを再生するために使用します。
 *
 * @author H. Aoshima
 * @version 1.0
 */
public final class PlayWave {

    /**
     * コンストラクタ. (このクラスはインスタンス化されません)
     */
    private PlayWave() {

    }


    /**
     * 音声再生を実行します.
     *
     * @param bytes 音源データ
     */
    public static void exec(final byte[] bytes) {

        try {
            // 音声データをオーディオ入力ストリームに読み込む
            final var ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(bytes));
            // オーディオ入力ストリームからデータを読み取る
            final var data = new byte[ais.available()];
            ais.read(data);
            ais.close();
            // ファイルのフォーマットを取得
            final var af = ais.getFormat();
            // 音声を再生する
            final var info = new DataLine.Info(SourceDataLine.class, af);
            final var line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(af);
            line.start();
            line.write(data, 0, data.length);
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
