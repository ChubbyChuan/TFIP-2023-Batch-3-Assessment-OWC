package myapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class wordcount {

    private int count = 1;
    private String firstword;
    private String nextword;
    private Map<String, Integer> wordFreq = new HashMap<>();

    public wordcount() {
    }

    public wordcount(String firstword, String nextword) {
        this.firstword = firstword;
        this.nextword = nextword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFirstword() {
        return firstword;
    }

    public void setFirstword(String firstword) {
        this.firstword = firstword;
    }

    public String getNextword() {
        return nextword;
    }

    public void setNextword(String nextword) {
        this.nextword = nextword;
    }

    public void evaluate(String nextword) {
       
        if (!wordFreq.containsKey(nextword)) {
            wordFreq.put(nextword, 1); // if the next word is not present, add 1
        } else {
            int c = wordFreq.get(nextword); // if the next word is already present, take a inital count plus 1
            wordFreq.put(nextword, ((c + 1)));
        }
        this.count++; // adding count for first words

    }

    public void print() {
        Set<String> words = wordFreq.keySet();
        for (String w: words) {
            float prob = (wordFreq.get(w)/this.count);
            String probf = String.valueOf(prob);
            System.out.println("   2nd word  " + w + "      probability = " + probf);
        }
    }
}
