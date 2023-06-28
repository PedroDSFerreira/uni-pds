package lab01;

import java.io.*;
import java.util.*;


public class WSGenerator {

    public static void main(String[] args) {

        //inicialização 
        int size_sopa = 0;
        int lower = 0; //vaor mínimo para gerar uma coordenada
        BufferedReader file_reader = null;
        String output_line = null;
        FileWriter file_solucao = null; //ficheiro onde vai ser escrito o resultado caso o utilizador assim o queira
        boolean write_in_file = false; // variável booleana que define se o utilizador quer ou não escrever o resultado num ficheiro
        coordenadas word_coordenadas = new coordenadas(0,0);
        ArrayList<String> words_lines = new ArrayList<String>(); // array das linhas das palavras a encontrar, que tem de ser constituidas por minusculas ou misturadas
        ArrayList<String> find_words = new ArrayList<String>(); // array de palavras a encontrar
        ArrayList<String> placed_words = new ArrayList<String>(); //array de palavras já introduzidas na sopa
        ArrayList<direction> used_directions = new ArrayList<direction>();

        //tratamento das opções
        for (int i = 0; i < args.length; i++){
            if(args[i].startsWith("-")){
                switch (args[i]) {

                //ficheiro a ler
                    case "-i":

                        try {
                            Scanner file = new Scanner(new File(args[i+1]));

                            while (file.hasNextLine()) { // ler linhas
                                String line = file.next();
                                words_lines.add(line);
                            }

                            file_reader = new BufferedReader(new FileReader(args[i+1]));
            
                        } catch (IOException e) {
                            System.out.println("An error reading the file occurred.");
                            e.printStackTrace();
                        }
                            
                        break;


                    //dimensão da sopa de letras a criar
                    case "-s":

                        size_sopa = Integer.parseInt(args[i+1]); // dimensão da sopa de letras

                        if(size_sopa <= 0 || size_sopa > 40){
                            System.out.println("A dimensão da sopa de letras pode ser de, no máximo, 40");
                            System.exit(0);
                        }

                        break;
                
                    //ficheiro onde escrever a sopa de letras
                    case "-o":

                        try {
                            file_solucao = new FileWriter(args[i+1]);
                            write_in_file=true; //ao passar esta opção, o utilizador quer que o resultado seja escrito num ficheiro

                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }

                        break;

                    default:
                        System.out.println("Use the available options: ");
                        System.out.println("-i      File to read");
                        System.out.println("-s      Soup dimension");
                        System.out.println("-o      File where to write solution");
                        break;
                }
            }
        }

        char[][] sopa_solucao = new char [size_sopa][size_sopa]; // array bidimensional para a criação da sopa de letras

        for(int l = 0; l < size_sopa; l++){
            for(int c = 0; c < size_sopa; c++){
                sopa_solucao[l][c] = '.'; //prencher o array bidimensional com pontinhos para facilitar a sua manipulação
            }
        }

        validate(words_lines, find_words,size_sopa);

        for(String word : find_words){
            PosicaoDirecao(word,find_words, used_directions, word_coordenadas, sopa_solucao, size_sopa, lower, placed_words); //coordenadas e direcao finais e válidas da palavra
            used_directions.removeAll(used_directions); //remover todos os items da lista de direções usadas para repetir o processo da função chamada acima com as outras palavras sem problemas
        }

        completeSoup(sopa_solucao, size_sopa);
        printTabela(sopa_solucao, file_solucao,write_in_file,file_reader,output_line);
 
    }


    static void PosicaoDirecao(String word, ArrayList<String> find_words, ArrayList<direction> used_directions, coordenadas word_coordenadas, char[][] sopa_solucao, int size_sopa, int lower, ArrayList<String> placed_words){

            for (direction direcao : direction.values()){
                used_directions.add(direcao); //adicionar as direções contidas em direction.java a um array para mais fácil manipulação
            }
            
            word_coordenadas = randomPosicao(sopa_solucao, size_sopa, lower); //gerar coordenadas para a palavra
            //System.out.printf("%5s: %5s\n", word,word_coordenadas);

            while (!placed_words.contains(word)){

                Random random = new Random();
                direction random_direcao = used_directions.get(random.nextInt(used_directions.size()));; //random direcao de entre o novo array de direções criado
                //System.out.printf("%s : direcao %s\n", word, random_direcao);
                randomDirecao(random_direcao, word_coordenadas, word, size_sopa, sopa_solucao, placed_words); //verificar se a direção gerada randomly é válida
                used_directions.remove(random_direcao); //remover a direção gerada do array
                //System.out.println(used_directions);
                //System.out.println(placed_words);

                if(used_directions.isEmpty()){ //casa o array de direções fique vazio significa que, para as coordenadas geradas, nenhuma direção é possível, logo temos de chamar novamente a função para serem criadas novas coordenadas e posições poara as mesmas
                    PosicaoDirecao(word, find_words, used_directions, word_coordenadas, sopa_solucao, size_sopa, lower, placed_words);
                }
            }
            
        }


