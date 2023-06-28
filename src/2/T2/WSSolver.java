package lab01;

import java.io.*;
import java.util.*;


public class WSSolver {
    public static void main(String[] args) throws IOException{ // String[] args contem um array com os argumentos introduzidos na linha de comando/terminal

        // inicializacao de variaveis
        Scanner file = null; 
        int size_sopa = 0; // conta o numero de linhas da sopa de letras
        int size_1st_line = 0; // guarda o tamanho da primeira linha
        char[] char_sopa_lines;
        ArrayList<Character> sopa_lines = new ArrayList<Character>(); // array das linhas da sopa de letras, que tem de ser constituidas so por maiusculas
        ArrayList<ArrayList<Character>> sopa_letras = new ArrayList<ArrayList<Character>>(); // array bidimensional para guardar a sopa de letras
        ArrayList<String> words_lines = new ArrayList<String>(); // array das linhas das palavras a encontrar, que tem de ser constituidas por minusculas ou misturadas
        ArrayList<String> find_words = new ArrayList<String>(); // array de palavras a encontrar
        FileWriter file_solucao = null; //ficheiro onde vai ser escrito o resultado

        // leitura do ficheiro introduzido como argumento no terminal linux
        try {
            if(args.length == 1)
            {
                file = new Scanner(new File(args[0]));// args[0] é o nome do ficheiro txt que contém a sopa de letras e as palavras a encontrar
                file_solucao = new FileWriter(String.format("%s_results.txt", args[0].substring(0,args[0].length()-4)));
            }
            else{
                System.out.println("Incorrect number of arguments.");
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("An error reading the file occurred.");
            e.printStackTrace();
        }

        // leitura das linhas do ficheiro e adicionadas aos arrays especificos
        while (file.hasNextLine()) { // ler linhas
            String line = file.next();
            if(line != null) {
                if(line.equals(line.toUpperCase())){
                    size_sopa++;

                    if(size_sopa == 1){
                        size_1st_line = line.length();
                    }
                    // validação do tamanho da sopa de letras
                    if(line.length() != size_1st_line || line.length() > 40) {
                        System.out.println("O puzzle tem um tamanho superior a 40 linhas e/ou as linhas não têm o memso tamanho entre elas!");
                    } else {

                        // passar o puzzle para o array bidimencional sopa_letras
                        char_sopa_lines = line.toCharArray();
                        sopa_lines = new ArrayList<Character>(); // array das linhas da sopa de letras, que tem de ser constituidas so por maiusculas

                        for(char character: char_sopa_lines){
                            sopa_lines.add(character);
                        }

                        // System.out.printf("here %s\n", sopa_lines);
                        sopa_letras.add(sopa_lines);
                        }
                } else {
                    words_lines.add(line);
                }
            }
        }

        // System.out.println(sopa_letras);
        file.close();

        validate(find_words, words_lines,size_1st_line,size_sopa);

        char[][] sopa_solucao = createTabela(sopa_lines);

        solve(file_solucao,sopa_letras, find_words, size_sopa,sopa_solucao);

        printTabela(sopa_solucao, file_solucao);
        
    }

    static void validate(ArrayList<String> find_words, ArrayList<String> words_lines, int size_1st_line, int size_sopa){

        // validação se o puzzle é quadrado
        if (size_sopa != size_1st_line){
            System.out.println("O puzzle não é quadrado!");
            System.exit(0);
        }

        // split das palavras do array words_lines para ter uma lista das palavras a encontrar
        for(String l : words_lines){
            String[] words = l.split("\\W");
            for(String word : words){
                if(word.matches("[a-zA-Z]+")){ //garantir que a palavra a procurar só tem letras
                    if(word.length() >= 3){ // tem de ter pelo menos 3 caracteres
                        find_words.add(word);
                    }
                }
            }
        }

        // se existir uma palavra contida noutra retorna a maior
        for(int i = 0; i < find_words.size(); i++){
            for(int j = 0; j < find_words.size(); j++){
                if(find_words.get(i) != find_words.get(j) && find_words.get(i).contains(find_words.get(j))){
                    // System.out.printf("%s contem %s \n", find_words.get(i), find_words.get(j));
                    find_words.remove(find_words.get(j));
                }
            }
        }
    }

    static void solve(FileWriter file_solucao,ArrayList<ArrayList<Character>> sopa_letras, ArrayList<String> find_words, int size_sopa, char[][] sopa_solucao){ // resolve a sopa de letras para cada palavra

        for(String word : find_words){ // percorrer a lista de palavras a encontrar
            find_word_WSsolver(file_solucao,sopa_letras, word.toUpperCase(), size_sopa, sopa_solucao);
        }
    }

    static void find_word_WSsolver(FileWriter file_solucao,ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, char[][] sopa_solucao){ // econtra a palavra na sopa de letras

        // percorrer a sopa de letras
        for(int l = 0; l < sopa_letras.size(); l++){ // l representa as linhas da sopa de letras
            for(int c = 0; c < sopa_letras.size(); c++){ // c representa as colunas da sopa de letras
                if(sopa_letras.get(l).get(c) == word.charAt(0)){ // (l,c) -> coordenadas da primeira letra de cada palavra

                    // testar para todas as direcoes caso haja espaço
                    coordenadas coord_inicial = new coordenadas(l+1, c+1); // definir as coordenadas iniciais de ca palavra -> l+1 e c+1 porque l e c comecam em 0
                    
                    // Up
                    if(checkUp(l, word)){
                        searchUp(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }
                    // Down
                    if(checkDown(l, word, size_sopa)){
                        searchDown(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao); 
                    }

                    // Left
                    if(checkLeft(c, word)){
                        searchLeft(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }

                    // Right
                    if(checkRight(c, word, size_sopa)){
                        // System.out.printf("sou a palavra %s e estou aqui! \n", word);
                        searchRight(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }

                    // UpLeft
                    if(checkUpLeft(l, c, word)){
                        searchUpLeft(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }

                    // UpRight
                    if(checkUpRight(l, c, word, size_sopa)){
                        searchUpRight(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }

                    // DownLeft
                    if(checkDownLeft(l, c, word, size_sopa)){
                        searchDownLeft(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }

                    // DownRight
                    if(checkDownRight(l, c, word, size_sopa)){
                        searchDownRight(file_solucao,l, c, sopa_letras, word, size_sopa, coord_inicial, sopa_solucao);
                    }
                    
                }
            }
        }
    }

    // funcoes de check da direcao das palavras
    static boolean checkUp(int linha, String word){
        if((linha + 1) - word.length() < 0){ // (linha + 1) porque comeca no indice 0
            return false;
        }

        return true;
    }
 
    static boolean checkDown(int linha, String word, int size_sopa){
        if(linha + word.length() > size_sopa){
            return false;
        }

        return true;
    }

    static boolean checkLeft(int coluna, String word){
        if((coluna + 1) - word.length() < 0){
            return false;
        }
        return true;
    }

    static boolean checkRight(int coluna, String word, int size_sopa){
        if(coluna + word.length() > size_sopa){ // (coluna - 1) porque comeca no indice 0
            return false;
        }

        return true;
    }

    // diagonais
    static boolean checkUpLeft(int linha, int coluna, String word){
        if(linha - word.length() < 0 || (coluna + 1) - word.length() < 0){
            return false;
        }

        return true;
    }

    static boolean checkUpRight(int linha, int coluna, String word, int size_sopa){
        if((linha + 1) - word.length() < 0 || coluna + word.length() > size_sopa){
            return false;
        }

        return true;
    }

    static boolean checkDownLeft(int linha, int coluna, String word, int size_sopa){
        if(linha + word.length() > size_sopa || (coluna + 1) - word.length() < 0){
            return false;
        }

        return true;
    }

    static boolean checkDownRight(int linha, int coluna, String word, int size_sopa){
        if(linha + word.length() > size_sopa || coluna + word.length() > size_sopa){
            return false;
        }

        return true;
    }

    // procurar a palavra toda na sopa de letras
    // estas funcoes de search não so procuram a palavra como, caso a encontrem, escrevem-na na tabela que sera mostrada ao utilizador

    private static void searchUp(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha - i).get(coluna) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha - i).get(coluna) == letter){ // ultima letra da palavra
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha - j][coluna] = sopa_letras.get(linha - j).get(coluna); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.Up, sopa_solucao);
            } else {
                break;
            }
        }
    }

    private static void searchDown(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha + i).get(coluna) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha + i).get(coluna) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha + j][coluna] = sopa_letras.get(linha + j).get(coluna); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.Down, sopa_solucao);
            } else {
                break;
            }
        }
    }

    private static void searchLeft(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha).get(coluna - i) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha).get(coluna - i) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha][coluna - j] = sopa_letras.get(linha).get(coluna - j); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.Left, sopa_solucao);
            } else {
                break;
            }
        }
    }

    private static void searchRight(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha).get(coluna + i) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha).get(coluna + i) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha][coluna + j] = sopa_letras.get(linha).get(coluna + j); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.Right, sopa_solucao);
            } else {
                break;
            }
        }
    }

    // diagonais
    private static void searchUpLeft(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha - i).get(coluna - i) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha - i).get(coluna - i) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha - j][coluna - j] = sopa_letras.get(linha - j).get(coluna - j); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.UpLeft, sopa_solucao);
            } else {
                break;
            }
        }
    }

    private static void searchUpRight(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha - i).get(coluna + i) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha - i).get(coluna + i) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha - j][coluna + j] = sopa_letras.get(linha - j).get(coluna + j); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.UpRight, sopa_solucao);
            } else {
                break;
            }
        }
    }

    private static void searchDownLeft(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha + i).get(coluna - i) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha + i).get(coluna - i) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha + j][coluna - j] = sopa_letras.get(linha + j).get(coluna - j); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.DownLeft, sopa_solucao);
            } else {
                break;
            }
        }
    }

    private static void searchDownRight(FileWriter file_solucao,int linha, int coluna, ArrayList<ArrayList<Character>> sopa_letras, String word, int size_sopa, coordenadas coord_inicial,  char[][] sopa_solucao){
        int i = 0;

        for (char letter : word.toCharArray() ){
            if(sopa_letras.get(linha + i).get(coluna + i) == letter && i != word.length() - 1){
                i++;
                continue;
            } else if (sopa_letras.get(linha + i).get(coluna + i) == letter){
                // a palavra foi encontrada
                for(int j = 0; j < word.length(); j++) {
                    sopa_solucao[linha + j][coluna + j] = sopa_letras.get(linha + j).get(coluna + j); // escrever a palavra na solucao da sopa de letras
                }
                printSolucao(file_solucao,word, coord_inicial, direction.DownRight, sopa_solucao);
            } else {
                break;
            }
        } 
    }


    // print da solucao
    static void printSolucao(FileWriter file_solucao,String word, coordenadas coord_inicial, direction direcao, char[][] sopa_solucao){
    
        try {
            //escrever a sopa de letras
            file_solucao.write(String.format("%-15s %-6d %-8s %-9s \n", word.toLowerCase(), word.length(), coord_inicial, direcao));
        } catch (IOException e) {
            System.out.print("Error writing solution in file");
            e.printStackTrace();
        }
    }

    static char[][] createTabela(ArrayList<Character> sopa_letras){
        char[][]sopa_solucao = new char [sopa_letras.size()][sopa_letras.size()]; // array bidimensional apresentado no fim com a solucao da sopa de letras
        for(int l = 0; l < sopa_solucao.length; l++){
            for(int c = 0; c < sopa_solucao.length; c++){
                sopa_solucao[l][c] = '.';
                // System.out.print(String.format("%c", sopa_solucao[l][c]));
            }
            // System.out.println();
        }
        return sopa_solucao;
    }

    // cria a tabela da solucao
    static void printTabela(char[][]sopa_solucao, FileWriter file_solucao){

        try {
            //escrever a sopa de letras
            file_solucao.write("\n");
            for(int l = 0; l < sopa_solucao.length; l++){
                for(int c = 0; c < sopa_solucao.length; c++){
                    file_solucao.write(String.format(" %c ", sopa_solucao[l][c]));
                }
                file_solucao.write("\n");
            }
            file_solucao.close();

        } catch (IOException e) {
            System.out.print("Error writing solution in file");
            e.printStackTrace();
        }
    }
}
