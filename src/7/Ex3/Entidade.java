package lab07.Ex3;

abstract class Entidade {
    public static StringBuffer indent = new StringBuffer();
    public abstract void draw();
    public abstract double getWeight();
}