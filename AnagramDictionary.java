// Name: Joshua Fendi
// USC NetID: 5712969966
// CS 455 PA4
// Spring 2024

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

/**
 * A dictionary of all anagram sets.
 * Note: the processing is case-sensitive; so if the dictionary has all lower
 * case words, you will likely want any string you test to have all lower case
 * letters too, and likewise if the dictionary words are all upper case.
 * 
 * Representation Invariant:
 *    anagramHashMap contains keys that are in canonical form, and values that are Strings that correspond to the canoncical form
 */
public class AnagramDictionary {
   private HashMap<String, ArrayList<String>> anagramHashMap; // key: canonical form, value: Array List of actual english words

   /**
    * Create an anagram dictionary from the list of words given in the file
    * indicated by fileName.
    * 
    * @param fileName the name of the file to read from
    * @throws FileNotFoundException      if the file is not found
    * @throws IllegalDictionaryException if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException, IllegalDictionaryException {
      anagramHashMap = new HashMap<String, ArrayList<String>>();

      File file = new File(fileName);

      // try reading file
      try (Scanner in = new Scanner(file)) {
         while (in.hasNext()) {
            // process a word in dictionary
            String nextWord = in.next();
            char[] charArray = nextWord.toCharArray();
            Arrays.sort(charArray); // sort to get cononical form
            String cononicalString = new String(charArray); // word with characters in alphabetical order

            // if already contains cononical String (key)
            if (anagramHashMap.containsKey(cononicalString)) {
               ArrayList<String> words = anagramHashMap.get(cononicalString);
               // check for duplicate words in dictionary
               if (words.contains(nextWord)) {
                  throw new IllegalDictionaryException(
                        "ERROR: Illegal dictionary: dictionary file has a duplicate word: " + nextWord);
               } else { // add word to list
                  words.add(nextWord);
               }
            }
            // does not contain cononical String yet (key)
            else {
               ArrayList<String> words = new ArrayList<String>();
               words.add(nextWord);
               anagramHashMap.put(cononicalString, words);
            }
         }
      }
   }

   /**
    * Get all anagrams of the given string. This method is case-sensitive.
    * E.g. "CARE" and "race" would not be recognized as anagrams.
    * 
    * @param string string to process
    * @return a list of the anagrams of string
    */
   public ArrayList<String> getAnagramsOf(String string) {
      // there is a valid word in dictionary
      if (anagramHashMap.containsKey(string)) {
         // defensive copy
         return new ArrayList<String>(anagramHashMap.get(string));
      }
      // no anagrams of string in dictionary
      else {
         return new ArrayList<String>();
      }
   }

}
