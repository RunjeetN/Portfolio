import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    @Test
    public void addAfterRemoveTest(){
         LinkedListDeque<Integer> lld = new LinkedListDeque<>();
         List<Integer> lst = new ArrayList<>();
         lld.addFirst(1);
         lld.addLast(2);
         lst.add(1);
         lst.add(2);

         assertThat(lld.toList()).isEqualTo(lst);
         for(int i = 0; i<2; i++){
             lld.removeLast();
             lst.remove(lst.size()-1);
             assertThat(lld.toList()).isEqualTo(lst);
         }
    }
    @Test
    public void addLastAfterRemoveToEmptyTest(){

    }
     @Test
    /* Test the size() function*/
    public void sizeTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
        assertThat(lld.size()).isEqualTo(0);
        for(int i = 1; i <= 10; i++){
            lld.addLast(i);
            assertThat(lld.size()).isEqualTo(i);
        }

    }
    @Test
    public void isEmptyTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.isEmpty()).isTrue();
        lld.addFirst(1);
        assertThat(lld.isEmpty()).isFalse();
    }

    @Test
    public void getValidIndexTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(3);
        lld.addLast(4);
        lld.addFirst(1);
        assertThat(lld.get(0)).isEqualTo(1);
        assertThat(lld.get(2)).isEqualTo(4);
        assertThat(lld.get(2)).isNotNull();
        }
    @Test
    public void getInvalidIndexTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.get(12)).isNull();
        assertThat(lld.get(-1)).isNull();
    }
    @Test
    public void getRecursiveValidIndexTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(3);
        lld.addLast(4);
        lld.addFirst(1);
        assertThat(lld.getRecursive(0)).isEqualTo(1);
        assertThat(lld.getRecursive(2)).isEqualTo(4);
        assertThat(lld.getRecursive(2)).isNotNull();
    }
    @Test
    public void getRecursiveInvalidIndexTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.getRecursive(12)).isNull();
        assertThat(lld.getRecursive(-1)).isNull();
    }

    @Test
    public void Remove_First_to_One(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        for (int i = 0; i < 100; i++) {
            lld.addFirst(2);
        }
        for (int i = 0; i < 99; i++) {
            lld.removeLast();
        }
        assertThat(lld.size()).isEqualTo(1);
    }

    @Test
    public void Remove_First_to_Empty(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        for (int i = 0; i < 100; i++) {
            lld.addFirst(2);
        }
        for (int i = 0; i < 100; i++) {
            lld.removeLast();
        }
        lld.addFirst(1);
        assertThat(lld).isNotNull();
    }

    @Test
    public void removeFirstTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.removeFirst()).isNull();
        assertThat(lld.removeLast()).isNull();

        lld.addLast(3);
        lld.addLast(2);
        lld.addFirst(1);

        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(3);
        lst.add(2);

        for(int i = 0; i < lld.size(); i++){
            assertThat(lld.removeFirst()).isEqualTo(lst.get(0));
            lst.remove(0);
            assertThat(lld.toList()).isEqualTo(lst);
        }
    }
    public void removeLastTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.removeFirst()).isNull();
        assertThat(lld.removeLast()).isNull();

        lld.addLast(3);
        lld.addLast(2);
        lld.addFirst(1);

        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(3);
        lst.add(2);

        for(int i = 0; i < lld.size(); i++) {
            assertThat(lld.removeFirst()).isEqualTo(lst.get(lst.size() - 1));
            lst.remove(lst.size() - 1);
            assertThat(lld.toList()).isEqualTo(lst);
        }
    }
}