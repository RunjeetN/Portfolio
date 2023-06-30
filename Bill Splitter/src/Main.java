import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Map<String, Friend> friends = new HashMap<>();
    static Map<String, Food> foods = new HashMap<>();

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

    public static void print() {
        // print the names of all friends who owe money, how much they owe, and what they've eaten
        for (String name : friends.keySet()) {
            Friend f = friends.get(name);
            System.out.println(name + " ate " + f.printFoods() + " and owes $" + f.getDebt());
        }
    }
}