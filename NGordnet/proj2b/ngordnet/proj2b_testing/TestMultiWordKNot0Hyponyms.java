package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.main.WordNet;
import ngordnet.ngrams.NGramMap;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestMultiWordKNot0Hyponyms {
    public static final String WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    @Test
    public void testMultiWordKNot0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("change, occurrence");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 3);
        String actual = studentHandler.handle(nq);
        String expected = "[change, development, going]";
        assertThat(actual).isEqualTo(expected);
    }
}
