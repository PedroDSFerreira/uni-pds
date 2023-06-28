package lab01;

public class Coordenadas {
    private int x;
    private int y;
    private String direcao;
    public Coordenadas(int y, int x,String direcao){
        this.y = y;
        this.x = x;
        this.direcao = direcao;

    }
    @Override
    public String toString() {
        // return this.y + "," + this.x + " " + this.direcao;
        return this.y + "," + this.x;
        
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public  String getDirecao() {
        return direcao;
    }
    
}
