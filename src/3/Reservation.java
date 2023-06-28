package lab03;

public class Reservation {
    private String fClass;
    private int numSeats;
    private int id;

    public Reservation(String fClass, int num_seats, int id) {
        this.fClass = fClass;
        this.numSeats = num_seats;
        this.id = id;
    }

    public String getfClass() {
        return this.fClass;
    }

    public int getNumSeats() {
        return this.numSeats;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return fClass + " " + numSeats;
    }
}
