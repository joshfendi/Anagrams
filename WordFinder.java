// Name: Joshua Fendi
// USC NetID: 5712969966
// CS 455 PA4
// Spring 2024

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This file takes an optional command-line argument for a dictionary file name, such format as follows to fun the program:
 * "java WordFinder [dictionaryFile]". If no command-line argument is entered, the default dictionary that will be used is 
 * "sowpods.txt". A string can then be inputted, in which the program will find all valid words that can be created with 
 * subsets of the inputted string according to the provided dictionary. Each valid word found would be displayed alongside
 * a corresponding score.
 */

/**
 * This class proccesses the command-line argument and handles error processing.
 * It provides user interface to prompt and
 * subsequently process the input from a user. A user can specify a dictionary
 * and multiple "Rack" inputs, which would be
 * followed by an output of found words with their corresponding scores.
 */
public class WordFinder {
    public static void main(String[] args) {
        String dictName = "sowpods.txt";

        // optional input for dictionary, or else sowpods.txt is used
        if (args.length > 0) {
            dictName = args[0];
        }

        try {
            // dictionary
            AnagramDictionary dictionary = new AnagramDictionary(dictName);

            Scanner in = new Scanner(System.in);
            System.out.println("Type . to quit.");

            // prompt user
            while (true) {
                System.out.print("Rack? ");
                String input = in.nextLine();

                // end program
                if (input.equals(".")) {
                    in.close();
                    break;
                }
                // find anagrams

                ArrayList<Entry<String, Integer>> list = new ArrayList<>();
                // find possible words and scores
                list = findWords(input, dictionary);

                // print list of possible words
                printPossibleWords(list, input);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Dictionary file \"" + dictName + "\" does not exist.");
            System.out.println("Exiting program.");
        } catch (IllegalDictionaryException e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting program.");
        }
    }

    /**
     * find possible words and scores
     * PRE: dictionary is valid, exists and no duplicate words
     * 
     * @param input      is the string of "tiles"
     * @param dictionary is the dictionary of valid words
     * @return an Array List of Entry<String, Integer>
     */
    private static ArrayList<Entry<String, Integer>> findWords(String input, AnagramDictionary dictionary) {
        // find all the words in the dictionary based on user input
        Rack currentRack = new Rack(input);
        ArrayList<String> foundWords = new ArrayList<String>();
        
        // add all the anagrams for every subset of input found
        for (String subset : currentRack.getSubsets()) {
            for (String anagrams : dictionary.getAnagramsOf(subset)) {
                foundWords.add(anagrams);
            }
        }

        // insert each word and corresponding score
        TreeMap<String, Integer> wordsAndPoints = new TreeMap<>();
        ScoreTable scoreTable = new ScoreTable();
        for (String word : foundWords) {
            wordsAndPoints.put(word, scoreTable.getScore(word));
        }

        // convert wordsAndPoints into a list so it can be sorted
        Set<Entry<String, Integer>> entrySet = wordsAndPoints.entrySet();
        ArrayList<Entry<String, Integer>> list = new ArrayList<>(entrySet);

        // sort wordsAndPoints based on their Points
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return -(o1.getValue().compareTo(o2.getValue()));
            }
        });

        return list;
    }

    /**
     * Prints all the possible words and their corresponding scores
     * @param list contains all the found word and score pairs
     * @param input is the original Rack input from the user
     */
    private static void printPossibleWords(ArrayList<Entry<String, Integer>> list, String input) {
        System.out.println("We can make " + list.size() + " words from \"" + input + "\"");

        // if there is something in the list
        if (list.size() > 0) {
            System.out.println("All of the words with their scores (sorted by score):");
            // print score and corresponding words
            for (Entry<String, Integer> entry : list) {
                System.out.println(entry.getValue() + ": " + entry.getKey());
            }
        }
    }
}
