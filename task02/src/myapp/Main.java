package myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Path p = Paths.get(args[0]);
        File f = p.toFile();

        Map<String, wordcount> table1 = new HashMap<>();
        wordcount wc = new wordcount();

        // Open a file as input stream to buffer reaader, then read everything
        InputStream is = new FileInputStream(f);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        // instntiate the line
        String line;

        while ((line = br.readLine()) != null) {
            line = line.toLowerCase(); // convert all text to lower case
            line = line.replaceAll("\\p{P}", ""); // removing all punctuations from the readline
            String[] words = line.split(" "); // splitting it into array

            String firstword;
            String nextword;

            // we can do our programming here.

            for (int i = 0; i < (words.length - 1); i++) { // while scanning for the words. minus to ignore the last
                                                           // word
                firstword = words[i];                       // defining positions
                nextword = words[i + 1];
                if (!table1.containsKey(firstword)) {
                    wc = new wordcount(firstword, nextword);
                    table1.put(firstword, wc);
                } else if (table1.containsKey(firstword)) {
                    wc = table1.get(firstword);
                    wc.evaluate(nextword);
                    table1.put(firstword, wc);
                }
            }

        }
        Set<String> words = table1.keySet();
        for (String w: words) {
            wc = table1.get(w);
            System.out.println("key = " + w);
            wc.print();
        }
        

        br.close();
        isr.close();
        is.close();
    }
}

// Get a list of all the words
// Set<String> words = wordFreq.keySet();
// for (String w : words) {
// int count = wordFreq.get(w);
// System.out.printf(": %s = %d\n", w, count);
// }

// System.out.printf("Number of words: %d\n", numWords);
// System.out.printf("Number of unique words: %d\n", words.size());
// System.out.printf("Percentage of unique words: %f\n", words.size() / (float)
// numWords);
