import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	private static class Node<U> {
        private U _data;
        private Node<U> _next;
        private Node<U> _previous;
        public Node (U data, Node<U> previous, Node<U> next){
            _data = data;
            _next = next;
            _previous = previous;
        }
		public Node() {
			_data = null;
			_next = null;
			_previous = null;
		}
    }
	private DataProvider<T, U> _provider;
	private int _capacity;
	private HashMap<T, Node<U>> _cache;
	private int _numMisses;
	//instance variables for doubly-linked list functionality
    private int _numElements;
	private Node<U> _dummyHead;
	private Node<U> _dummyTail;

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity must be at least 1");
		}
		_provider = provider;
		_capacity = capacity;
		_cache = new HashMap<T, Node<U>>();
		_numMisses = 0;
		
		// instantiate doubly-linked list fields
		_dummyHead = new Node<U>();
		_dummyTail = new Node<U>();
		_dummyHead._next = _dummyTail;
		_dummyTail._previous = _dummyHead;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */

	
	public U get(T key) {
		// hit
		if(isInCache(key)) {
			//since remove(T key) returns the node removed, it can be used at the same time as adding
			U data = remove(key)._data;
			add(key, data);
			return _cache.get(key)._data;
		}
		// miss
		else {
			add(key, _provider.get(key));
			//eviction
			if(_numElements > _capacity) {
				removeFirst();
			}
			_numMisses++;
			return _cache.get(key)._data;
		}
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses() {
		return _numMisses;
	}

	/**
	 * Returns whether the object with the specified key is contained in the cache.
	 * @param the key of the object
	 * @return whether the object is contained in the cache.
	 */
	public boolean isInCache (T key) {
		return _cache.containsKey(key);
	}

	public boolean add (T key, U data){
		Node<U> node = new Node<U>(data, null, null);
		_dummyTail._previous._next = node;
		node._previous = _dummyTail._previous;
		_dummyTail._previous = node;
		node._next = _dummyTail; 
        _numElements++;
		_cache.put(key, node);
        return true;
	}

	public Node<U> removeFirst () {
		Node<U> storedHead = _dummyHead._next;
		_dummyHead._next = _dummyHead._next._next;
		_numElements--;
		_cache.remove(storedHead);
		return storedHead;
	}

	public Node<U> remove (T key){
		Node<U> node = _cache.get(key);
		System.out.println(node._data);
		node._previous._next = node._next;
		node._next._previous = node._previous;
		_numElements--;
		_cache.remove(key);
		return node;
	}

	/*public boolean DLinkedListAdd (U data){
		Node<U> node = new Node<T>(data, null, null);
		_dummyTail._previous._next = node;
		_dummyTail._previous = node;
		node._next = _dummyTail; 
        _numElements++;
        return true;
	}

	public T DLinkedListRemoveFirst () {
		Node<T> storedHead = _dummyHead._next;
		_dummyHead._next = _dummyHead._next._next;
		_numElements--;
		return storedHead._data;
	}

	public T DLinkedListRemove(T key) {
        
    }*/
}