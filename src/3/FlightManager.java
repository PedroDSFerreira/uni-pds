package lab03;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class FlightManager {
    public static void main(String[] args) throws IOException { 
        ArrayList<Flight> flights = new ArrayList<Flight>();
        String opt = " ";
        Scanner sc = new Scanner(System.in);
        while(!opt.equals("Q")) {
            System.out.println("Escolha uma opção: (H para ajuda)");
            String[] input = sc.nextLine().split(" ");
            opt = input[0];
            switch (opt) {
                case "H":
                    listAllOptions();
                    break;
                case "I":
                    flights.add(parseFile(input[1]));
                    break;
                case "M":
                    reservationsMap(input[1], flights);
                    break;
                case "F":
                    if (input.length == 4)
                        addFlight(input[1], input[2], input[3], flights);
                    else if (input.length == 3)
                        addFlight(input[1], input[2], flights);
                    break;
                case "R":
                    addRes(input[1], input[2], input[3], flights);
                    break;
                case "C":
                    cancelRes(input[1], flights);
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Opção inválida. A letra deve estar em maiúscula.");
                    break;
            }
        }
        sc.close();
    }

    private static void cancelRes(String resCode, ArrayList<Flight> flights) {
        String fCode = resCode.split(":")[0];
        int id = Integer.parseInt(resCode.split(":")[1]);
        Flight f = getFlight(fCode, flights);
        for (Reservation r : f.getReservations()) {
            if (r.getID() == id) {
                if (r.getfClass().equals("E"))
                    f.setTakenExec(f.getTakenExec() - r.getNumSeats());
                else if (r.getfClass().equals("T"))
                    f.setTakenTour(f.getTakenTour() - r.getNumSeats());
                return;
            }
        }
        System.out.println("Reserva não encontrada.");
    }

    private static void addRes(String fCode, String fClass, String numSeats, ArrayList<Flight> flights) {
        Flight f = getFlight(fCode, flights);
        int id = 0;
        try {
            if (f.getNumReservations() > 0)
                id = f.getNumReservations();
        } catch (Exception e) {
            
        }
        Reservation r = new Reservation(fClass, Integer.parseInt(numSeats), id);
        if (!f.addReservation(r))
            System.out.println("Não foi possível adicionar a reserva.");
        else {
            System.out.println(fCode + ":" + r.getNumSeats() + " = ");
        }
    }

    private static void addFlight(String fCode, String numExec, String numTour, ArrayList<Flight> flights) {
        int c1 = Integer.parseInt(numExec.split("x")[0]);
        int r1 = Integer.parseInt(numExec.split("x")[1]);
        int c2 = Integer.parseInt(numTour.split("x")[0]);
        int r2 = Integer.parseInt(numTour.split("x")[1]);
        Flight f = new Flight(fCode, c1, r1, c2, r2);
        flights.add(f);
        System.out.println(f);
    }

    private static void addFlight(String fCode, String numTour, ArrayList<Flight> flights) {
        int c2 = Integer.parseInt(numTour.split("x")[0]);
        int r2 = Integer.parseInt(numTour.split("x")[1]);
        Flight f = new Flight(fCode, c2, r2);
        flights.add(f);
        System.out.println(f);
    }

    private static void reservationsMap(String code, ArrayList<Flight> flights) {
        Flight f = getFlight(code, flights);
        System.out.println(f.getResMap());
    }

    private static Flight getFlight(String code, ArrayList<Flight> flights) {
        for (Flight f : flights) {
            if (f.getCode().equals(code))
                return f;
        }
        return null;
    }

    private static Flight parseFile(String fname) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/lab03/" + fname));
        String[] flightInfo = lines.get(0).split(" ");
        String flightCode = "";
        int c1 = Integer.parseInt(flightInfo[1].split("x")[0]);
        int r1 = Integer.parseInt(flightInfo[1].split("x")[1]);
        Flight f = new Flight();

        if (flightInfo[0].startsWith(">"))
            flightCode = flightInfo[0].substring(1);
        if (flightInfo.length == 2) {
            f = new Flight(flightCode, c1, r1);
            System.out.println(f.toString());
        } else if (flightInfo.length == 3) {
            int c2 = Integer.parseInt(flightInfo[2].split("x")[0]);
            int r2 = Integer.parseInt(flightInfo[2].split("x")[1]);
            f = new Flight(flightCode, c1, r1, c2, r2);
            System.out.println(f.toString());
        }

        int seq = 1;
        for (int i = 1; i < lines.size(); i++) {
            String[] reservationInfo = lines.get(i).split(" ");
            String fClass = reservationInfo[0];
            int num_seats = Integer.parseInt(reservationInfo[1]);
            Reservation r = new Reservation(fClass, num_seats, seq);
            if (!f.addReservation(r))
                System.out.println("Não foi possível obter lugares para a reserva: " + r.toString());
            else
                seq++;
        }
        
        return f;
    }

    private static void listAllOptions() {
        System.out.println("I <nome_ficheiro> - Verificar informação do voo");
        System.out.println("M <código_voo> - Mapa das reservas do voo");
        System.out.println("F <código_voo> <num_lugares_executiva> <num_lugares_turistica> - Criar voo");
        System.out.println("R <código_voo> <classe> <num_lugares> - Acrescentar reserva");
        System.out.println("C <código_reserva> - Cancelar reserva");
        System.out.println("Q - Sair");
    }
}