    static void validate(ArrayList<String> words_lines, ArrayList<String> find_words, int size_sopa){

        // split das palavras do array words_lines para ter uma lista das palavras a encontrar
        for(String l : words_lines){
            String[] words = l.split("\\W");
            for(String word : words){
                if(word.length() >= 3){ //validação de que a palavra tem de ter pelo menos 3 caracteres
                    find_words.add(word.toUpperCase());
                }
                if(word.length() > size_sopa){ //caso a dimensão dada para a sopa seja menor que o tamanho de uma ou mais palavras a encontrar
                    System.out.print("Dimensão da sopa inválida! Há palavras no ficheiro de palavras a procurar com tamanho maior!\n");
                    System.exit(0);
                };
            }
        }

        //validação de que se existir uma palavra contida noutra retorna a maior
        for(int i = 0; i < find_words.size(); i++){
            for(int j = 0; j < find_words.size(); j++){
                if(find_words.get(i) != find_words.get(j) && find_words.get(i).contains(find_words.get(j))){
                    find_words.remove(find_words.get(j));
                }
            }
        }
        //System.out.println(find_words);
    }

    //para cada palavra a colocar na sopa vai atribuir uma coordenada random para a primeira letra
    static coordenadas randomPosicao(char[][] sopa_solucao, int size_sopa, int lower){

        int c = (int) (Math.random() * (size_sopa - lower)) + lower;
        int l = (int) (Math.random() * (size_sopa - lower)) + lower;

        coordenadas random_coordenadas = new coordenadas(l, c);

        return random_coordenadas;

    }

