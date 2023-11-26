import deque.ArrayDeque;
import deque.MaxArrayDeque;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDequeTest {
    @Test
    public void testToString(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(2);
        ad.addFirst(56);
        System.out.println(ad);
    }
    @Test
    public void testHasNextAfterCallingNextManyTime(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        Iterator<Integer> adIterator = ad.iterator();
        for(int i = 0; i < 25; i++){
            ad.addFirst(i);
        }
        for(int i = 0; i < 25; i++){
            System.out.println(adIterator.next());
        }
        assertThat(adIterator.hasNext()).isFalse();
    }
    @Test
    public void testWithTwoIterators(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for(int i = 0; i < 25; i++){
            ad.addFirst(i);
        }
        Iterator<Integer> adIter1 = ad.iterator();
        Iterator<Integer> adIter2 = ad.iterator();
        for(int i = 0; i < 25; i++){
            System.out.println(adIter1.next());
        }
        System.out.println("SWITCHING ITERATORS");
        for(int i = 0; i < 11; i++){
            System.out.println(adIter2.next());
        }
        assertThat(adIter1.hasNext()).isFalse();
        assertThat(adIter2.hasNext()).isTrue();

    }
}
