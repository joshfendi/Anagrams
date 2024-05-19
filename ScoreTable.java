// Name: Joshua Fendi
// USC NetID: 5712969966
// CS 455 PA4
// Spring 2024

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Score Table has information about how much each scrabble letter is worth
 * Representation Invariants:
 *  Point1 is immutable and contains upper cased Characters that correspond to 1 point
 *  Point2 is immutable and contains upper cased Characters that correspond to 2 point
 *  Point3 is immutable and contains upper cased Characters that correspond to 3 point
 *  Point4 is immutable and contains upper cased Characters that correspond to 4 point
 *  Point5 is immutable and contains upper cased Characters that correspond to 5 point
 *  Point8 is immutable and contains upper cased Characters that correspond to 8 point
 *  Point10 is immutable and contains upper cased Characters that correspond to 10 point
 */
public class ScoreTable {
    private final ArrayList<Character> Point1;
    private final ArrayList<Character> Point2;
    private final ArrayList<Character> Point3;
    private final ArrayList<Character> Point4;
    private final ArrayList<Character> Point5;
    private final ArrayList<Character> Point8;
    private final ArrayList<Character> Point10;

    public ScoreTable() {
        // Every letter only appears once out of all the lists
        Point1 = new ArrayList<Character>(Arrays.asList('A', 'E', 'I', 'O', 'U', 'L', 'N', 'S', 'T', 'R'));
        Point2 = new ArrayList<Character>(Arrays.asList('D', 'G'));
        Point3 = new ArrayList<Character>(Arrays.asList('B', 'C', 'M', 'P'));
        Point4 = new ArrayList<Character>(Arrays.asList('F', 'H', 'V', 'W', 'Y'));
        Point5 = new ArrayList<Character>(Arrays.asList('K'));
        Point8 = new ArrayList<Character>(Arrays.asList('J', 'X'));
        Point10 = new ArrayList<Character>(Arrays.asList('Q', 'Z'));
    }

    /**
     * Calculates the score for a word
     * PRE: word contains only alphabetical letters, either lower or upper case
     * 
     * @param word 
     * @return the score for word
     */
    public int getScore(String word) {
        int score = 0;

        if (word != "") {
            char[] cArr = word.toCharArray();
            
            for (char c : cArr) {
                // convert to upper case if not upper case already
                if (c >= 'a' && c <= 'z') {
                    c = Character.toUpperCase(c);
                }

                // add to score
                if (Point1.contains(c)) {
                    score += 1;
                } else if (Point2.contains(c)) {
                    score += 2;
                } else if (Point3.contains(c)) {
                    score += 3;
                } else if (Point4.contains(c)) {
                    score += 4;
                } else if (Point5.contains(c)) {
                    score += 5;
                } else if (Point8.contains(c)) {
                    score += 8;
                } else if (Point10.contains(c)) {
                    score += 10;
                }
            }
        }

        return score;
    }
}
