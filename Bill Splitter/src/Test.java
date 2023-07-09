import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        int[] lst = {1, 3, 5, 4, 3};
        Node n = new Node(0);
        n = n.makeList(lst);
        System.out.println(n.getString(n));
        n = n.removeDupes(n);
        System.out.println(n.getString(n));
    }
    public static class Node {
        Node rest;
        int first;

        public Node(int num){
            first = num;
            rest = null;
        }

        public Node removeDupes(Node lst){
            if(lst == null || lst.rest == null){
                return lst;
            }
            List<Integer> uniqueNums = new ArrayList<>();
            Node item = lst;
            while(item != null){
                if(!uniqueNums.contains(item.first)){
                    uniqueNums.add(item.first);
                    item = item.rest;
                }
                else{
                    if(item.rest == null){
                        return lst;
                    }
                    item.rest = item.rest.rest;
                }
            }
            return lst;
        }
        public Node makeList(int[] lst){
            Node n = new Node(lst[0]);
            Node first = n;
            for(int i = 1; i < lst.length; i++){
                n.rest = new Node(lst[i]);
                n = n.rest;
            }
            return first;
        }
        public String getString(Node n){
            String s = "";
            while(n != null){
                s += n.first + ", ";
                n = n.rest;
            }
            return s;
        }
    }

}
