package adt;

import java.util.Iterator;

public class Queue <T> implements QueueI<T>{
    private T[] array; // circular array of array entries and one unused location
    private int frontIndex;
    private int backIndex;
    private static final int DEFAULT_CAPACITY = 5;

    public Queue() {
        this(DEFAULT_CAPACITY);
    }

    public Queue(int initialCapacity) {
        array = (T[]) new Object[initialCapacity + 1];
        frontIndex = 0;
        backIndex = initialCapacity;
    }

    public void enqueue(T newEntry) {
        if (!isArrayFull()) {
            backIndex = (backIndex + 1) % array.length;
            array[backIndex] = newEntry;
        }
    }

    public T getFront() {
        T front = null;

        if (!isEmpty()) {
            front = array[frontIndex];
        }

        return front;
    }

    public T dequeue() {
        T front = null;

        if (!isEmpty()) {
            front = array[frontIndex];
            array[frontIndex] = null;
            frontIndex = (frontIndex + 1) % array.length;
        }

        return front;
    }

    public boolean isEmpty() {
        return frontIndex == ((backIndex + 1) % array.length);
    }

    public void clear() {
        if (!isEmpty()) {
            for (int index = frontIndex; index != backIndex; index = (index + 1) % array.length) {
                array[index] = null;
            }
            array[backIndex] = null;
        }

        frontIndex = 0;
        backIndex = array.length - 1;
    }

    private boolean isArrayFull() {
        return frontIndex == ((backIndex + 2) % array.length);
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(array);
    }

    public class QueueIterator<T> implements Iterator<T>{

        private T[] array;
        private int current;

        public QueueIterator(T[] array) {
            this.array = array;
            current = frontIndex;
        }

        @Override
        public boolean hasNext() {
            return !isEmpty() && current != backIndex + 1;
        }

        @Override
        public T next() {
            if(isEmpty())
                return null;

            T result = array[current];
            current = (current + 1) % array.length;
            return result;
        }
    }
}
