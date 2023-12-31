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
    public double getDebt() {
        debt = 0;
        for(Food f: eaten){
            debt += f.getPrice() / f.getN();
        }
        return debt;
    }
    public String getName() {return name;}
    public void markOff() {paid = true;}
    public void addFood(Food f){
        if(!eaten.contains(f)){
            eaten.add(f);
            f.increment();

        }
    }
    public void removeFood(Food f){
        if(eaten.contains(f)){
            eaten.remove(f);
        }
    }
    public List<Food> getFoods(){return eaten;}
    public String printFoods(){
        if (eaten.isEmpty()) {
            return "nothing";
        }
        return eaten.toString();
    }
    // now calling toString on a list of friend objects returns a list of friend names
    @Override
    public String toString(){
        return name;
    }
    public List<String> whatTheyAte(){
        List<String> lst = new ArrayList<>();
        for(Food f: eaten){
            lst.add(f.getName());
        }
        return lst;
    }
    // now calling .contains(some_name) on a list of friend objects checks if any of the objects have some_name as their names
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Friend){
            return ((Friend) obj).getName().equals(getName());
        }
        return false;
    }


    public static void main(String[] args){
        Friend nathan = new Friend("Nathan");
        nathan.addFood(new Food("Curry", 10));
        nathan.addFood(new Food("Naan", 4));
        System.out.println(nathan.eaten.toString());
    }
}