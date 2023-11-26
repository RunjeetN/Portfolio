import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    Node<T> sentinel = new Node<T>(null, null, null);
    int size;
    private class Node<T> {
        private T item;
        private Node next;
        private Node prev;
        private Node(Node p, T value, Node n) {
            item = value;
            next = n;
            prev = p;
        }
    }
    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(3);
        lld.addLast(2);
        lld.addFirst(1);

    }
    public LinkedListDeque() {
        size = 0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node<T> newFirst = new Node<>(sentinel, x, sentinel.next);
        sentinel.next = newFirst;
        newFirst.next.prev = newFirst;
        size += 1;

    }

    @Override
    public void addLast(T x) {
        Node<T> newLast = new Node<T>(sentinel.prev, x, sentinel);
        sentinel.prev.next = newLast;
        sentinel.prev = newLast;
        size += 1;

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> temp = sentinel.next;
        while (temp != sentinel) {
            returnList.add(temp.item);
            temp = temp.next;
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
        return size;
    }

    @Override
    public T removeFirst() {
        T x = get(0);
        if (isEmpty()) {
            return null;
        } else {
            Node t = sentinel.next.next;
            sentinel.next = t;
            t.prev = sentinel;
            size -= 1;
        }
        return x;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = get(size() - 1);
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> temp = sentinel.next;
        temp = sentinel.next;
        while (index > 0) {
            temp = temp.next;
            index -= 1;
        }
        return temp.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getHelper(index, sentinel);
    }
    public T getHelper(int index, Node<T> lst) {
        Node<T> temp = lst.next;
        if (index == 0) {
            return temp.item;
        }
        return getHelper(index - 1, temp);
    }
}
