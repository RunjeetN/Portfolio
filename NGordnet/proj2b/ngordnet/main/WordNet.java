package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Graph g;
    private Map<String, String[]> idsFirst;
    private Map<String, ArrayList<String>> wordsFirst;

    private In h;
    private In s;

    public WordNet(String synsetFile, String hyponymsFile) {
        h = new In(hyponymsFile);
        s = new In(synsetFile);
        g = new Graph();
        idsFirst = new TreeMap<>();
        wordsFirst = new HashMap<>();


        // create graph with hyponymsFile
        while(!h.isEmpty()) {
            String line = h.readLine();
            String[] ids = line.split("," , -2);
            for(int k = 1; k < ids.length; k++) {
                g.addEdge(ids[0], ids[k]);
            }
        }
        // create map with ids,synsets
        while(!s.isEmpty()) {
            String line = s.readLine();
            String[] johnny = line.split(","); // hehe get it ;)
            String[] lst = johnny[1].split(" ");
            idsFirst.put(johnny[0], lst);
            // johnny likes it both ways
        }
        for(String key : idsFirst.keySet()) {
            for(String word : idsFirst.get(key)) {
                if (wordsFirst.containsKey(word)) {
                    wordsFirst.get(word).add(key);
                }
                else {
                    wordsFirst.put(word, new ArrayList<>());
                    wordsFirst.get(word).add(key);
                }
            }
        }
    }
    public String lookup(String word) {
        return setLookup(word).toString();
    }
    private List<String> setLookup(String word) {
        List<String> result = new ArrayList<>();
        List<String> ids = getIds(word);
        List<String> hyponyms = new ArrayList<>();
        for(String id : ids) {
            hyponyms.addAll(getHyponyms(id));
        }
        // convert from ids to words
        for(String id : ids) {
            for(String w : idsFirst.get(id))
                result.add(w); // add every word in first synset associated with the word
        }
        for(String id : hyponyms){
            for(String w : idsFirst.get(id)) {
                result.add(w);
            }
        }
        return result;
    }
    public String lookupMultiple(String words) {
        words = words.replace("[", "");
        words = words.replace("]", "");

        String[] lst = words.split(", ");
        if(lst.length == 0) {
            return "[]";
        }
        List<String> result = setLookup(lst[0]);
        List<String> temp = new ArrayList<>();
        for(String word : lst) {
            temp = setLookup(word);
            terminator(result, temp);
        }
        return result.toString();
    }
    private void terminator(List<String> result, List<String> temp) {
        result.retainAll(temp);
    }
    private List<String> getHyponyms(String id){
        List<String> hyponyms = g.getNeighbors(id);
        List<String> result = new ArrayList<>();
        for(String h : hyponyms) {
            result.add(h);
            result.addAll(getHyponyms(h));
        }
        return result;
    }

    private ArrayList<String> getIds(String word) {
        return wordsFirst.get(word);
    }


    public String printGraph() {
        return g.toString();
    }
    public String printIds(){
        return idsFirst.keySet().toString();
    }
    public String printWords(){
        return wordsFirst.keySet().toString();
    }
}