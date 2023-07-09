public class Food {
    private String name;
    private double price;
    private int n; // number of people eating this food/drink
    public Food(String name, double price){
        this.name = name;
        this.price = price;
        n = 0;
    }
    public String getName(){return name;}
    public double getPrice(){return price;}
    public void updatePrice(double p){price = p;}
    public void increment(){n++;}
    public int getN(){return n;}
    @Override
    public String toString(){return name + " : " + price;}
}
