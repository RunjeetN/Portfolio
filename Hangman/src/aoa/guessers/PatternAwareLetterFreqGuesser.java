package aoa.guessers;

import aoa.utils.FileUtils;
import org.apache.commons.collections.list.TreeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) { // CONSTRUCTOR
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // create a list of words that match the pattern
        List<String> matchingWords = getMatchingWords(pattern);
        // create a frequency map based on matching words
        Map<Character, Integer> frequencyMap = getFrequencyMap(matchingWords);
        // return guess
        return getGuessHelper(guesses, frequencyMap);
    }
    public List<String> getMatchingWords(String pattern) {
        // return a list of words that match the pattern
        List<String> matchingWords = new ArrayList<String>();
        boolean matching = true;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.length() > pattern.length()){
                    matching = false;
                }
                if (pattern.charAt(i) != '-' && pattern.charAt(i) != word.charAt(i)) {
                    matching = false;
                }
            }
            if (matching) {
                matchingWords.add(word);
            }
            matching = true;
        }
        System.out.println(matchingWords.toString());
        return matchingWords;
    }
    public Map<Character, Integer> getFrequencyMap(List<String> words) {
        TreeMap<Character, Integer> map = new TreeMap<>();
        char letter = 'a';
        int freq = 0;

        for(String word: words){
            for(int i = 0; i < word.length(); i++){
                if(!map.containsKey(word.charAt(i))){
                    map.put(word.charAt(i), 1);
                }
                else{
                    map.put(word.charAt(i), map.get(word.charAt(i)) + 1);
                }
            }
        }
        System.out.println(map.toString());
        return map;
    }

    public char getGuessHelper(List<Character> guesses, Map<Character, Integer> freqMap) {
        int max = 0;
        char guess = '?';
        for(char letter : freqMap.keySet()){
            if (!guesses.contains(letter)){ // if letter hasn't been guessed before
                if(freqMap.get(letter) > max){      // if freq of letter is max so far
                    max = freqMap.get(letter);
                    guess = letter;
                }
            }
        }
        return guess;
    }
    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}