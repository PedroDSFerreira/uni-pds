package lab01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Checkers {
    public static HashMap<String, List<Coordenadas>> checkRight(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&& (x + word.length() <=  map.size() +1)) { 
                    foundWord = firstLetter;
                    aux = x;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux + 1 <= map.size()){
                            aux++;
                        }
                        if(map.get(y).get(aux).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"Right");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }

    public static HashMap<String, List<Coordenadas>> checkLeft(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&& (x - word.length() >= 0)) { 
                    foundWord = firstLetter;
                    aux = x;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux - 1 >= 1){
                            aux--;
                        }
                        if(map.get(y).get(aux).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"Left");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }
    public static HashMap<String, List<Coordenadas>> checkUp(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&& (y - word.length() >= 0)) { 
                    foundWord = firstLetter;
                    aux = y;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux - 1 >= 0){
                            aux--;
                        }
                        if(map.get(aux).get(x).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"Up");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }

    public static HashMap<String, List<Coordenadas>> checkDown(HashMap<Integer, HashMap<Integer, String>> map, String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&&(y + word.length() <= map.size() +1)) { 
                    foundWord = firstLetter;
                    aux = y;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux + 1 <= map.size()){
                            aux++;
                        }
                        if(map.get(aux).get(x).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"Down");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }
    public static HashMap<String, List<Coordenadas>> checkUpRight(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux,aux1;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&&(x + word.length() <= map.size() +1 )&&(y - word.length() >= 0)) { 
                    foundWord = firstLetter;
                    aux = y;
                    aux1 = x;
                    // System.out.println(word + " " + y + " " + x + " " +aux);
                    for(char c : word.substring(1).toCharArray()){
                        
                        if(aux - 1 >= 0 && aux1 + 1 <= map.size()){//Corrigir aqui
                            aux--;
                            aux1++;
                        }
                        if(map.get(aux).get(aux1).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"UpRight");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }
    public static HashMap<String, List<Coordenadas>> checkUpLeft(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux,aux1;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&&(x - word.length() >= 0)&&(y - word.length() >= 0)) { 
                    foundWord = firstLetter;
                    aux = y;
                    aux1 = x;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux - 1 >= 0 && aux1 - 1 >= 1){
                            aux--;
                            aux1--;
                        }
                        if(map.get(aux).get(aux1).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"UpLeft");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }
    public static HashMap<String, List<Coordenadas>> checkDownRight(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux,aux1;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&&(y + word.length() <= map.size() +1)&& (x + word.length() <= map.size() +1)) { 
                    foundWord = firstLetter;
                    aux = y;
                    aux1 = x;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux + 1 <= map.size() && aux1 + 1 <= map.size()){
                            aux++;
                            aux1++;
                        }
                        if(map.get(aux).get(aux1).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"DownRight");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }
    public static HashMap<String, List<Coordenadas>> checkDownLeft(HashMap<Integer, HashMap<Integer, String>> map,String word) {
        HashMap<String,List<Coordenadas>> foundwords = new HashMap<>();
        List<Coordenadas> coordenadas = new ArrayList<>();
        String foundWord;
        String firstLetter = String.valueOf(word.charAt(0)).toUpperCase();
        int aux,aux1;
        for (int y = 1 ; y <= map.size() ; y++) {
            for(int x = 1 ; x <= map.size() ; x++){
                if (map.get(y).get(x).equals(firstLetter)&&(y + word.length() <= map.size() +1)&&(x - word.length() >= 0)) { 
                    foundWord = firstLetter;
                    aux = y;
                    aux1 = x;
                    for(char c : word.substring(1).toCharArray()){
                        if(aux + 1 <= map.size() && aux1 - 1 >= 1){
                            aux++;
                            aux1--;
                        }
                        if(map.get(aux).get(aux1).equals(String.valueOf(c).toUpperCase())){
                            foundWord += String.valueOf(c).toUpperCase();
                        }
                        if(foundWord.equals(word.toUpperCase())){
                            Coordenadas coord = new Coordenadas(y,x,"DownLeft");
                            coordenadas.add(coord);
                            foundwords.put(foundWord,coordenadas);
                        }
                        
                    }
                }
            }

        }
        
        return foundwords;
    }
}
