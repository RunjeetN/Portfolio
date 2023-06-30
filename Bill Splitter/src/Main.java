import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Map<String, Friend> friends = new HashMap<>();
    static Map<String, Food> foods = new HashMap<>();
    public static void main(String[] args){
        friends.put("Nathan", new Friend("Nathan"));
        friends.put("Dev", new Friend("Dev"));
        friends.put("Ridhi", new Friend("Ridhi"));
        friends.put("Shivani", new Friend("Shivani"));
        friends.put("Shruti", new Friend("Shruti"));

        foods.put("Chicken_Curry", new Food("Chicken_Curry", 15));
        foods.put("Naan", new Food("Naan", 6));
        foods.put("Rice", new Food("Rice", 3));
        foods.put("Paneer_Saag", new Food("Paneer_Saag", 9));

        assignFriends();
    }
    public static void addFriends(){
        // continuously check for new input in a while loop:
            // if the name isn't already in the list, add it
            // if it is, ask if it's another friend with the same name or a mistake
                // if it's a diff person, ask for last initial and append that to their name
        String firstName = "";
        String lastInitial = "";

        while(true){
            System.out.print("Please enter a friend's first name: ");
            firstName = scan.next();
            if(firstName.equalsIgnoreCase("finish")){
                System.out.println("all friends added");
                System.out.println(friends.keySet().toString());
                return;
            }
            System.out.println();
            if(friends.containsKey(firstName)){
                System.out.print("There is already a friend with this name, please enter their last initial to differentiate or type MISTAKE if this was a mistake: ");
                lastInitial = scan.next();
                if(!lastInitial.equalsIgnoreCase("mistake")) {
                    firstName += " " + lastInitial;
                }
                else { // I feel dirty doing this
                    addFriends();
                    return;
                }
            }
            friends.put(firstName, new Friend(firstName));
        }
    }
    public static void addFoods(){
        // continuously check for new input
            // if entered item (name) is not already in the list, create a food object and add it
            // else update the food's price with the new price

        String name = "";
        double cost = 0.0;
        String result = "";
        System.out.println("Type finish when you are done inputting foods");
        while(true) {
            System.out.print("\nEnter a dish/drink: ");
            name = scan.next();
            if(name.equalsIgnoreCase("finish")){
                System.out.println("\n" + foods.values().toString());
                return;
            }
            if(foods.containsKey(name)){
                System.out.print("\nThis dish is already added. Override the price or type mistake if this was a mistake: ");
                result = scan.next();
                if(result.equalsIgnoreCase("mistake")){
                    addFoods();
                    return;
                }
                foods.get(name).updatePrice(Double.parseDouble(result));
            }
            else{
                System.out.print("\nEnter the price: ");
                cost = scan.nextDouble();
                foods.put(name, new Food(name, cost));
            }
        }
    }
    public static void assignFriends(){
        for(Food f : foods.values()){
            assignFood(f);
        }
        print();
    }
    public static void assignFood(Food f){
        String person = "";
        System.out.println("Who had the " + f.getName() + " : \n");
        while(true){
            person = scan.next();
            if(person.equalsIgnoreCase("finish")){
                return;
            }
            if(friends.containsKey(person)){
                friends.get(person).addFood(f);
            }
        }
    }

    public static void print(){
        // print the names of all friends who owe money, how much they owe, and what they've eaten
        for(String name : friends.keySet()){
            Friend f = friends.get(name);
            System.out.println(name + " ate " + f.printFoods() + " and owes $" + f.getDebt());
        }
    }
}