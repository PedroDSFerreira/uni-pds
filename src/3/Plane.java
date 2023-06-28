package lab03;

public class Plane {
    private int execRows;
    private int execCols;
    private int tourRows;
    private int tourCols;

    public Plane(int execRows, int execCols, int tourRows, int tourCols) {
        this.execRows = execRows;
        this.execCols = execCols;
        this.tourRows = tourRows;
        this.tourCols = tourCols;
    }

    public Plane(int tourRows, int tourCols) {
        this.execRows = 0;
        this.execCols = 0;
        this.tourRows = tourRows;
        this.tourCols = tourCols;
    }

    public int getTotalSeats() {
        return this.execRows * this.execCols + this.tourRows * this.tourCols;
    }

    public int getExecSeats() {
        return this.execRows * this.execCols;
    }

    public int getTourSeats() {
        return this.tourRows * this.tourCols;
    }

    public int getCols() {
        return this.execCols + this.tourCols;
    }

    public int getRows() {
        if (this.execRows > this.tourRows)
            return this.execRows;
        return this.tourRows;
    }

    public int getExecRows() {
        return this.execRows;
    }

    public int getTourRows() {
        return this.tourRows;
    }

    public int getExecCols() {
        return this.execCols;
    }

    public int getTourCols() {
        return this.tourCols;
    }
}