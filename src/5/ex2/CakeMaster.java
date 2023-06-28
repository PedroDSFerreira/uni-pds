package lab05.ex2;

public class CakeMaster {
    private CakeBuilder cakeBuilder = null;


    public void setCakeBuilder(CakeBuilder cakeBuilder) {
        this.cakeBuilder = cakeBuilder;
    }

    public void createCake(String message) {
        cakeBuilder.createCake();
        cakeBuilder.setCakeShape(Shape.Round);
        cakeBuilder.addCakeLayer();
        cakeBuilder.addTopLayer();
        cakeBuilder.addTopping();
        cakeBuilder.addMessage(message);
    }

    public void createCake(Shape shape, int layers, String message) {
        cakeBuilder.createCake();
        cakeBuilder.setCakeShape(shape);
        
        for (int i = 0; i < layers; i++) {
            cakeBuilder.addCakeLayer();
        }

        cakeBuilder.addCreamLayer();
        cakeBuilder.addTopLayer();
        cakeBuilder.addTopping();
        cakeBuilder.addMessage(message);

    }

    public void createCake(int layers, String message) {
        cakeBuilder.createCake();
        cakeBuilder.setCakeShape(Shape.Round);

        for (int i = 0; i < layers; i++) {
            cakeBuilder.addCakeLayer();
        }

        cakeBuilder.addCreamLayer();
        cakeBuilder.addTopLayer();
        cakeBuilder.addTopping();
        cakeBuilder.addMessage(message);
    }

    public Cake getCake() {
        return cakeBuilder.getCake();
    }
}
