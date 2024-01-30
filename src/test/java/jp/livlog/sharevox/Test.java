package jp.livlog.sharevox;

import java.io.IOException;

import ws.schild.jave.EncoderException;

class Test {

    @org.junit.jupiter.api.Test
    void test() {

        try {
            final var response = SharevoxSynthesis.synthesis(
                    "こんにちは、SHAREVOXを試しています。",
                    3,
                    false);
            final var audioData = AudioConverter.convertWavToMp3(response.bytes());
            PlayWave.exec(audioData);

        } catch (final IOException | EncoderException e) {
            e.printStackTrace();
        }
    }

}
