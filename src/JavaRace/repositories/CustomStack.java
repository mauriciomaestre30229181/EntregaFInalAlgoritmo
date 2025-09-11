package JavaRace.repositories;

import java.util.EmptyStackException;

/**
 * Implementación de una Pila (Stack) genérica desde cero, utilizando una política LIFO (Last-In, First-Out).
 * Esta estructura no contiene manejo de errores interno; en su lugar, lanza excepciones
 * que deben ser gestionadas por el código cliente.
 * @param <T> El tipo de dato que almacenará la pila.
 */
public class CustomStack<T> {

    // Nodo interno para la lista enlazada
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> top; // Cima de la pila
    private int size;

    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento a la cima de la pila.
     * @param item El elemento a apilar.
     */
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Remueve y retorna el elemento en la cima de la pila.
     * @return El elemento que estaba en la cima.
     * @throws EmptyStackException si la pila está vacía.
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Retorna el elemento en la cima de la pila sin removerlo.
     * @return El elemento que está en la cima.
     * @throws EmptyStackException si la pila está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    /**
     * Verifica si la pila no tiene elementos.
     * @return true si la pila está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna el número de elementos en la pila.
     * @return el tamaño de la pila.
     */
    public int size() {
        return size;
    }
}