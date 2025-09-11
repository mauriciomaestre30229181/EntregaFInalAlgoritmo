public class Node<T> {
    private T data;
    Node<T> next;


    public Node(){
        this.data = null;
        this.next = null;
    }

    public Node(T data){
        if (data == null){
            throw new IllegalArgumentException("- RefError: Data no puede ser 0. ");
        }
        this.data = data;
        this.next = null;
    }

    public Node(Node<T> copy){
        if (copy == null){
            throw new IllegalArgumentException("- RefError: Copia fallida por nodo nulo. ");
        }
        this.data = copy.getData();
        this.next = copy.getNext();
    }

    //setters
    public void setData(T data) {
        if (data == this.data){
            throw new IllegalArgumentException("- RefError: Tiene el mismo dato. ");
        }

        if (data == null){
            throw new IllegalArgumentException("- RefError: El dato es nulo. ");
        }

        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    //getters
    public T getData() {
        return this.data;
    }

    public Node<T> getNext() {
        return this.next;
    }

    //operator
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


    @Override
    public String toString() {
        return "{Valor = "+this.data+"}";
    }
}
