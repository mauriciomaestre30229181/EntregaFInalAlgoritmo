package types;

public class Stack<T> {
    private Node<T> stack;
    private int size;

    public Stack() {
        this.stack = null;
        this.size = 0;
    }

    public T showStack() {
        if (this.isEmpty())
            throw new IllegalArgumentException("- StackError: La pila esta vacia.");

        return this.stack.getData();
    }

    public int getSize() {
        return this.size;
    }

    public void push(T data) {
        if (data == null)
            throw new IllegalArgumentException("- StackError: El dato es nulo, no pudo ser agregado a la pila.");

        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.stack);
        this.stack = newNode; // El nuevo nodo ahora sera la cima
        this.size++;
    }

    public T pop() { // Desapilar stack
        if (this.isEmpty())
            throw new IllegalArgumentException("- StackError: La pila esta vacia.");

        T data = this.stack.getData();
        this.stack = this.stack.getNext();
        this.size--;

        return data;
    }

    public boolean isEmpty() {
        return this.stack == null && this.size == 0;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        Node<T> current = this.stack;
        while (current != null) {
            value.append(current.getData() + "\n");
            current = current.getNext();
        }

        return value.toString();
    }
}