package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
public class TestOneWordKNot0Hyponyms {
    public static final String WORDS_FILE = "./data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "./data/ngrams/total_counts.csv";
    public static final String SYNSET_FILE = "./data/wordnet/synsets.txt";
    public static final String HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    @Test
    public void testChangeAndOccurrence(){
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SYNSET_FILE, HYPONYM_FILE);
        List<String> words = List.of("occurrence");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 15);
        String actual = studentHandler.handle(nq);
        String expected = "[case, change, development, end, example, experience, going, head, increase, last, report, result, set, side, time]";
        assertThat(actual).isEqualTo(expected);
    }
}
