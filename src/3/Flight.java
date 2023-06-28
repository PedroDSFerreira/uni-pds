package lab03;

import java.util.*;

public class Flight {
    private String code;
    private int takenExec = 0;
    private int takenTour = 0;
    private Plane plane;
    // private Map<Integer, List<Seat>> takenSeats = new HashMap<Integer, List<Seat>>();
    private String[][] execMap;
    private String[][] tourMap;
    private List<Reservation> reservations = new LinkedList<Reservation>();

    public Flight() {
        this.code = null;
        this.plane = new Plane(0, 0);
    }

    public Flight(String code, int tourCols, int tourRows) {
        this.code = code;
        this.plane = new Plane(tourRows, tourCols);
        tourMap = new String[this.plane.getTourRows()][this.plane.getTourCols()];
    }

    public Flight(String code, int execCols, int execRows, int tourCols, int tourRows) {
        this.code = code;
        this.plane = new Plane(execRows, execCols, tourRows, tourCols);
        execMap = new String[this.plane.getExecRows()][this.plane.getExecCols()];
        tourMap = new String[this.plane.getTourRows()][this.plane.getTourCols()];
    }

    public int getNumReservations() {
        return this.reservations.size();
    }

    public String getCode() {
        return this.code;
    }

    public int getTakenExec() {
        return this.takenExec;
    }

    public int getTakenTour() {
        return this.takenTour;
    }

    public void setTakenExec(int i) {
        this.takenExec = i;
    }

    public void setTakenTour(int i) {
        this.takenTour = i;
    }

    public Reservation[] getReservations() {
        return this.reservations.toArray(new Reservation[this.reservations.size()]);
    }

    public boolean addReservation(Reservation r) {
        // Check if the flight has an executive class
        if (r.getfClass().equals("E") && this.plane.getExecSeats() == 0)
            return false;

        // Check if there are enough seats available
        if (r.getfClass().equals("E") && this.plane.getExecSeats() - this.takenExec >= r.getNumSeats()) {
            this.takenExec += r.getNumSeats();
            this.reservations.add(r);
            addSeats(r);
            return true;
        } else if (r.getfClass().equals("T") && this.plane.getTourSeats() - this.takenTour >= r.getNumSeats()) {
            this.takenTour += r.getNumSeats();
            this.reservations.add(r);
            addSeats(r);
            return true;
        }
        
        return false;
    }

    private void addSeats(Reservation r) {
        int i = 0;
        if (r.getfClass().equals("E")) {
            while (!findExecSeat(i, r)) {
                i++;
            }
        } else {
            while (!findTourSeat(i, r)) {
                i++;
            }
        }
    }

    private boolean findTourSeat(int i, Reservation r) {
        for (int j = 0; j < this.plane.getCols(); j++) {
            if (tourMap[i][j] == null) {
                for (int k = 0; k < r.getNumSeats(); k++) {
                    if (i < this.plane.getTourRows()){
                        tourMap[i][j] = String.valueOf(r.getID());
                        i++;
                    } else {
                        i = 0;
                        j++;
                        tourMap[i][j] = String.valueOf(r.getID());
                        i++;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean findExecSeat(int i, Reservation r) {
        for (int j = 0; j < this.plane.getExecCols(); j++) {
            if (execMap[i][j] == null) {
                for (int k = 0; k < r.getNumSeats(); k++) {
                    if (i < this.plane.getExecRows()){
                        execMap[i][j] = String.valueOf(r.getID());
                        i++;
                    } else {
                        i = 0;
                        j++;
                        execMap[i][j] = String.valueOf(r.getID());
                        i++;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String getResMap() {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String map = "   ";
        for (int i = 0; i < this.plane.getCols(); i++) {
            map += String.format("%3d", i + 1);
        }
        map += "\n";
        
        for (int i = 0; i < this.plane.getRows(); i++) {
            map += String.format("%3s", alphabet[i]);
            for (int j = 0; j < this.plane.getExecCols(); j++) {
                if (i >= this.plane.getExecRows()) {
                    map += "   ";
                    continue;
                }
                if (execMap[i][j] == null)
                    map += String.format("%3d", 0);
                else
                    map += String.format("%3s", execMap[i][j]);
            }
            for (int j = 0; j < this.plane.getTourCols(); j++) {
                if (tourMap[i][j] == null)
                    map += String.format("%3d", 0);
                else
                    map += String.format("%3s", tourMap[i][j]);
            }
            map += "\n";
        }
        return map;
    }

    // private int getResID(Seat s) {
    //     for (Map.Entry<Integer, List<Seat>> entry : this.takenSeats.entrySet()) {
    //         for (Seat seat : entry.getValue()) {
    //             if (seat.equals(s))
    //                 return entry.getKey();
    //         }
    //     }
    //     return -1;
    // }

    @Override
    public String toString() {
        String prefix = "Código do voo " + this.code + ". Lugares disponíveis: ";
        if (this.plane.getExecSeats() == 0)
            return prefix + this.plane.getTourSeats() + " lugares em classe Turística.\nClasse executiva não disponível neste voo.";
        return prefix + this.plane.getExecSeats() + " lugares em classe Executiva; " + this.plane.getTourSeats() + " lugares em classe Turística.";
    }
}
