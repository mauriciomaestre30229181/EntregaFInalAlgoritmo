package types;

public class List<T> {
    private Node<T> head;
    private int size;

    public List() {
        this.head = new Node<>();
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public Node<T> getNode() {
        if (this.IsEmpty())
            throw new IllegalArgumentException("- List-Error: Lista vacia, nada que eliminar.");

        return this.head.getNext();
    }

    public Node<T> getNodeByData(T data) {
        if (data == null)
            throw new IllegalArgumentException("- List-Error: El dato es nulo, no puede realizar la busqueda.");

        if (this.IsEmpty())
            throw new IllegalArgumentException("- List-Error: Lista vacia, nada que eliminar.");

        Node<T> head = this.head;
        Node<T> found = head.getNext();

        while (found != null){
            if (found.getData().equals(data))
                return found;

            found = found.getNext();
        }

        return null;
    }

    public Node<T> getNodeByPos(int pos) {
        if (this.IsEmpty())
            throw new IllegalArgumentException("- List-Error: Lista vacia, nada que eliminar.");

        if (pos <= 0)
            return this.getNode();

        int iterator = 0;
        Node<T> head = this.head;
        Node<T> prev = head;
        Node<T> found = head.getNext();

        while (found != null && iterator < pos) {
            prev = found;
            found = found.getNext();
            iterator++;
        }

        if (iterator == this.size)
            return prev;

        return found;
    }

    public void insert(T data) {
        Node<T> head = this.head;
        Node<T> node = new Node<>(data);

        // No existe elemento
        if (head.getNext() == null) {
            head.setNext(node);
        } else { // Si elijo la posicion 0 o negativo
            Node<T> aux = head.getNext();
            head.setNext(node);
            node.setNext(aux);
        }

        this.size++;
    }

    public void insert(T data, int pos) {
        // Si elijo la posicion 0 o negativo
        if (pos <= 0) {
            this.insert(data);
            return;
        }

        // Mayor a la posicion 1
        Node<T> head = this.head;
        Node<T> node = new Node<>(data);

        int iterator = 0;
        Node<T> prev = head;
        Node<T> next = head.getNext(); // 0 primer elemento

        while (next != null && iterator < pos){
            prev = next;
            next = next.getNext();
            iterator++;
        }

        prev.setNext(node);
        if (next != null)
            node.setNext(next);

        this.size++;
    }

    public void remove() {
        if (this.IsEmpty())
            throw new IllegalArgumentException("- List-Error: Lista vacia, nada que eliminar.");

        Node<T> head = this.head;
        Node<T> nodeRemove = head.getNext();
        head.setNext(nodeRemove.getNext());
        nodeRemove = null;
        this.size--;
    }

    public void remove(int pos) {
        if (pos < 0 || pos > this.size)
            throw new IllegalArgumentException("- List-Error: Invalid pos.");

        if (pos == 0) {
            this.remove();
            return;
        }

        // Mayor a la posicion 1
        Node<T> head = this.head;

        // [0, 1, 2, 3]
        int iterator = 0;
        Node<T> prev = head;
        Node<T> next = head.getNext(); // 0 primer elemento

        while (next != null && iterator < pos) {
            prev = next; // [0]
            next = next.getNext(); // [1]
            iterator++;
        }

        prev.setNext(next.getNext());
        next = null;
        this.size--;
    }

    public void remove(T data){
        if (data == null)
            throw new IllegalArgumentException("- List-Error: El dato es nulo, no puede realizar la busqueda.");

        if (this.IsEmpty())
            throw new IllegalArgumentException("- List-Error: Lista vacia, nada que eliminar.");

        // Mayor a la posicion 1
        Node<T> head = this.head;

        // [0, 1, 2, 3]
        Node<T> prev = head;
        Node<T> next = head.getNext(); // 0 primer elemento
        while (!next.getData().equals(data)) {
            prev = next; // [0]
            next = next.getNext(); // [1]
        }

        prev.setNext(next.getNext());
        next = null;
        this.size--;
    }

    // Operator
    public boolean IsEmpty() {
        return this.head.getNext() == null || this.size == 0;
    }

    public void showList() {
        if (this.size == 0) {
            System.out.println("|::");
            return;
        }

        Node<T> aux = this.head;
        while (aux.getNext() != null) {
            System.out.print(aux.getNext());
            if (aux.getNext() != null)
                System.out.print(" -> ");

            aux = aux.getNext();
        }

        System.out.print("|::");
    }
}