package jp.livlog.sharevox;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ws.schild.jave.EncoderException;

public class SharevoxSynthesisTest {

    @Test
    void test() throws IOException, EncoderException {

        final var response = SharevoxSynthesis.synthesis(
                "こんにちは、SHAREVOXを試しています。",
                18,
                false);
        final var audioData = AudioConverter.convertWavToMp3(response.bytes());
        PlayWave.exec(audioData);
    }

}
