package lab01;

public class coordenadas {
    public int l, c; // l -> linhas e c -> colunas
    public coordenadas (int l, int c){
        this.l = l;
        this.c = c;
    }

    @Override
    public String toString() {
        return l + "," + c;
    }
}
