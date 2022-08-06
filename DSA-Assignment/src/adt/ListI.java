package adt;

public interface ListI <T>{

    public boolean add(T newEntry);

    public boolean add(int newPosition, T newEntry);

    T remove(int givenPosition);

    public void clear();

    public boolean replace(int position, T newEntry);

    public T getEntry(int position);

    public boolean contains(T anEntry);

    public int getNumberOfEntries();

    public boolean isEmpty();

    public boolean isFull();

}