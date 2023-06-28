package lab05.ex2;

// Cake: Sponge
// Cream: Red Berries
// Top: Whipped Cream
// Topping: Fruit

public class SpongeCakeBuilder implements CakeBuilder {
    private Cake cake;
    private String cakeLayer = "Sponge"; 


    public void setCakeShape(Shape shape) {
        cake.setShape(shape);
    }
    
    public void addCakeLayer() {
        cake.addLayer();
    }

    public void addCreamLayer() {
        cake.setMidLayerCream(Cream.Red_Berries);
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

