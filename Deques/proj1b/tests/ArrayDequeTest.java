import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    public void testAddFirst(){ //not required for coverage I think
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");
        ad.addFirst("d");
        ad.addFirst("e");
        ad.addFirst("f");
        ad.addFirst("g");
        ad.addFirst("h");

        assertThat(ad.size()).isEqualTo(8);
    }
    @Test
    public void add_first_after_remove_to_empty(){
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for(int i = 0; i < 10; i++){
            ad2.addFirst(i);
        }
        for(int i = 0; i < 10; i++){
            ad2.removeLast();
        }
        assertThat(ad2.isEmpty()).isTrue();
        assertThat(ad2.size()).isEqualTo(0);
        ad2.addFirst(-99);
        assertThat(ad2.get(0)).isEqualTo(-99);
    }
    @Test
    public void add_last_after_remove_to_empty(){
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for(int i = 0; i < 100; i++){
            ad2.addFirst(i);
        }
        for(int i = 0; i < 100; i++){
            ad2.removeLast();
        }
        assertThat(ad2.isEmpty()).isTrue();
        ad2.addLast(-99);
        assertThat(ad2.get(0)).isEqualTo(-99);
    }
    @Test
    public void testAddLast(){ // not required for coverage I think
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addLast("a");
        ad.addLast("b");
        ad.addLast("c");
        ad.addLast("d");
        ad.addLast("e");
        ad.addLast("f");
        ad.addLast("g");
        ad.addLast("h");

        assertThat(ad.size()).isEqualTo(8);
    }
    @Test
    public void testAddFirstAndAddLast(){ // not required for coverage I think
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addFirst("e");
        ad.addLast("f");
        ad.addLast("g");
        ad.addLast("h");

        assertThat(ad.size()).isEqualTo(8);

    }
    @Test
    public void testGet(){
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addFirst("e");
        ad.addLast("f");
        ad.addLast("g");
        ad.addLast("h");

        assertThat(ad.get(0)).isEqualTo("e");
        assertThat(ad.get(1)).isEqualTo("c");
        assertThat(ad.get(2)).isEqualTo("b");
        assertThat(ad.get(3)).isEqualTo("a");
        assertThat(ad.get(4)).isEqualTo("d");
        assertThat(ad.get(5)).isEqualTo("f");
        assertThat(ad.get(6)).isEqualTo("g");
        assertThat(ad.get(7)).isEqualTo("h");

        assertThat(ad.get(-1)).isNull();
        assertThat(ad.get(9)).isNull();


        //RANDOMIZED ADD, REMOVE
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ad2.addFirst(0);
        assertThat(ad2.get(0)).isEqualTo(0);
        ad2.addFirst(2);
        assertThat(ad2.removeLast()).isEqualTo(0);
        ad2.addFirst(4);
        assertThat(ad2.get(1)).isEqualTo(2);
        assertThat(ad2.get(1)).isEqualTo(2);
        assertThat(ad2.removeFirst()).isEqualTo(4);
        ad2.addFirst(8);
        assertThat(ad2.removeFirst()).isEqualTo(8);
        assertThat(ad2.removeLast()).isEqualTo(2);
        ad2.addLast(11);

        // ADD CASE FOR RESIZING
        // ADD CASE FOR REMOVE FIRST AND REMOVE LAST
        // ADD A CASE FOR REMOVE FIRST, REMOVE LAST, ADD FIRST, ADD LAST, RESIZING UP AND DOWN
    }
    @Test
    public void testToList(){
        ArrayDeque<String> ad = new ArrayDeque<>();
        List<String> lst = new ArrayList<>();
        assertThat(ad.toList()).isEqualTo(lst);
        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addFirst("e");
        ad.addLast("f");
        ad.addLast("g");
        ad.addLast("h");

        lst = new ArrayList<>(List.of("e", "c", "b", "a", "d", "f", "g", "h"));

        assertThat(ad.toList()).isEqualTo(lst);
    }

    @Test
    public void testIsEmpty(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.isEmpty()).isTrue();

        ad.addFirst(1);
        assertThat(ad.isEmpty()).isFalse();

        //ADD CASE WHERE YOU ADD AND THEN REMOVE
    }

    @Test
    public void testSize(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.size()).isEqualTo(0);
        for(int i = 0; i < 8; i++){
            ad.addFirst(i);
            assertThat(ad.size()).isEqualTo(i + 1);
        }
    }
    @Test
    public void testRemoveFirst(){
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addFirst("e");
        ad.addLast("f");
        ad.addLast("g");
        ad.addLast("h");

        assertThat(ad.removeFirst()).isEqualTo("e");
        assertThat(ad.removeFirst()).isEqualTo("c");
        assertThat(ad.removeFirst()).isEqualTo("b");
        assertThat(ad.removeFirst()).isEqualTo("a");

        assertThat(ad.size()).isEqualTo(4);

        assertThat(ad.removeFirst()).isEqualTo("d");
        assertThat(ad.removeFirst()).isEqualTo("f");
        assertThat(ad.removeFirst()).isEqualTo("g");
        assertThat(ad.removeFirst()).isEqualTo("h");


        assertThat(ad.isEmpty()).isTrue();

        assertThat(ad.removeFirst()).isNull();

        //remove first triggers resize
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for(int i = 0; i < 64; i++){
            ad2.addFirst(i);
        }
        for(int i = 0; i < 48; i++){
            ad2.removeFirst();
        }
        ad2.removeFirst();

    }
    @Test
    public void testRemoveLast(){
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addFirst("e");
        ad.addLast("f");
        ad.addLast("g");
        ad.addLast("h");

        assertThat(ad.removeLast()).isEqualTo("h");
        assertThat(ad.removeLast()).isEqualTo("g");
        assertThat(ad.removeLast()).isEqualTo("f");
        assertThat(ad.removeLast()).isEqualTo("d");

        assertThat(ad.size()).isEqualTo(4);

        assertThat(ad.removeLast()).isEqualTo("a");
        assertThat(ad.removeLast()).isEqualTo("b");
        assertThat(ad.removeLast()).isEqualTo("c");
        assertThat(ad.removeLast()).isEqualTo("e");

        assertThat(ad.isEmpty()).isTrue();

        assertThat(ad.removeLast()).isNull();

        //remove last triggers resize
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for(int i = 0; i < 64; i++){
            ad2.addFirst(i);
        }
        for(int i = 0; i < 48; i++){
            ad2.removeLast();
        }
        ad2.removeFirst();
    }
    @Test
    public void testResizeDown(){

    }

}
