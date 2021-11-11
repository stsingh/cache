public class DoublyLinkedList<T>{
    private static class Node<T> {
        private T _data;
        private Node<T> _next;
        private Node<T> _previous;
        public Node (T data, Node<T> previous, Node<T> next){
            _data = data;
            _next = next;
            _previous = previous;
        }
    }
    private Node<T> _head;
    private Node<T> _tail;
    private int _numElements;
    public DoublyLinkedList () {
        _head = null;
        _tail = null;
        _numElements = 0;
    }

    public T get (int index){
        if (index < 0 || index >= _numElements){
            throw new IllegalArgumentException("bad index");
        }
        Node<T> cursor = _head;
        for(int i = 0 ; i < index ; i ++){
            cursor = _head._next;
        }
        return cursor._data;
    }
    public boolean add (T val){
        Node<T> node = new Node<T>(val, null, null);
        if (_head == null) {
            _head = node;
            _tail = node;
        }   
        else {
            _tail._next = node;
            node._previous = _tail;
            _tail = node;
        }
        _numElements++;
        return true;
    }
    public T remove() {
        if(_head == null) 
            return null;
        Node<T> storedHead = _head;
        _head = _head._next;
        _head._previous = null;
        _numElements--;
        return storedHead._data;
    }

    //doubly linked list
    public Boolean remove(T key) {
        Node<T> cursor = _head;
        for(int i = 0 ; i < _numElements ; i ++){
            cursor = cursor._next;
            if(cursor._data.equals(key)){
                cursor._previous._next = cursor._next;
                cursor._next._previous = cursor._previous;
                _numElements--;
                return true;
            }
        }
        return false;
    }
    
    public int size() {
        return _numElements;
    }
}