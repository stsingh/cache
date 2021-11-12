import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	private static class Node<T, U> {
		private T _key;
        private U _data;
        private Node<T, U> _next;
        private Node<T, U> _previous;
        public Node (T key, U data, Node<T, U> previous, Node<T, U> next){
            _key = key;
			_data = data;
            _next = next;
            _previous = previous;
        }
		public Node() {
			_key = null;
			_data = null;
			_next = null;
			_previous = null;
		}
    }
	private DataProvider<T, U> _provider;
	private int _capacity;
	private HashMap<T, Node<T, U>> _cache;
	private int _numMisses;
	//instance variables for doubly-linked list functionality
    private int _numElements;
	private Node<T, U> _dummyHead;
	private Node<T, U> _dummyTail;

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
		_cache = new HashMap<T, Node<T, U>>();
		_numMisses = 0;
		
		// instantiate doubly-linked list fields
		_dummyHead = new Node<T, U>();
		_dummyTail = new Node<T, U>();
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
			//eviction
			if(_numElements >= _capacity) {//check this! >= is silly
				removeFirst();
			}
			add(key, _provider.get(key));
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
		Node<T, U> node = new Node<T, U>(key, data, null, null);
		_dummyTail._previous._next = node;
		node._previous = _dummyTail._previous;
		_dummyTail._previous = node;
		node._next = _dummyTail; 
        _numElements++;
		_cache.put(key, node);
        return true;
	}

	public Node<T, U> removeFirst () {
		Node<T, U> storedHead = _dummyHead._next;
		T firstKey = storedHead._key;
		_dummyHead._next = _dummyHead._next._next;
		_dummyHead._next._previous = _dummyHead;
		_cache.remove(firstKey);
		_numElements--;
		return storedHead;
	}

	public Node<T, U> remove (T key){
		Node<T, U> node = _cache.get(key);
		node._previous._next = node._next;
		node._next._previous = node._previous;
		_numElements--;
		_cache.remove(key);
		return node;
	}

	public int getNumElements(){
		return _numElements;
	}
}