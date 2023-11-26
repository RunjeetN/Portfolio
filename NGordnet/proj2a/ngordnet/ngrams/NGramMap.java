package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private static final int FOUR = 4;

    In wordsFile;
    In countsFile;
    Map<String, TimeSeries> freqs;
    TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        freqs = new TreeMap<>();
        wordsFile = new In(wordsFilename);
        countsFile = new In(countsFilename);
        counts = new TimeSeries();
        // read and store wordsFile info in a map. Key is the word, value is a TimeSeries;
        while (!wordsFile.isEmpty()) {
            String word = wordsFile.readString();
            int year = wordsFile.readInt();
            double freq = wordsFile.readDouble();
            wordsFile.readInt(); // ignore fourth column
            if (freqs.containsKey(word)) {
                freqs.get(word).put(year, freq);
            } else {
                freqs.put(word, new TimeSeries());
                freqs.get(word).put(year, freq);
            }
        }
        // read and store countsFile data
        while (!countsFile.isEmpty()) {
            String ln = countsFile.readLine();
            String[] splitLn = ln.split(",", FOUR);
            int year = Integer.parseInt(splitLn[0]);
            double usage = (double) Long.parseLong(splitLn[1]);
            counts.put(year, usage);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(freqs.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return counts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts1 = new TimeSeries(freqs.get(word), startYear, endYear);
        TimeSeries ts2 = new TimeSeries(counts, startYear, endYear);
        return ts1.dividedBy(ts2);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!freqs.containsKey(word)) {
            return new TimeSeries();
        } else {
            return weightHistory(word, MIN_YEAR, MAX_YEAR);
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        TimeSeries result = null;
        if (words.isEmpty()) {
            return sum;
        }
        for (String word : words) {
            if (freqs.keySet().contains(word)) {
                result = weightHistory(word, startYear, endYear);
            }
            if (result != null) {
                sum = result.plus(sum);
            }
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
