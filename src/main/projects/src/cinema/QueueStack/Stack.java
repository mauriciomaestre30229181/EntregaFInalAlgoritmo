package cinema.QueueStack;

import java.util.NoSuchElementException;

public class Stack<T> {
    private static class Node<T>{
        T data;
        Node<T> next;
        
        public Node(T data){
            this.data = data;
        }
    }
    
    private Node<T> top;
    private int size;
    
    public void push(T item){
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    public T pop(){
        if (isEmpty()){
            throw new NoSuchElementException("La pila está vacía");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }
    
    public T peek(){
        if (isEmpty()){
            throw new NoSuchElementException("La pila está vacía");
        }
        return top.data;
    }
    
    public boolean isEmpty(){
        return top == null;
    }
    
    public int getSize(){
        return size;
    }
}