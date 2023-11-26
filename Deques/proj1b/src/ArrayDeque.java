
import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private static final int INITIALSIZE = 8;
    private static final int MAX_SIZE = 16;
    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 32; i++) {
            ad.addLast(i);
        }

    }

    public ArrayDeque() {
        items = (T[]) new Object[INITIALSIZE];
        this.nextFirst = 7;
        this.nextLast = 0;
        this.size = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
           resizeUp();
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resizeUp();
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int first = (nextFirst + 1) % items.length;
        T temp = items[first];
        items[first] = null;
        nextFirst = first;
        size -= 1;
        resizeDown();
        return temp;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int last = (nextLast - 1 + items.length) % items.length;
        T temp = items[last];
        items[last] = null;
        nextLast = last;
        size -= 1;
        resizeDown();
        return temp;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int i = ((nextFirst + 1) % items.length + index) % items.length;
        return items[i];
    }

    private void resizeDown() {
        if (size <= items.length / 4 && items.length > MAX_SIZE) {
            T[] temp = (T[]) new Object[size + 1];
            for (int i = 0; i < size; i++) {
                temp[i] = get(i);
            }
            items = temp;
            nextLast = items.length - 1;
            nextFirst = items.length - 1;
        }
    }

    private void resizeUp() {
        if (size == items.length) {
            T[] temp = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = get(i);
            }
            items = temp;
            nextLast = size;
            nextFirst = items.length - 1;
        }
    }
}
