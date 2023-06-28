package lab01;

public class PuzzleResult {
    private String word;
    private int size;
    private int row;
    private int col;
    private String direction;

    public PuzzleResult(String word, int size, int row, int col, String direction) {
        this.word = word;
        this.size = size;
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    public String getWord() {
        return this.word;
    }

    public int getSize() {
        return this.size;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public String getDirection() {
        return this.direction;
    }

    public String toString() {
        return this.word + " " + this.size + " " + this.row + " " + this.col + " " + this.direction;
    }
}
