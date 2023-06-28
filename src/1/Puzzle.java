package lab01;

import java.util.*;
import java.io.*;

public class Puzzle {
    private ArrayList<ArrayList<Character>> matrix;
    private List<String> wordList;

    public Puzzle(ArrayList<ArrayList<Character>> matrix, List<String> wordList) {
        this.matrix = matrix;
        this.wordList = wordList;
    }

    public ArrayList<ArrayList<Character>> getMatrix() {
        return matrix;
    }

    public List<String> getList() {
        return wordList;
    }

    public int getSize() {
        return matrix.size();
    }

    public ArrayList<PuzzleResult> solve() {
        ArrayList<PuzzleResult> results = new ArrayList<PuzzleResult>();
        results = searchHorizontally(results);
        results = searchVertically(results);
        results = searchDiagonally(results);

        // Sort results by wordLists' order
        results.sort((PuzzleResult r1, PuzzleResult r2) -> {
            return wordList.indexOf(r1.getWord()) - wordList.indexOf(r2.getWord());
        });

        return results;
    }

    public void print(ArrayList<PuzzleResult> results) {
        // Table
        for (PuzzleResult result : results) {
            String rowCol = result.getRow() + "," + result.getCol();
            // Formatted output
            System.out.printf("%-16s %-8d %-8s %s %n", result.getWord(), result.getSize(), rowCol,
                    result.getDirection());
        }

        System.out.println();

        // Matrix
        ArrayList<ArrayList<Character>> resultMatrix = getResultMatrix(results);
        for (ArrayList<Character> row : resultMatrix) {
            for (Character c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

    }

    private ArrayList<ArrayList<Character>> getResultMatrix(ArrayList<PuzzleResult> results) {
        ArrayList<ArrayList<Character>> resultMatrix = new ArrayList<ArrayList<Character>>();
        // Fill matrix with dots
        for (int i = 0; i < getSize(); i++) {
            ArrayList<Character> row = new ArrayList<Character>();
            for (int j = 0; j < getSize(); j++) {
                row.add('.');
            }
            resultMatrix.add(row);
        }

        // Fill matrix with results
        for (PuzzleResult result : results) {
            int row = result.getRow() - 1;
            int col = result.getCol() - 1;
            String word = result.getWord().toUpperCase();
            String direction = result.getDirection();

            for (int i = 0; i < word.length(); i++) {
                if (direction.equals("Right")) {
                    resultMatrix.get(row).set(col + i, word.charAt(i));
                } else if (direction.equals("Left")) {
                    resultMatrix.get(row).set(col - i, word.charAt(i));
                } else if (direction.equals("Down")) {
                    resultMatrix.get(row + i).set(col, word.charAt(i));
                } else if (direction.equals("Up")) {
                    resultMatrix.get(row - i).set(col, word.charAt(i));
                } else if (direction.equals("DownRight")) {
                    resultMatrix.get(row + i).set(col + i, word.charAt(i));
                } else if (direction.equals("DownLeft")) {
                    resultMatrix.get(row + i).set(col - i, word.charAt(i));
                } else if (direction.equals("UpRight")) {
                    resultMatrix.get(row - i).set(col + i, word.charAt(i));
                } else if (direction.equals("UpLeft")) {
                    resultMatrix.get(row - i).set(col - i, word.charAt(i));
                }
            }
        }
        return resultMatrix;
    }

    private ArrayList<PuzzleResult> searchHorizontally(ArrayList<PuzzleResult> results) {
        // Search from left to right
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                for (String word : wordList) {
                    // If first letter matches, search for the rest of the word
                    if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                        int k = 1;
                        while (k < word.length() && j + k < getSize()) {
                            if (word.toUpperCase().charAt(k) == matrix.get(i).get(j + k)) {
                                if (k == word.length() - 1) {
                                    results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "Right"));
                                }
                                k++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Search from right to left
        for (int i = 0; i < getSize(); i++) {
            for (int j = getSize() - 1; j >= 0; j--) {
                for (String word : wordList) {
                    // If first letter matches, search for the rest of the word
                    if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                        int k = 1;
                        while (k < word.length() && j - k >= 0) {
                            if (word.toUpperCase().charAt(k) == matrix.get(i).get(j - k)) {
                                if (k == word.length() - 1) {
                                    results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "Left"));
                                }
                                k++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return results;
    }

    private ArrayList<PuzzleResult> searchVertically(ArrayList<PuzzleResult> results) {
        // Search from top to bottom
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                for (String word : wordList) {
                    // If first letter matches, search for the rest of the word
                    if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                        int k = 1;
                        while (k < word.length() && i + k < getSize()) {
                            if (word.toUpperCase().charAt(k) == matrix.get(i + k).get(j)) {
                                if (k == word.length() - 1) {
                                    results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "Down"));
                                }
                                k++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Search from bottom to top
        for (int i = getSize() - 1; i >= 0; i--) {
            for (int j = 0; j < getSize(); j++) {
                for (String word : wordList) {
                    // If first letter matches, search for the rest of the word
                    if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                        int k = 1;
                        while (k < word.length() && i - k >= 0) {
                            if (word.toUpperCase().charAt(k) == matrix.get(i - k).get(j)) {
                                if (k == word.length() - 1) {
                                    results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "Up"));
                                }
                                k++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return results;
    }

    private ArrayList<PuzzleResult> searchDiagonally(ArrayList<PuzzleResult> results) {
        // Search from bottom-right to top-left
        for (int k = 0; k <= getSize() * 2 - 2; k++) {
            for (int i = 0; i <= k; i++) {
                int j = k - i;
                if (i < getSize() && j < getSize()) {
                    for (String word : wordList) {
                        // If first letter matches, search for the rest of the word
                        if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                            int l = 1;
                            while (l < word.length() && i - l >= 0 && j - l >= 0) {
                                if (word.toUpperCase().charAt(l) == matrix.get(i - l).get(j - l)) {
                                    if (l == word.length() - 1) {
                                        results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "UpLeft"));
                                    }
                                    l++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Search from top-left to bottom-right
        for (int k = 0; k <= getSize() * 2 - 2; k++) {
            for (int i = 0; i <= k; i++) {
                int j = k - i;
                if (i < getSize() && j < getSize()) {
                    for (String word : wordList) {
                        // If first letter matches, search for the rest of the word
                        if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                            int l = 1;
                            while (l < word.length() && i + l < getSize() && j + l < getSize()) {
                                if (word.toUpperCase().charAt(l) == matrix.get(i + l).get(j + l)) {
                                    if (l == word.length() - 1) {
                                        results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "DownRight"));
                                    }
                                    l++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Search from top-right to bottom-left
        for (int k = 0; k <= getSize() * 2 - 2; k++) {
            for (int i = 0; i <= k; i++) {
                int j = k - i;
                if (i < getSize() && j < getSize()) {
                    for (String word : wordList) {
                        // If first letter matches, search for the rest of the word
                        if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                            int l = 1;
                            while (l < word.length() && i + l < getSize() && j - l >= 0) {
                                if (word.toUpperCase().charAt(l) == matrix.get(i + l).get(j - l)) {
                                    if (l == word.length() - 1) {
                                        results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "DownLeft"));
                                    }
                                    l++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Search from bottom-left to top-right
        for (int k = 0; k <= getSize() * 2 - 2; k++) {
            for (int i = 0; i <= k; i++) {
                int j = k - i;
                if (i < getSize() && j < getSize()) {
                    for (String word : wordList) {
                        // If first letter matches, search for the rest of the word
                        if (word.toUpperCase().charAt(0) == matrix.get(i).get(j)) {
                            int l = 1;
                            while (l < word.length() && i - l >= 0 && j + l < getSize()) {
                                if (word.toUpperCase().charAt(l) == matrix.get(i - l).get(j + l)) {
                                    if (l == word.length() - 1) {
                                        results.add(new PuzzleResult(word, word.length(), i + 1, j + 1, "UpRight"));
                                    }
                                    l++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return results;
    }

    public String getChallenge() {
        String result = "";
        // Format matrix
        for (ArrayList<Character> row : matrix) {
            for (char c : row) {
                result += c;
            }
            result += "\n";
        }
        // Format word list
        for (String word : wordList) {
            result += word + ";";
        }
        return result;
    }

    private static int randNum(int size) {
        Random rand = new Random();
        return rand.nextInt(size - 1);
    }

    private static char randChar() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int rand = randNum(alphabet.length());
        return alphabet.charAt(rand);
    }

    public static Puzzle parse(String fname) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<ArrayList<Character>>();
        List<String> wordList = new ArrayList<String>();

        Scanner sc = new Scanner(new File(fname));

        String firstLine = sc.nextLine();

        int size = firstLine.length();

        // Check the size of the puzzle
        if (size > 40)
            throwError(1);

        // Add first line to matrix
        matrix.add(new ArrayList<Character>());
        for (int i = 0; i < size; i++) {
            char c = firstLine.charAt(i);

            // Check if chars are uppercase
            if (Character.isLowerCase(c))
                throwError(4);
            matrix.get(0).add(c);
        }

        // Add the rest of the lines to matrix
        for (int j = 1; j < size; j++) {
            matrix.add(new ArrayList<Character>());

            String line = sc.nextLine();

            // Check for empty lines
            if (line.isEmpty())
                throwError(2);

            // Check if size matches
            if (line.length() != size)
                throwError(3);

            for (int i = 0; i < size; i++) {
                char c = line.charAt(i);

                // Check if chars are uppercase
                if (Character.isLowerCase(c))
                    throwError(4);

                matrix.get(j).add(c);
            }
        }

        // Add list of words
        while (sc.hasNext()) {
            String ln = sc.next();

            // If all uppercase, throw error
            if (isAllUpperCase(ln))
                throwError(3);

            String[] arr = ln.split("[,; ]");
            for (String s : arr) {
                if (s.length() >= 3 && alphabeticCharsOnly(s))
                    wordList.add(s);
                else
                    System.out.println("Ignoring word: " + s + ". Must be at least 3 alphabetic characters long.");
            }
        }
        sc.close();

        return new Puzzle(matrix, wordList);
    }

    public static Puzzle generate(ArrayList<String> words, int size) {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<ArrayList<Character>>();

        // Create empty matrix
        for (int i = 0; i < size; i++) {
            matrix.add(new ArrayList<Character>());
            for (int j = 0; j < size; j++) {
                matrix.get(i).add(' ');
            }
        }

        for (String word : words) {
            word = word.toUpperCase();

            // Keep trying to place word until it fits
            while (true) {
                int row = randNum(size);
                int col = randNum(size);
                int dir = randNum(8);

                int rowInc, colInc;
                switch (dir) {
                    case 1:
                        // Right
                        rowInc = 1;
                        colInc = 0;
                        break;

                    case 2:
                        // Left
                        rowInc = -1;
                        colInc = 0;
                        break;
                    
                    case 3:
                        // Up
                        rowInc = 0;
                        colInc = -1;
                        break;

                    case 4:
                        // Down
                        rowInc = 0;
                        colInc = 1;
                        break;

                    case 5:
                        // UpRight
                        rowInc = 1;
                        colInc = -1;
                        break;
                    
                    case 6:
                        // DownRight
                        rowInc = 1;
                        colInc = 1;
                        break;

                    case 7:
                        // UpLeft
                        rowInc = -1;
                        colInc = -1;
                        break;

                    case 8:
                        // DownLeft
                        rowInc = -1;
                        colInc = 1; 
                        break;
                
                    default:
                        rowInc = 0;
                        colInc = 0;
                        break;
                }
                
                //Check if word fits in the puzzle
                if (row + (word.length() - 1) * rowInc < size && row + (word.length() - 1) * rowInc >= 0 
                && col + (word.length() - 1) * colInc < size && col + (word.length() - 1) * colInc >= 0) {
                    // Check if word overlaps with another word
                    boolean overlaps = false;
                    for (int i = 0; i < word.length(); i++) {
                        if (matrix.get(row + i * rowInc).get(col + i * colInc) != word.charAt(i)
                        && matrix.get(row + i * rowInc).get(col + i * colInc) != ' ') {
                            overlaps = true;
                            break;
                        }
                    }

                    if (!overlaps) {
                        // Place word in puzzle
                        for (int i = 0; i < word.length(); i++) {
                            matrix.get(row + i * rowInc).set(col + i * colInc, word.charAt(i));
                        }
                        break;
                    }
                }
            }            
        }

        // Fill in the rest of the puzzle with random letters
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.get(i).get(j) == ' ')
                    matrix.get(i).set(j, randChar());
            }
        }
        return new Puzzle(matrix, words);
    }

    private static boolean alphabeticCharsOnly(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isAlphabetic(s.charAt(i)))
                return false;
        }
        return true;
    }

    private static boolean isAllUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isUpperCase(s.charAt(i)))
                return false;
        }
        return true;
    }

    private static void throwError(int num) {
        switch (num) {
            case 1:
                System.err.println("The puzzle is too large. Maximum size is 40x40.");
                System.exit(num);
                break;
            case 2:
                System.err.println("Beware of empty lines in the puzzle.");
                System.exit(num);
                break;
            case 3:
                System.err.println("The puzzle is not a square.");
                System.exit(num);
                break;
            case 4:
                System.err.println("All puzzle characters must be uppercase.");
                System.exit(num);
                break;
            default:
                System.err.println("Unknown error.");
                System.exit(0);
                break;
        }
    }
}
