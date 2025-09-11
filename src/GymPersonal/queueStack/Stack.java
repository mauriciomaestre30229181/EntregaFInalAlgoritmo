package GymPersonal.queueStack;

public class Stack<T> {
    private Node<T> stack;
    private int size;

    public Stack(){
        this.stack = null;
        this.size = 0;
    }

    public void push(T data){
        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.stack);
        this.stack = newNode;//el nuevo nodo ahora sera la sima

        this.size++;
    }

    public T pop(){//des stack
        if (this.isEmpty()){
            throw new IllegalArgumentException("- StackError: la pila esta vacia");
        }
        T data = this.stack.getData();
        this.stack = this.stack.getNext();

        this.size--;
        return data;
    }

    public T showStack(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("- StackError: la pila esta vacia");
        }
        return this.stack.getData();
    }

    public int getSize(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.stack == null && this.size == 0;
    }
}
