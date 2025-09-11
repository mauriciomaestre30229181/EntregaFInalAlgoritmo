package JavaRace.repositories;

import java.util.NoSuchElementException;

/**
 * Implementación de una Cola (Queue) genérica desde cero, utilizando una política FIFO (First-In, First-Out).
 * Esta estructura no contiene manejo de errores interno; en su lugar, lanza excepciones
 * que deben ser gestionadas por el código cliente.
 * @param <T> El tipo de dato que almacenará la cola.
 */
public class CustomQueue<T> {

    // Nodo interno para la lista enlazada
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> front; // Cabeza de la cola
    private Node<T> rear;  // Final de la cola
    private int size;

    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param item El elemento a encolar.
     */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Remueve y retorna el elemento al frente de la cola.
     * @return El elemento que estaba al frente.
     * @throws NoSuchElementException si la cola está vacía.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("No se puede desencolar de una cola vacía.");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * Retorna el elemento al frente de la cola sin removerlo.
     * @return El elemento que está al frente.
     * @throws NoSuchElementException si la cola está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("No se puede espiar una cola vacía.");
        }
        return front.data;
    }

    /**
     * Verifica si la cola no tiene elementos.
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna el número de elementos en la cola.
     * @return el tamaño de la cola.
     */
    public int size() {
        return size;
    }
}