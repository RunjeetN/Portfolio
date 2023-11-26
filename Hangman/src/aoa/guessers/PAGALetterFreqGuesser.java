package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // get list of matching words (taking into account both pattern and guesses)
        // make a freqMap based on list
        Map<Character, Integer> frequencyMap = getFrequencyMap(getMatchingWordsEdited(pattern, guesses));
        // return guess based on frequency map
        return getGuessHelper(guesses, frequencyMap);
    }

    public List<String> getMatchingWords(String pattern) {
        // return a list of words that match the pattern
        List<String> matchingWords = new ArrayList<>();
        boolean matching = true;
        for (String word : words) {
            if (word.length() != pattern.length()){
                matching = false;
            }
             else{
                 for (int i = 0; i < word.length(); i++) {
                    if (pattern.charAt(i) != '-')
                        if(pattern.charAt(i) != word.charAt(i)) {
                            matching = false;
                        }
                    if(pattern.charAt(i) == '-'){
                        if(getPatternList(pattern).contains(word.charAt(i))){
                            matching = false;
                        }
                    }
                }
            }
            if (matching) {
                matchingWords.add(word);
            }
            matching = true;
        }
        return matchingWords;
    }


    public List<String> getMatchingWordsEdited(String pattern, List<Character> guesses) { //Removes shit letters
        // return a list of words that match the pattern and don't have incorrectly guessed letters
        // Returns list of letters that aren't in pattern
        List<Character> incorrectGuesses = new ArrayList<>();
        List<String> goodWords = new ArrayList<>();
        for(Character letter : guesses){
            if (pattern.indexOf(letter) == -1){
                incorrectGuesses.add(letter);
            }
        }
        for (String word : getMatchingWords(pattern)) {
           if(tOrF(word, incorrectGuesses)){
               goodWords.add(word);
           }
        }
        return goodWords;
    }
    public List<Character> getPatternList(String pattern){
        List<Character> patternList = new ArrayList<>();
        for(int i = 0; i < pattern.length(); i++){
            if(pattern.charAt(i) != '-'){
                patternList.add(pattern.charAt(i));
            }
        }
        return patternList;
    }
    public boolean tOrF(String w, List<Character> c){
        for(int k=0; k<w.length(); k++){
            if(c.contains(w.charAt(k))){
                return false;
            }
        }
        return true;
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
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
