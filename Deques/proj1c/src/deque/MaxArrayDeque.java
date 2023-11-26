package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> comp;
    public MaxArrayDeque(Comparator<T> c) {
        comp = c;
    }
    public T max() {
        return max(comp);
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        int maxI = 0;
        for (int i = 1; i < size(); i++) {
            if (c.compare(get(maxI), get(i)) < 0) {
                maxI = i;
            }
        }
        return get(maxI);

    }
}

