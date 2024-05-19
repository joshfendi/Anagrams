// Name: Joshua Fendi
// USC NetID: 5712969966
// CS 455 PA4
// Spring 2024

import java.util.*;
import java.util.ArrayList;

/**
 * A Rack of Scrabble tiles
 * 
 * Representation Invariant:
 *    generatedSubsets can contain an empty String "" or multiple Strings
 */
public class Rack {
   private ArrayList<String> generatedSubsets;

   /**
    * Create a rack and generates subsets of input string
    * 
    * @param str a string
    */
   public Rack(String str) {
      // convert str into char array
      char[] charArray = str.toCharArray();
      ArrayList<Character> charArrayList = new ArrayList<>();

      // filter out non-letters and add to Array List
      for (char c : charArray) {
         if (!(c >= 'a' && c <= 'z') || !(c >= 'A' && c <= 'Z')) {
            charArrayList.add(c);
         }
      }

      // count characters in str
      TreeMap<Character, Integer> countChars = new TreeMap<>();
      for (char c : charArrayList) {
         // already exists in countChars
         if (countChars.containsKey(c)) {
            countChars.put(c, countChars.get(c) + 1);
         }
         // no entry exists yet
         else {
            countChars.put(c, 1);
         }
      }

      // extract a string and corresponding ints
      Set<Character> setOfChars = countChars.keySet();
      Collection<Integer> counts = countChars.values();

      // concatenate all chars into unique string
      String unique = "";
      for (Character c : setOfChars) {
         unique += c;
      }

      // put all counts in array
      int[] mult = new int[counts.size()];
      int index = 0;
      for (Integer count : counts) {
         mult[index] = count;
         ++index;
      }

      // all generated subsets
      generatedSubsets = allSubsets(unique, mult, 0);
   }

   /**
    * Getter to return generated subsets
    *
    * @return generated subsets
    */
   public ArrayList<String> getSubsets() {
      // defensive copy
      return new ArrayList<String>(generatedSubsets);
   }

   /**
    * Finds all subsets of the multiset starting at position k in unique and mult.
    * unique and mult describe a multiset such that mult[i] is the multiplicity of
    * the char
    * unique.charAt(i).
    * PRE: mult.length must be at least as big as unique.length()
    * 0 <= k <= unique.length()
    * 
    * @param unique a string of unique letters
    * @param mult   the multiplicity of each letter from unique.
    * @param k      the smallest index of unique and mult to consider.
    * @return all subsets of the indicated multiset. Unlike the multiset in the
    *         parameters,
    *         each subset is represented as a String that can have repeated
    *         characters in it.
    * @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();

      if (k == unique.length()) { // multiset is empty
         allCombos.add("");
         return allCombos;
      }

      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k + 1);

      // prepend all possible numbers of the first char (i.e., the one at position k)
      // to the front of each string in restCombos. Suppose that char is 'a'...

      String firstPart = ""; // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {
         for (int i = 0; i < restCombos.size(); i++) { // for each of the subsets
                                                       // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));
         }
         firstPart += unique.charAt(k); // append another instance of 'a' to the first part
      }

      return allCombos;
   }

}
