package projects.GymPersonal.queueStack;

public class Queue<T> {
    private Node<T> front;
    private Node<T> last;
    private int size;

    public Queue(){
        this.front = null;
        this.last = null;
        this.size = 0;
    }


    public void enqueue(T data){//push
        if (data == null){
            throw new IllegalArgumentException("- QueueError: El dato es nulo, no pudo ser agregado a la cola. ");
        }

        Node<T> newNode = new Node<>(data);

        if (this.isEmpty()){
            this.front = newNode;//inicio de la cola nuevo nodo
        } else {
            this.last.setNext(newNode);//decimos: el siguiente nodo del ultimo ahora es el nuevo nodo
        }
        this.last = newNode;//ahora el nuevo nodo es el ultimo

        this.size++;
    }

    public T dequeue(){//pop
        if (this.isEmpty()){
            throw new IllegalArgumentException("- QueueError: la cola esta vacia");
        }

        T data = this.front.getData();// se toma el dato a retornar
        this.front = this.front.getNext();

        if (this.front == null){
            this.last = null;
        }

        this.size--;
        return data;
    }

    public T getPeek(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("- QueueError: la cola esta vacia");
        }
        return this.front.getData();
    }

    public int getSize(){
        return this.size;
    }

    public void showLast(){//Jamas deberia estar
        System.out.println(this.last);
    }

    public boolean isEmpty(){
        return this.front == null && this.size == 0;
    }
}
