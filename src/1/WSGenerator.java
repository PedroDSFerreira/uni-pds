package lab01;

import java.io.*;
import java.util.*;

public class WSGenerator {
    public static void main(String[] args) throws IOException {
        String fname = "", output = "";
        int size = 40;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    fname = args[i + 1];
                    break;
                case "-s":
                    size = Integer.parseInt(args[i + 1]);
                    if (size < 1 || size > 40)
                        throwError(1);
                    break;
                case "-o":
                    output = args[i + 1];
                    break;
            }
        }

        ArrayList<String> words = readFile(fname);
        Puzzle p = Puzzle.generate(words, size);

        String challenge = p.getChallenge();
        if (output.equals(""))
            System.out.println(challenge);
        else
            writeFile(output, challenge);
    }

    private static ArrayList<String> readFile(String fname) throws IOException {
        ArrayList<String> lst = new ArrayList<>();
        Scanner sc = new Scanner(new File(fname));
        while (sc.hasNextLine()) {
            String[] words = sc.nextLine().split("[,; ]");
            for (String str : words) {
                if (str.length() >= 3)
                    lst.add(str);
                else
                    System.out.println("Ignoring word: " + str + ". Minimum length is 3.");
            }
        }
        return lst;
    }

    private static void writeFile(String fname, String output) throws IOException {
        PrintWriter pw = new PrintWriter(new File(fname));
        pw.println(output);
        pw.close();
    }

    private static void throwError(int n) {
        switch (n) {
            case 1:
                System.err.println("Make sure size is between 1 and 40.");
                System.exit(n);
                break;
            default:
                System.err.println("Unknown error.");
                System.exit(0);
                break;
        }
    }
}
