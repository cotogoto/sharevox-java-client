package jp.livlog.voicevox;

import java.io.IOException;

import ws.schild.jave.EncoderException;

class Test {

    @org.junit.jupiter.api.Test
    void test() {

        try {
            var audioData = VoicevoxSynthesis.synthesis(
                    "こんにちは、VOICEVOXを試しています。",
                    3,
                    false);
            audioData = AudioConverter.convertWavToMp3(audioData);
            PlayWave.exec(audioData);

        } catch (final IOException | EncoderException e) {
            e.printStackTrace();
        }
    }

}
