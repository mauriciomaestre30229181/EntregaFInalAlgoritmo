package TemplateList;

public class List<T> {
    private Node<T> head;
    private int size;

    /**
     * Precondición: Ninguna
     * Postcondición: Se crea una lista vacía
     */
    public List(){
        this.head = new Node<>();
        this.size = 0;
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna el tamaño actual de la lista
     * @return El tamaño de la lista
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Precondición: La lista no debe estar vacía
     * Postcondición: Retorna el primer nodo de la lista
     * @return El primer nodo de la lista
     * @throws IllegalArgumentException Si la lista está vacía
     */
    public Node<T> getNode(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("- list-Error: Lista vacia, nada que eliminar");
        }
        return this.head.getNext();
    }

    /**
     * Precondición: El parámetro data no debe ser nulo y la lista no debe estar vacía
     * Postcondición: Retorna el nodo que contiene el data especificado, o null si no se encuentra
     * @param data El dato a buscar en la lista
     * @return El nodo que contiene el data, o null si no se encuentra
     * @throws IllegalArgumentException Si data es nulo o la lista está vacía
     */
    public Node<T> getNodeByData(T data){
        if (data == null){
            throw new IllegalArgumentException("- list-Error: El dato es nulo, no puede realizar la busqueda. ");
        }

        if (this.isEmpty()){
            throw new IllegalArgumentException("- list-Error: Lista vacia, nada que eliminar");
        }

        Node<T> current = this.head.getNext();
        while (current != null){
            if (current.getData().equals(data)){
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Precondición: La lista no debe estar vacía
     * Postcondición: Retorna el nodo en la posición especificada, o el último nodo si la posición es mayor al tamaño
     * @param pos La posición del nodo a buscar (basado en 0)
     * @return El nodo en la posición especificada
     * @throws IllegalArgumentException Si la lista está vacía
     */
    public Node<T> getNodeByPos(int pos){
        if (this.isEmpty()){
            throw new IllegalArgumentException("- list-Error: Lista vacia, nada que eliminar");
        }

        if (pos <= 0){
            return this.getNode();
        }

        int iterator = 0;
        Node<T> current = this.head.getNext();
        while (current != null && iterator < pos){
            current = current.getNext();
            iterator++;
        }
        return current;
    }

    /**
     * Precondición: El parámetro data no debe ser nulo
     * Postcondición: El elemento es insertado al inicio de la lista
     * @param data El elemento a insertar
     */
    public void insert(T data){
        Node<T> head = this.head;
        Node<T> node = new Node<>(data);

        if (head.getNext() == null){
            head.setNext(node);
        } else {
            Node<T> aux = head.getNext();
            head.setNext(node);
            node.setNext(aux);
        }
        this.size++;
    }

    /**
     * Precondición: El parámetro data no debe ser nulo
     * Postcondición: El elemento es insertado en la posición especificada, o al inicio si la posición es menor o igual a 0
     * @param data El elemento a insertar
     * @param pos La posición donde insertar el elemento
     */
    public void insert(T data, int pos){
        if (pos <= 0){
            this.insert(data);
            return;
        }

        Node<T> head = this.head;
        Node<T> node = new Node<>(data);

        int iterator = 0;
        Node<T> prev = head;
        Node<T> next = head.getNext();

        while (next != null && iterator < pos){
            prev = next;
            next = next.getNext();
            iterator++;
        }

        prev.setNext(node);
        if (next != null){
            node.setNext(next);
        }
        this.size++;
    }

    /**
     * Precondición: La lista no debe estar vacía
     * Postcondición: El primer elemento de la lista es removido
     * @throws IllegalArgumentException Si la lista está vacía
     */
    public void remove(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("- list-Error: Lista vacia, nada que eliminar");
        }
        Node<T> head = this.head;
        Node<T> nodeRemove = head.getNext();
        head.setNext(nodeRemove.getNext());
        nodeRemove = null;
        this.size--;
    }

    /**
     * Precondición: La posición debe estar entre 0 y size-1
     * Postcondición: El elemento en la posición especificada es removido
     * @param pos La posición del elemento a remover
     * @throws IllegalArgumentException Si la posición es inválida
     */
    public void remove(int pos){
        if (pos < 0 || pos >= this.size){
            throw new IllegalArgumentException("- list-Error: Invalid pos. ");
        }

        if (pos == 0){
            this.remove();
            return;
        }

        Node<T> head = this.head;
        int iterator = 0;
        Node<T> prev = head;
        Node<T> current = head.getNext();

        while (current != null && iterator < pos){
            prev = current;
            current = current.getNext();
            iterator++;
        }

        if (current != null){
            prev.setNext(current.getNext());
            current = null;
            this.size--;
        }
    }

    /**
     * Precondición: El parámetro data no debe ser nulo y la lista no debe estar vacía
     * Postcondición: El elemento con el data especificado es removido
     * @param data El dato del elemento a remover
     * @throws IllegalArgumentException Si data es nulo o la lista está vacía
     */
    public void remove(T data){
        if (data == null){
            throw new IllegalArgumentException("- list-Error: El dato es nulo, no puede realizar la busqueda. ");
        }

        if (this.isEmpty()){
            throw new IllegalArgumentException("- list-Error: Lista vacia, nada que eliminar");
        }

        Node<T> head = this.head;
        Node<T> prev = head;
        Node<T> current = head.getNext();

        while (current != null && !current.getData().equals(data)){
            prev = current;
            current = current.getNext();
        }

        if (current != null){
            prev.setNext(current.getNext());
            current = null;
            this.size--;
        }
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Retorna true si la lista está vacía, false en caso contrario
     * @return true si la lista está vacía, false en caso contrario
     */
    public boolean isEmpty(){
        return this.head.getNext() == null || this.size == 0;
    }

    /**
     * Precondición: Ninguna
     * Postcondición: Muestra el contenido de la lista en la consola
     */
    public void showList(){
        if (this.size == 0) {
            System.out.println("|::");
            return;
        }

        Node<T> current = this.head.getNext();
        while (current != null){
            System.out.print(current);
            if (current.getNext() != null){
                System.out.print(" -> ");
            }
            current = current.getNext();
        }
        System.out.print("|::");
    }
}