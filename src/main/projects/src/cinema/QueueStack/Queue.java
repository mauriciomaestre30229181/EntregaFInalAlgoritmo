package cinema.QueueStack;

import java.util.NoSuchElementException;

public class Queue<T> {
    private static class Node<T>{
        T data;
        Node<T> next;
        
        public Node(T data){
            this.data = data;
        }
    }
    
    private Node<T> first;
    private Node<T> last;
    private int size;
    
    public void enqueue(T item){
        Node<T> newNode = new Node<>(item);
        if (last != null){
            last.next = newNode;
        }
        last = newNode;
        if (first == null){
            first = last;
        }
        size++;
    }
    
    public T dequeue(){
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        T data = first.data;
        first = first.next;
        if(first == null){
            last = null;
        }
        size --;
        return data;
    }
    
    public T peek(){
        if(isEmpty()){
            throw new NoSuchElementException("La cola está vacía");
        }
        return first.data;
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    
    public int getSize(){
        return size;
    }
}