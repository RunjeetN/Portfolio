
import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    private class testComparator implements Comparator {
        @Override
        public boolean equals(Object obj){
            return false;
        }
        @Override
        public int compare(Object o1, Object o2){
            return (Integer) o1 - (Integer) o2;
        }
    }
    @Test
    public void testMax(){
        testComparator c = new testComparator();
        MaxArrayDeque<Integer> s = new MaxArrayDeque<>(c);
        s.addFirst(3000);
        assertThat(s.max(c)).isEqualTo(3000);
    }
    public class stringComparator implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }

    @Test
    public void stringTest(){
        stringComparator t = new stringComparator();
        MaxArrayDeque<String> temp = new MaxArrayDeque<>(t);
        for(int i = 0; i < 51; i++) {
            temp.addLast("a");
        }
        temp.removeFirst();
        temp.addLast("x");
        System.out.println(temp.max());
    }
    public class genericObject{
        private int weight;
        public genericObject(int w){
            weight = w;
        }
        public int getWeight(){
            return this.weight;
        }
        @Override
        public String toString(){
            return "genericObject with weight " + getWeight();
        }
    }
    public class OC implements Comparator{  // hehehe...like the show ;)

        @Override
        public int compare(Object o1, Object o2) {
            genericObject g1 = (genericObject) o1;
            genericObject g2 = (genericObject) o2;
            if(g1.getWeight() > g2.getWeight()){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
    @Test
    public void testGenericObjectDeque(){
        MaxArrayDeque<genericObject> gO = new MaxArrayDeque<>(new OC());
        assertThat(gO.max()).isNull();
        gO.addLast(new genericObject(1500));
        gO.addLast(new genericObject(23));
        gO.addFirst(new genericObject(12));
        gO.addLast(new genericObject(4));
        gO.addFirst(new genericObject(1));
        gO.addLast(new genericObject(13995));
        gO.addFirst(new genericObject(1));
        gO.addFirst(new genericObject(1));
        gO.addLast(new genericObject(1502));
        gO.addFirst(new genericObject(1));
        gO.addLast(new genericObject(1504));

        assertThat(gO.max().getWeight()).isEqualTo(13995);
        System.out.println(gO.max());
    }

}
