import java.util.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static Map<String, Friend> friends = new TreeMap<>();
    private static Map<String, Food> foods = new TreeMap<>();
    private static List<String> foodList = new ArrayList<>();

    public static void main(String[] args) {

    }

    public void addFriend(String input) {
        // given a name, add the name and the corresponding Friend object to the map if not already there
        if (!friends.containsKey(input) && !input.isBlank()){
            friends.put(input, new Friend(input));
        }
    }


    public void addFood(String name, double cost) {
        // given a name and cost of food, add name to map and associated Food object. If already in map, update price.
        if(name.isBlank() || cost == 0.0){
            return;
        }
        if(foods.containsKey(name)){
            foods.get(name).updatePrice(cost);
        }
        else{
            foods.put(name, new Food(name, cost));
        }
    }

    public void assignFriends() {
        for (Food f : foods.values()) {
            assignFood(f);
        }
        print();
    }

    public void assignFood(Food f) {
        String person = "";
        List<Friend> people = new ArrayList<>();
        System.out.println("Who had the " + f.getName() + " : \n");
        while (true) {
            person = scan.next();
            if (person.equalsIgnoreCase("finish")) {
                for (Friend x : people) {
                    x.addFood(f);
                    x.addDebt(f.getPrice() / people.size());
                }
                return;
            }
            if (friends.containsKey(person)) {
                people.add(friends.get(person));
            }
        }
    }

    public static List<String> print() {
        // print the names of all friends who owe money, how much they owe, and what they've eaten
        List<String> receipt = new ArrayList<>();
        for (String name : friends.keySet()) {
            Friend f = friends.get(name);
            receipt.add(name + " ate " + f.whatTheyAte() + " and owes $" + f.getDebt());
        }
        return receipt;
    }
    public String friendsToString(){
        return friends.toString();
    }
    public Object[] getFriends(){
        return friends.keySet().toArray();
    }
    public Friend getFriend(String name){
        return friends.get(name);
    }
    public Food getFood(String name){
        return foods.get(name);
    }
    public Collection<Friend> getFriendObjects(){
        return friends.values();
    }
    public String foodsToString(){
        return foods.toString();
    }
    public Object[] getFoods(){
        foodList.clear();
        for(Food f: foods.values()){
            if(f.getPrice() % 1 == 0){
                foodList.add(f.getName() + " : $" + f.getPrice() + "0");
            }
            else{
                foodList.add(f.getName() + " : $" + f.getPrice());
            }
        }
        return foodList.toArray();
    }
    public Object[] getFoodKeySet(){
        return foods.keySet().toArray();
    }
}