package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordProb {

    // Process each file in the directory
    private static void processFile(File file, Map<String, Map<String, Integer>> wordMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length - 1; i++) {
                    String word = sanitizeWord(words[i]);
                    String nextWord = sanitizeWord(words[i + 1]);

                    // Update the word map
                    updateWordMap(word, nextWord, wordMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sanitize the word by removing punctuation and converting to lowercase
    private static String sanitizeWord(String word) {
        return word.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    }

    // Update the word map with the next word occurrence
    private static void updateWordMap(String word, String nextWord, Map<String, Map<String, Integer>> wordMap) {
        // Retrieve the next word map for the current word
        Map<String, Integer> nextWordsMap = wordMap.get(word);

        // If the map doesn't exist, create a new one
        if (nextWordsMap == null) {
            nextWordsMap = new HashMap<>();
            wordMap.put(word, nextWordsMap);
        }

        // Update the count of the next word
        int count = nextWordsMap.getOrDefault(nextWord, 0);
        nextWordsMap.put(nextWord, count + 1);
    }

    // Print the word distribution with probabilities
    private static void printWordDistribution(Map<String, Map<String, Integer>> wordMap) {
        for (String word : wordMap.keySet()) {
            System.out.println(word);
            Map<String, Integer> nextWordsMap = wordMap.get(word);

            // Calculate the total count of next words
            int totalCount = getTotalCount(nextWordsMap);

            for (String nextWord : nextWordsMap.keySet()) {
                int count = nextWordsMap.get(nextWord);
                double probability = calculateProbability(count, totalCount);

                // Print the next word and its probability
                System.out.println("    " + nextWord + " " + probability);
            }
        }
    }

    // Calculate the total count of next words
    private static int getTotalCount(Map<String, Integer> nextWordsMap) {
        int totalCount = 0;
        for (int count : nextWordsMap.values()) {
            totalCount += count;
        }
        return totalCount;
    }

    // Calculate the probability of the next word occurring
    private static double calculateProbability(int count, int totalCount) {
        return (double) count / totalCount;
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error! Missing command line argument of Directory Name");
            return;
        }

        String directoryName = args[0];
        File directory = new File(directoryName);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory: " + directoryName);
            return;
        }

        Map<String, Map<String, Integer>> wordMap = new HashMap<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    processFile(file, wordMap);
                }
            }
        }

        printWordDistribution(wordMap);
    }

}
