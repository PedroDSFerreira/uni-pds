package lab05.ex2;

class Cake { 
    private Shape shape;
    private String cakeLayer; 
    private int numCakeLayers = 0; 
    private Cream midLayerCream; 
    private Cream topLayerCream; 
    private Topping topping; 
    private String message; 


    public Cake(Shape shape, String cakeLayer) {
        this.shape = shape;
        this.cakeLayer = cakeLayer;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setCakeLayer(String cakeLayer) {
        this.cakeLayer = cakeLayer;
    }

    public void setMidLayerCream(Cream midLayerCream) {
        this.midLayerCream = midLayerCream;
    }

    public void setTopLayerCream(Cream topLayerCream) {
        this.topLayerCream = topLayerCream;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addLayer() {
        numCakeLayers++;
    }



    @Override
    public String toString() {
        String midLayerCreamOutput = "";
        if (midLayerCream != null) {
            midLayerCreamOutput = " and " + midLayerCream + " cream";
        }
        return cakeLayer + " cake with " + 
        numCakeLayers + " layers" + midLayerCreamOutput +
        ", topped with " + 
        topLayerCream + " cream and " + topping + 
        ". Message says: \"" + message + "\".";
    }
}