package lab03;

public class Seat {
    private char row;
    private int col;
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private fClass fClass;
    private enum fClass {E, T};

    public Seat(int row, int col, fClass fClass) {
        this.row = alphabet[row];
        this.col = col;
        this.fClass = fClass;
    }

    public Seat() {
        this.row = 'A';
        this.col = 0;
        this.fClass = fClass.T;
    }

    public char getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        this.row = alphabet[row];
    }

    public void setCol(int col) {
        this.col = col;
    }

    public fClass getfClass() {
        return this.fClass;
    }

    @Override
    public String toString() {
        return this.col + "" + this.row;
    }
}