    //para cada palavra a colocar na sopa vai criar uma atribuir uma direção random mas válida
    static void randomDirecao(direction random_direcao, coordenadas random_coordenadas,String word, int size_sopa, char[][] sopa_solucao, ArrayList<String> placed_words){

        boolean validDirection = true;

        switch (random_direcao){

            case Up:
                try {
                    for (int i = 0; i < word.length(); i++){
                        //se as coordenadas que seguem esta direção, a partir das coordenadas random atribuídas, estiverem ocupadas com um caracter que não é o caracter
                        //correspondente àquele índice na palavra então significa que a palavra não pode seguir esta direção
                        if((sopa_solucao[random_coordenadas.l - i][random_coordenadas.c] != '.' && sopa_solucao[random_coordenadas.l - i][random_coordenadas.c] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false; //esta direção não é válida, temos de tentar outra, ou outras coordenadas
                        }
                    }
                    if(validDirection){ //se a palavra passou o teste anterior então esta direção é válida e podemos preencher as coordenadas com a palavra
                        for (int l = 0; l < word.length(); l++){
                            sopa_solucao[random_coordenadas.l - l][random_coordenadas.c] = word.charAt(l);
                        }
                        placed_words.add(word);//como direção for válida, adicionamos a palavra ao array placed_words para que, caso uma palavra não esteja lá, possamos procurar uma nova direção válida para a mesma
                    }
    
                    break;
                    
                } catch (ArrayIndexOutOfBoundsException e) { //caso a direção exceda os limites da sopa de letras
                    break;            
                }

            case Down:
                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l + i][random_coordenadas.c] != '.' && sopa_solucao[random_coordenadas.l + i][random_coordenadas.c] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;
                        }
                    }
                    if(validDirection){
                        for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l + l][random_coordenadas.c] = word.charAt(l);
                        }
                        placed_words.add(word);
                    }
                    

                    break;
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }

            case Left:
                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l][random_coordenadas.c - i] != '.' && sopa_solucao[random_coordenadas.l][random_coordenadas.c - i] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;
                        }
                    }
                    if(validDirection){for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l][random_coordenadas.c - l] = word.charAt(l);
                    }
                    placed_words.add(word);}
                    
    
                    break;
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }

            case Right:
                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l][random_coordenadas.c + i] != '.' && sopa_solucao[random_coordenadas.l][random_coordenadas.c + i] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;}
                    }
                    if(validDirection){
                    for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l][random_coordenadas.c + l] = word.charAt(l);
                    }
                    placed_words.add(word);  
                    }
                    
    
                    break;
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }

            case UpLeft:
                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l - i][random_coordenadas.c - i] != '.' && sopa_solucao[random_coordenadas.l - i][random_coordenadas.c - i] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;
                        }
                    }
                    if(validDirection){for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l - l][random_coordenadas.c - l] = word.charAt(l);
                    }
                    placed_words.add(word);}
                    
    
                    break;
    
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }

            case UpRight:

                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l - i][random_coordenadas.c + i] != '.' && sopa_solucao[random_coordenadas.l - i][random_coordenadas.c + i] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;
                        }
                    }
                    if(validDirection){for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l - l][random_coordenadas.c + l] = word.charAt(l);
                    }
                    placed_words.add(word);}
                    
    
                    break;
                
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }

            case DownLeft:
                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l + i][random_coordenadas.c - i] != '.' && sopa_solucao[random_coordenadas.l + i][random_coordenadas.c - i] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;}
                    }
                    if(validDirection){for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l + l][random_coordenadas.c - l] = word.charAt(l);
                    }
                    placed_words.add(word);}
                    
    
                    break;
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }

            case DownRight:
                try {
                    for (int i = 0; i < word.length(); i++){
                        if((sopa_solucao[random_coordenadas.l + i][random_coordenadas.c + i] != '.' && sopa_solucao[random_coordenadas.l + i][random_coordenadas.c + i] != word.charAt(i)) || ( random_coordenadas.l + word.length() > size_sopa || random_coordenadas.c + word.length() > size_sopa)){
                            validDirection = false;
                        }
                    }
                    if(validDirection){
                      for (int l = 0; l < word.length(); l++){
                        sopa_solucao[random_coordenadas.l + l][random_coordenadas.c + l] = word.charAt(l);
                    }
                    placed_words.add(word);  
                    }
                    
    
                    break;
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;            
                }
        
            default:
                break;
            }
    }
    
    //completar os espaços restantes da sopa, onde ainda não há nenhuma letra de palavra a encontrar, com letras random
    static void completeSoup (char[][] sopa_solucao, int size_sopa){

        for(int l = 0; l < size_sopa; l++){
            for(int c = 0; c < size_sopa; c++){
                if(sopa_solucao[l][c] == '.'){ //cada espaço da tabela que não está preenchido por nenhuma letra
                    Random letra = new Random();
                    char character = (char)(letra.nextInt(26) + 'A'); //gerar uma letra random para esse espaço de forma a completar a sopa
                    sopa_solucao[l][c] = character;
                    //System.out.println(character);
                }
            }
        }
    }

    // print da tabela da solucao
    static void printTabela( char[][] sopa_solucao, FileWriter file_solucao, boolean write_in_file, BufferedReader file_reader, String output_line){

        if(write_in_file){ //caso o utilizador queira o resultado escrito no ficheiro
            try {
                //escrever a sopa de letras
                for(int l = 0; l < sopa_solucao.length; l++){
                    for(int c = 0; c < sopa_solucao.length; c++){
                        file_solucao.write(String.format("%c", sopa_solucao[l][c]));
                    }
                    file_solucao.write("\n");
                }

                //escrever as palavras a encontrar
                output_line = file_reader.readLine(); //ler cada linha do ficheiro dado com as palavras a encontrar
                while (output_line != null) {
                    file_solucao.write(output_line); //escrever as linhas do ficheiro dado, ou seja, as palavras a encontrar, de igual forma à que está nesse ficheiro
                    file_solucao.write("\n");
                    output_line = file_reader.readLine();  //avançar na linha
                } 

                file_solucao.close();

            } catch (IOException e) {
                System.out.print("Error writing solution in file");
                e.printStackTrace();
            }

        } else{ //caso o utilizador queira o resultado printado no terminal

            //printar a sopa de letras
            for(int l = 0; l < sopa_solucao.length; l++){
                for(int c = 0; c < sopa_solucao.length; c++){
                        System.out.print(String.format("%c", sopa_solucao[l][c]));
                }
                System.out.println();
            }

            //printar as palavras a encontrar
            try {
                output_line = file_reader.readLine();
                while (output_line != null) {
                    System.out.println(output_line);
                    output_line = file_reader.readLine();  
                } 
            } catch (IOException e) {
                System.out.print("Error writing solution in file");
                e.printStackTrace();
            }
        }
    }
}
