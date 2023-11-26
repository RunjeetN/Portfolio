package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import static aoa.utils.FileUtils.readWordsOfLength;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        if(wordLength < 1){
            throw new IllegalArgumentException();
        }
        if(readWordsOfLength(dictionaryFile, wordLength).isEmpty()){
            throw new IllegalStateException();
        }
        int numWords = readWordsOfLength(dictionaryFile, wordLength).size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = readWordsOfLength(dictionaryFile, wordLength).get(randomlyChosenWordNumber);

        // make basePattern
        String basePattern = "";
        for(int i = 0; i < chosenWord.length(); i++){
            basePattern += "-";
        }
        pattern = basePattern;

    }

    @Override
    public int makeGuess(char letter) {
        int counter = 0;
        for(int j = 0; j < pattern.length(); j++){
            if(chosenWord.charAt(j) == letter && pattern.charAt(j) == '-'){
                pattern = pattern.substring(0, j) + letter + pattern.substring(j + 1);
                counter += 1;
            }
        }
        return counter;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
