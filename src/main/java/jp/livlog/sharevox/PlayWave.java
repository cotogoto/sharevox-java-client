package jp.livlog.sharevox;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 音声データ再生ユーティリティクラス。
 * <p>
 * このクラスは、音声データ（WAVまたはMP3）の再生をサポートします。
 * </p>
 */
@Slf4j
public final class PlayWave {

    /**
     * コンストラクタ. (このクラスはインスタンス化されません)
     */
    private PlayWave() {

    }


    /**
     * 音声データを再生します。
     * <p>
     * このメソッドは、入力された音声データ（バイト配列）を解析し、
     * MP3またはWAVとして再生を試みます。
     * </p>
     *
     * @param bytes 音声データを含むバイト配列。nullであってはなりません。
     */
    public static void exec(final byte[] bytes) {

        try {
            // 音声データをオーディオ入力ストリームに読み込む
            final var bais = new ByteArrayInputStream(bytes);
            final var ais = AudioSystem.getAudioInputStream(bais);

            // ファイルのフォーマットを取得
            final var af = ais.getFormat();

            // MP3ファイルの場合
            if (af.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                PlayWave.playMp3(bytes);
            }
            // WAVファイルの場合
            else {
                PlayWave.playWav(ais, af);
            }
        } catch (final UnsupportedAudioFileException e) {
            // サポートされていないオーディオフォーマットの場合、MP3と仮定して再生を試みる
            try {
                PlayWave.playMp3(bytes);
            } catch (final JavaLayerException jle) {
                jle.printStackTrace();
                System.out.println();
                PlayWave.log.error("Playback failure: " + jle.getMessage());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            PlayWave.log.error("Playback failure: " + e.getMessage());
        }
    }


    /**
     * MP3データを再生します。
     * <p>
     * このメソッドは、入力されたMP3データを再生します。
     * 再生が終了した場合、追加の処理を実行することもできます。
     * </p>
     *
     * @param mp3Data MP3データを含むバイト配列。nullであってはなりません。
     * @throws JavaLayerException MP3データの再生中に問題が発生した場合にスローされます。
     */
    private static void playMp3(final byte[] mp3Data) throws JavaLayerException {

        final var player = new AdvancedPlayer(new ByteArrayInputStream(mp3Data));
        player.setPlayBackListener(new PlaybackListener() {

            @Override
            public void playbackFinished(final PlaybackEvent evt) {

                // 再生終了後、処理がある場合はコードを記載
            }
        });
        player.play();
    }


    /**
     * WAVデータを再生します。
     * <p>
     * このメソッドは、入力されたWAVデータを再生します。
     * </p>
     *
     * @param ais オーディオ入力ストリーム。nullであってはなりません。
     * @param af  オーディオフォーマット。nullであってはなりません。
     * @throws LineUnavailableException 適切なラインが利用できない場合にスローされます。
     * @throws IOException              I/O操作中に問題が発生した場合にスローされます。
     */
    private static void playWav(final AudioInputStream ais, final AudioFormat af) throws LineUnavailableException, IOException {

        final var info = new DataLine.Info(SourceDataLine.class, af);
        final var line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(af);
        line.start();

        final var data = new byte[4096];
        int bytesRead;
        while ((bytesRead = ais.read(data, 0, data.length)) != -1) {
            line.write(data, 0, bytesRead);
        }

        line.drain();
        line.close();
        ais.close();
    }
}
