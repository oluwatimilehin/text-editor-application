package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    LLNode<E> current = head;
    LLNode<E> newNode;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {

        this.head = new LLNode<E>();
        this.tail = new LLNode<E>();
        size = 0;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        if (element != null) {
            newNode = new LLNode<>(element);
            if (size == 0) {
                head.next = newNode;
            }

            newNode.prev = current;
            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            }
            newNode.next = tail;
            this.current = newNode;
            tail.prev = newNode;
            size++;
            return true;
        } else {
            throw new NullPointerException();
        }

    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> node = head;
        for (int i = 0; i <= index; i++) {
            node = node.getNext();
        }
        return node.data;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   The index where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        //TODO: Add more work on this
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> node = head;
        for (int i = 0; i <= index; i++) {
            node = node.getNext();
        }
        node.data = element;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return this.size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> node = head;
        for (int i = 0; i <= index; i++) {
            node = node.getNext();
        }

        E elementRemoved = node.data;
        LLNode<E> prevNode = node.prev;
        if(node.prev != null) {
            node.prev.next = node.next;
        }else{
            head.next = node.next;
        }
        node.next.prev = prevNode;
        size--;
        return elementRemoved;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (element != null) {
            LLNode<E> node = head;
            for (int i = 0; i <= index; i++) {
                node = node.getNext();
            }
            E elementReplaced = node.data;
            node.data = element;
            return elementReplaced;
        } else {
            throw new NullPointerException();
        }
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;


    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

    public LLNode(E e, LLNode<E> prev, LLNode<E> next) {
        this(e);
        this.prev = prev;
        this.next = next;
    }

    public LLNode() {
        this(null);
    }

    public LLNode<E> getNext() {
        return this.next;
    }

}
