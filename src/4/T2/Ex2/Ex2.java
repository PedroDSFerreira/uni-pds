package lab04.T2.Ex2;

import java.util.Scanner;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Ex2 {
    public static void main(String[] args){
        Map<String, Voo> voos = new HashMap<>();
        String line, op, codigoVoo;
        boolean loop = true;
        Scanner input;
        Voo voo;
        long lines = 0;
        boolean readFromStdin = true;

        if(args.length == 1){
            try{
                File file = new File(args[0]);
                input = new Scanner(file);
                Path p1 = Paths.get(args[0]);
                lines = Files.lines(p1).count();
                readFromStdin = false;
            }catch(IOException e){
                System.out.println("Erro ao ler o ficheiro de comandos.");
                e.printStackTrace();
                return;
            }
        }else{
            input = new Scanner(System.in);
        }

        while(loop){
            if(readFromStdin)
                System.out.println("Escolha uma opcao: (H para ajuda)");

            line = input.nextLine();
            if(lines == 1){
                input.close();
                input = new Scanner(System.in);
                readFromStdin = true;
            }
            String[] lineSplit = line.split(" ");

            if(!checkOption(lineSplit)) {
                System.out.println("\nComando invalido!\n");
                continue;
            }

            op = lineSplit[0];
            switch (op) {
                case "H":
                    menu();
                    break;
                case "I":
                    String filename = lineSplit[1];
                    voo = readFlightInfo(filename);
                    voos.put(voo.getCode(), voo);
                    break;
                case "M":
                    codigoVoo = lineSplit[1];
                    System.out.println(voos.get(codigoVoo).getLayout());
                    break;
                case "F":
                    String vooInfo = line.substring(2);
                    voo = createVoo(vooInfo);
                    voos.put(voo.getCode(), voo);
                    break;
                case "R":
                    String code = lineSplit[1];
                    char clas = lineSplit[2].charAt(0);
                    int number_seats = Integer.parseInt(lineSplit[3]);
                    List<String> reslist = voos.get(code).setReservation(clas, number_seats);
                    StringBuilder strprint = new StringBuilder();
                    strprint.append(String.format("%s:%s = %s", code,reslist.get(0),reslist.get(1)));
                    for(int i = 2; i < reslist.size(); i++){
                        strprint.append(String.format(" | %s", reslist.get(i)));
                    }
                    System.out.println(strprint.toString());
                    break;
                case "C":
                    String[] resCode = lineSplit[1].split(":");
                    voos.get(resCode[0]).removeReservation(Integer.parseInt(resCode[1]));
                    break;
                case "Q":
                    loop = false;
                    break;
                default:
                    System.out.println("\nComando invalido!\n");
                    break;
            }
            lines--;
        }

        input.close();
    }

    private static boolean checkOption(String[] lineSplit){
        String op = lineSplit[0];
        if( (op.equals("I") || op.equals("M") || op.equals("C") ) && lineSplit.length != 2) 
            return false;
        else if((op.equals("R") || op.equals("F"))  && lineSplit.length !=4) 
            return false;
        return true;
    }

    private static void menu(){
        System.out.println("\n--------- MENU -----------\nH - Apresenta as opcoes do menu\nI filename - Le informacao sobre voo\nM flight_code - Exibe o mapa de reservas de um voo\nF flight_code num_seats_executive num_seats_tourist - Acrescenta um novo voo\nR flight_code class number_seats - Acrescenta uma nova reserva a um voo\nC reservation_code - Cancela uma reserva\nQ - Termina o programa\n");
    }

    private static Voo readFlightInfo(String filename){
        String line;
        String[] lineSplit;
        Path fileIn = Paths.get(filename);

        try(BufferedReader bufferedReader = Files.newBufferedReader(fileIn)){
            line = bufferedReader.readLine();
            Voo voo = createVoo(line);
            
            // adicionar reservas ao voo
            while((line = bufferedReader.readLine()) != null){
                lineSplit = line.split(" ");
                    char classe = lineSplit[0].charAt(0);
                    int numReservas = Integer.parseInt(lineSplit[1]);
                    voo.setReservation(classe, numReservas);
            }

            return voo;
        }
        catch(IOException ex){
            System.out.println("Erro ao ler o ficheiro.");
            ex.printStackTrace();
            return null;
        }
        
    }

    private static Voo createVoo(String line){
        String[] lineSplit;
        lineSplit = line.split(" ");
    
        if(lineSplit.length >= 2){
            String codigoVoo; 
            if(lineSplit[0].charAt(0) == '>') codigoVoo = lineSplit[0].substring(1);
            else codigoVoo = lineSplit[0];
            Voo voo = null;
    
            if(lineSplit.length == 2){
                int numFilasTuristica = Integer.parseInt(lineSplit[1].split("x")[0]);
                int numLugaresTuristica = Integer.parseInt(lineSplit[1].split("x")[1]);
                voo = new Voo(codigoVoo, numLugaresTuristica, numFilasTuristica);
            }
            else{
                int numFilasExecutiva = Integer.parseInt(lineSplit[1].split("x")[0]);
                int numLugaresExecutiva = Integer.parseInt(lineSplit[1].split("x")[1]);
                int numFilasTuristica = Integer.parseInt(lineSplit[2].split("x")[0]);
                int numLugaresTuristica = Integer.parseInt(lineSplit[2].split("x")[1]);
                voo = new Voo(codigoVoo, numLugaresExecutiva, numFilasExecutiva, numLugaresTuristica, numFilasTuristica);
            }
            return voo;
        }
        else{
            System.out.println("Ficheiro com formato invalido! Falta codigo de voo.");
            return null;
        }
    }

}


