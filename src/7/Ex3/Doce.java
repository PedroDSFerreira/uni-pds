package lab07.Ex3;

public class Doce extends Entidade {
    private String name;
    private double weight;

    public Doce(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public void draw() {
        System.out.println(indent.toString() + "Doce '" + name + "' - Weight : " + weight);
    }

    public double getWeight() {
        return weight;
    }
}
