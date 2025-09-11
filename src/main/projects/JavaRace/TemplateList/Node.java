package TemplateList;

public class Node<T> {
    private T data;
    Node<T> next;

    /**
     * Precondición: Ninguna
     * Postcondición: Se crea un nodo con data nulo y next nulo
     */
    public Node(){
        this.data = null;
        this.next = null;
    }

    /**
     * Precondición: El parámetro data no debe ser nulo
     * Postcondición: Se crea un nodo con el data especificado y next nulo
     * @param data El dato a almacenar en el nodo
     * @throws IllegalArgumentException Si data es nulo
     */
    public Node(T data){
        if (data == null){
            throw new IllegalArgumentException("- RefError: Data no puede ser nulo. ");
        }
        this.data = data;
        this.next = null;
    }

    /**
     * Precondición: El nodo a copiar no debe ser nulo
     * Postcondición: Se crea un nodo con los mismos valores que el nodo copiado
     * @param copy El nodo a copiar
     * @throws IllegalArgumentException Si copy es nulo
     */
    public Node(Node<T> copy){
        if (copy == null){
            throw new IllegalArgumentException("- RefError: Copia fallida por nodo nulo. ");
        }
        this.data = copy.getData();
        this.next = copy.getNext();
    }

    /**
     * Precondición: El parámetro data no debe ser nulo
     * Postcondición: El data del nodo es actualizado al valor especificado
     * @param data El nuevo dato a almacenar en el nodo
     * @throws IllegalArgumentException Si data es nulo
     */
    public void setData(T data) {
        if (data == null){
            throw new IllegalArgumentException("- RefError: El dato es nulo. ");
        }
        this.data = data;
    }

    /**
     * Precondición: Ninguna
     * Postcondición: El next del nodo es actualizado al nodo especificado
     * @param next El nuevo nodo siguiente
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna el data almacenado en el nodo
     * @return El data almacenado en el nodo
     */
    public T getData() {
        return this.data;
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna el nodo siguiente
     * @return El nodo siguiente
     */
    public Node<T> getNext() {
        return this.next;
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna true si el objeto especificado es igual a este nodo
     * @param obj El objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    public boolean IsEquals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Node<T> another = (Node<T>) obj;
        if (this.data == null){
            return another.data == null;
        }else {
            return this.data.equals(another.data);
        }
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna una representación en string del nodo
     * @return String que representa el nodo
     */
    @Override
    public String toString() {
        return "{Valor = "+this.data+"}";
    }
}