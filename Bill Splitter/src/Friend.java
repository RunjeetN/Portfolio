import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Friend {
    private String name;
    private double debt;
    private boolean paid;
    List<Food> eaten;
    public Friend(String name){
        this.name = name;
        debt = 0.0;
        paid = false;
        eaten = new ArrayList<>();
    }
    public void addDebt(double amount) {debt += amount;}
    public void payDebt(int amount) {debt -= amount;}
    public double getDebt() {return debt;}
    public String getName() {return name;}
    public void markOff() {paid = true;}
    public void addFood(Food f){
        if(!eaten.contains(f)){
            eaten.add(f);
        }
    }
    public void removeFood(Food f){
        if(eaten.contains(f)){
            eaten.remove(f);
        }
    }

}