package jp.livlog.sharevox;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import jp.livlog.sharevox.Speakers.Speaker;

public class SpeakersTest {

    @Test
    void test() throws IOException {

        final var Speakers = new Speakers();
        final var list = Speakers.fetchSpeakers();
        for (final Speaker speaker : list) {
            System.out.println(speaker);
        }
    }

}
