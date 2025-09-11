package src_restaurant.types;

public class Queue<T> {
    private Node<T> front;
    private Node<T> last;
    private int size;

    public Queue() {
        this.front = null;
        this.last = null;
        this.size = 0;
    }

    public T getPeek() {
        if (this.isEmpty())
            throw new IllegalArgumentException("- QueueError: La cola esta vacia.");

        return this.front.getData();
    }

    public void showLast() {
        System.out.println(this.last);
    }

    public int getSize() {
        return this.size;
    }

    public void enqueue(T data) { // Push
        if (data == null)
            throw new IllegalArgumentException("- QueueError: El dato es nulo, no pudo ser agregado a la cola.");

        Node<T> newNode = new Node<>(data);
        if (this.isEmpty())
            this.front = newNode; // Inicio de la cola nuevo nodo
        else
            this.last.setNext(newNode); // El siguiente nodo del ultimo ahora es el nuevo nodo

        this.last = newNode; // Ahora el nuevo nodo es el ultimo
        this.size++;
    }

    public T dequeue() { // Pop
        if (this.isEmpty())
            throw new IllegalArgumentException("- QueueError: La cola esta vacia.");

        T data = this.front.getData(); // Se toma el dato a retornar
        this.front = this.front.getNext();

        if (this.front == null)
            this.last = null;

        this.size--;
        return data;
    }

    public boolean isEmpty() {
        return this.front == null && this.size == 0;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        Node<T> current = front;
        while (current != null) {
            value.append(current.getData() + "\n");
            current = current.getNext();
        }

        return value.toString();
    }
}