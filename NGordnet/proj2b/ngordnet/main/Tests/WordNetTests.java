package ngordnet.main.Tests;

import ngordnet.main.WordNet;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class WordNetTests {
    @Test
    public void testConstructor() {
        String synsetFile = "./data/wordnet/synsets11.txt";
        String hyponymFile = "./data/wordnet/hyponyms11.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        System.out.println(wn.printGraph());
        System.out.println(wn.printWords());

    }
}
