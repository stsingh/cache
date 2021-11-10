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
    public Boolean remove(T key) {
        Node<T> cursor = _head;
        if(cursor == key){
            _head = cursor._next;
            _numElements--;
            return true;
        }
        for(int i = 0 ; i < _numElements ; i ++){
            if(cursor._next == key){
                cursor._next = cursor._next._next;
                if(_tail._next == key) {
                    _tail = cursor;
                }
                _numElements--;
                return true;
            }
            cursor = _head._next;
        }
        return null;
    }
    public int size() {
        return _numElements;
    }
}
