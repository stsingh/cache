public class NewLinkedList<T>{
    private static class Node<T> {
        private T _data;
        private Node<T> _next;
        public Node (T data, Node<T> next){
            _data = data;
            _next = next;
        }
    }
    private Node<T> _head;
    private Node<T> _tail;
    private int _numElements;
    public NewLinkedList () {
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
        Node<T> node = new Node<T>(val, null);
        if (_head == null) {
            _head = node;
            _tail = node;
        }
        else {
            _tail._next = node;
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
        _numElements--;
        return storedHead._data;
    }
    public Boolean remove(int index) {
        // prelim checks
        if (index < 0 || index >= _numElements)
            throw new IllegalArgumentException("bad index");
        else if(_numElements == 1) {
            _head = null;
            _tail = null;
        } 
        else if(index == 0) // index check
            _head = _head._next;
        else {
            Node<T> cursor = _head;
            for(int i = 0 ; i < index ; i ++){
                cursor = _head._next;
            }
            cursor._next = cursor._next._next;

            if(index == _numElements - 1) {
                _tail = cursor;
            }
        }
        _numElements--;
        return true;
    }
}
