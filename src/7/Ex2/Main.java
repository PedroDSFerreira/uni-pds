package lab07.Ex2;

public class Main {
    public static void main(String[] args) {
        ReaderInterface reader;
        String file = "src/lab07/Ex2/data/dummy-file.txt";

        reader = new TextReader(file);
        print(reader);

        reader = new NormalizationFilter(new TextReader(file));
        print(reader);

        reader = new VowelFilter(new TermFilter(new TextReader(file)));
        print(reader);

        reader = new CapitalizationFilter(new VowelFilter(new TextReader(file)));
        print(reader);
    }

    private static void print(ReaderInterface reader) {
        while (reader.hasNext()) {
            System.out.println(reader.next());
        }
        System.out.println();
    }
    
}
