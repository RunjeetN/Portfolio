package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;


public class HyponymsHandler extends NgordnetQueryHandler {
    private static final int START_YEAR = 1900;
    private static final int END_YEAR = 2020;
    private WordNet wn;
    NGramMap ngm;

    public HyponymsHandler(WordNet w, NGramMap ngm) {
        wn = w;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        String hyponyms = wn.lookupMultiple(q.words().toString());
        int startYear = q.startYear();
        int endYear = q.endYear();
        if (q.k() != 0) {
            if (q.startYear() == 0) {
                startYear = START_YEAR;
            }
            if (q.endYear() == 0) {
                endYear = END_YEAR;
            }
            return passThrough(hyponyms, q.k(), startYear, endYear);
        }
        return hyponyms;
    }

    public String passThrough(String words, int k, int startYear, int endYear) {
        Set<String> result = new TreeSet<>();
        Map<Integer, String> freqs = new TreeMap<>();
        String[] wordArr = words.split(", ");
        for (String word : wordArr) {
            if (ngm.countHistory(word, startYear, endYear) != null) {
                freqs.put(sum(ngm.countHistory(word, startYear, endYear)), word);
            }
        }
        return getMaxes(freqs, result, k).toString();
    }

    private int sum(TimeSeries ts) {
        int sum = 0;
        for (Double val : ts.values()) {
            sum += val;
        }
        return sum;
    }

    private Set<String> getMaxes(Map<Integer, String> freqs, Set<String> result, int k) {
        Set<Integer> keys = freqs.keySet();
        Set<Integer> topK = new TreeSet<>();
        Object o;
        while (k > 0 && keys.size() > 0) {
            o = Collections.max(keys);
            result.add(freqs.get((Integer) o));
            keys.remove((Integer) o);
            k--;
        }
        return result;
    }
}
