package lab04.T2.Ex2;

import java.util.List;
import java.util.ArrayList;

public class Voo {
    private final String codigo;
    private int[][] layout;
    private int filasExecutiva;
    private int executiveAvailable;
    private int turisticAvailable;
    private int executiveTotal;
    private int turisticTotal;
    private int reservationNumber = 1;
    
    public Voo(String codigo,int lugaresE,int filasE,int lugaresT,int filasT){
        this.executiveTotal = filasE * lugaresE;
        this.turisticTotal = filasT * lugaresT;
        this.executiveAvailable = this.executiveTotal;
        this.turisticAvailable = this.turisticTotal;
        this.codigo = codigo;
        this.filasExecutiva = filasE;
        int lugaresLayout, filasLayout, lugaresToFill;
        if(lugaresE < lugaresT){
            lugaresLayout = lugaresT;
        }
        else{
            lugaresLayout = lugaresE;
        }
        filasLayout = filasE + filasT;
        this.layout = new int[lugaresLayout][filasLayout];
        for (int i = 0; i < this.layout.length; i++) {
            for (int j = 0; j < this.layout[0].length; j++) {
                this.layout[i][j] = -1;
            }
        }
        for (int j = 0; j < filasLayout; j++) {
            if (j < filasE) lugaresToFill = lugaresE;
            else lugaresToFill = lugaresT;
            for (int i = 0; i < lugaresToFill; i++) {
                this.layout[i][j] = 0;
            }
        }

        if(this.executiveTotal > 0)
            System.out.println("Codigo de voo: " + this.codigo + ". Lugares disponiveis: " + this.executiveTotal + " lugares em classe Executiva; " + this.turisticTotal + " lugares em classe Turistica.");
        else{
            System.out.println("Codigo de voo: " + this.codigo + ". Lugares disponiveis: " + this.turisticTotal + " lugares em classe Turistica.");
            System.out.println("Classe executiva nao disponivel neste voo.");
        }
    }

    public Voo(String codigo,int lugaresT,int filasT){
        this(codigo,0,0,lugaresT,filasT);
    }

    public String getLayout(){
        int letter = 65;
        StringBuilder outString = new StringBuilder();
        outString.append(" ");
        for (int i = 1; i <= this.layout[0].length; i++) {
            outString.append(String.format("%3d", i));
        }
        outString.append("\n");
        for (int i = 0; i < this.layout.length; i++) {
            outString.append((char)letter);
            letter++;
            for (int j = 0; j < this.layout[0].length; j++) {
                if(this.layout[i][j] != -1){
                    outString.append(String.format("%3d", this.layout[i][j]));
                }else{
                    outString.append(String.format("%3c", ' '));
                }

            }
            outString.append("\n");
        }
        return outString.toString();
    }

    public String getCode(){
        return this.codigo;
    }

    public List<String> setReservation(char type,int number_seats){
        List<String> returnList = new ArrayList<>();
        boolean emptyColum;
        int startColum = 0;
        int endColum = this.layout[0].length;
        switch (type) {
            case 'T':
                if(this.turisticAvailable >= number_seats){
                    startColum = filasExecutiva;
                    this.turisticAvailable = this.turisticAvailable - number_seats;
                }
                else{
                    System.out.println("Nao foi possível obter lugares para a reserva: " + type + " " + number_seats);
                    returnList.add("-1");
                    return returnList;
                }
                break;
            case 'E':
                if(this.executiveAvailable >= number_seats){
                    endColum = filasExecutiva;
                    this.executiveAvailable = this.executiveAvailable - number_seats;
                }
                else{
                    System.out.println("Nao foi possivel obter lugares para a reserva: " + type + " " + number_seats);
                    returnList.add("-1");
                    return returnList;
                }
                break;
            default:
                System.out.println("Classe nao existe");
                returnList.add("-1");
                return returnList;
        }

        for(int j = startColum; j < endColum; j++){
            emptyColum = true;
            for(int i = 0; i < this.layout.length; i++){
                if(this.layout[i][j] != 0 && this.layout[i][j] != -1 ){
                    emptyColum = false;
                }
            }
            if(emptyColum){
                returnList.add(Integer.toString(this.reservationNumber));
                putseatsOrder(number_seats, j, startColum, endColum, returnList);
                this.reservationNumber++;
                return returnList;
            }
        }

        for(int j = startColum; j < endColum; j++){
            for (int i = 0; i < layout.length; i++) {
                if(this.layout[i][j] == 0){
                    this.layout[i][j] = this.reservationNumber;
                    returnList.add(positionString(i, j));
                    number_seats--;
                }
                if(number_seats <= 0){
                    this.reservationNumber++;
                    return returnList;
                }
            }
        }
        returnList.add("-1");
        return returnList;

    }

    public void removeReservation(int resNunber){
        for(int j = 0; j < this.layout[0].length; j++){
            for (int i = 0; i < layout.length; i++) {
                if(this.layout[i][j] == resNunber)
                    this.layout[i][j] = 0;
                    if(j < this.filasExecutiva){
                        this.executiveAvailable++;
                    }else{
                        this.turisticAvailable++;
                    }
            }
        }
    }

    private void putseatsOrder(int number_seats,int colum,int startColum,int endColum,List<String> list){
        for(int j = colum; j < endColum; j++){
            for (int i = 0; i < layout.length; i++) {
                if(this.layout[i][j] == 0){
                    this.layout[i][j] = this.reservationNumber;
                    list.add(positionString(i, j));
                    number_seats--;
                }
                if(number_seats <= 0)
                    return;
            }
        }
        if(number_seats > 0){
            for(int j = startColum; j < endColum; j++){
                for (int i = 0; i < layout.length; i++) {
                    if(this.layout[i][j] == 0){
                        this.layout[i][j] = this.reservationNumber;
                        list.add(positionString(i, j));
                        number_seats--;
                    }
                    if(number_seats <= 0)
                        return;
                }
            } 
        }
    }

    private String positionString(int line,int colum){
        String sColum = Integer.toString(colum+1);
        char sLine = (char)(line+65);
        return sColum + sLine;
    }

}
