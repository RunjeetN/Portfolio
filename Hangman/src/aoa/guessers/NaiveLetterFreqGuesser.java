package aoa.guessers;

import aoa.utils.FileUtils;
import org.antlr.v4.runtime.tree.Tree;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
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
        return map;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        int max = 0;
        char guess = '?';
        Map<Character, Integer> map = getFrequencyMap();
        for(char letter : map.keySet()){
            if (!guesses.contains(letter)){ // if letter hasn't been guessed before
                if(map.get(letter) > max){      // if freq of letter is max so far
                    max = map.get(letter);
                    guess = letter;
                }
            }
        }
        return guess;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
