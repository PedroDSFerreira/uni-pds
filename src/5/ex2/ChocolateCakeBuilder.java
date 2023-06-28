package lab05.ex2;

// Cake: Soft Chocolate
// Cream: Whipped Cream
// Top: Whipped Cream
// Topping: Fruit

public class ChocolateCakeBuilder implements CakeBuilder {
    private Cake cake;
    private String cakeLayer = "Soft Chocolate"; 


    public void setCakeShape(Shape shape) {
        cake.setShape(shape);
    }
    
    public void addCakeLayer() {
        cake.addLayer();
    }

    public void addCreamLayer() {
        cake.setMidLayerCream(Cream.Whipped_Cream);
    }
    
    public void addTopLayer() {
        cake.setTopLayerCream(Cream.Whipped_Cream);
    }

    public void addTopping() {
        cake.setTopping(Topping.Fruit);
    }

    public void addMessage(String m) {
        cake.setMessage(m);
    }

    public void createCake() {
        cake = new Cake(Shape.Round, cakeLayer);
    }

    public Cake getCake() {
        return cake;
    }
}
