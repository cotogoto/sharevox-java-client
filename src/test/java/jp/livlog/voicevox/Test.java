package jp.livlog.voicevox;

import java.io.IOException;

public class Test {

    public static void main(final String[] args) {

        try {
            final var audioData = VoicevoxSynthesis.synthesis(
                    "この列車は、函館線、宗谷線直通、特急宗谷号・稚内行です。",
                    1,
                    false);
            PlayWave.exec(audioData);

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
