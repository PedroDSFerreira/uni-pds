package lab01;

import java.io.IOException;
import java.util.ArrayList;

public class WSSolver {
    public static void main(String[] args) throws IOException {
        Puzzle p = Puzzle.parse("data/" + args[0]);
        ArrayList<PuzzleResult> results = p.solve();
        p.print(results);
    }
}

