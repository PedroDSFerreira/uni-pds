package lab05.ex2;

// Cake: Yogurt
// Cream: Vanilla
// Top: Red Berries
// Topping: Chocolate

public class YogurtCakeBuilder implements CakeBuilder {
    private Cake cake;
    private String cakeLayer = "Yogurt"; 


    public void setCakeShape(Shape shape) {
        cake.setShape(shape);
    }
    
    public void addCakeLayer() {
        cake.addLayer();
    }

    public void addCreamLayer() {
        cake.setMidLayerCream(Cream.Vanilla);
    }
    
    public void addTopLayer() {
        cake.setTopLayerCream(Cream.Red_Berries);
    }

    public void addTopping() {
        cake.setTopping(Topping.Chocolate);
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
