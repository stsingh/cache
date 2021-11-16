import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	
	//Inner node class
	private static class Node<T, U> {
		private T _key; //corresponding key of node in Hashmap
        private U _data; //data contained within node pertaining to the provider
		private Node<T, U> _previous; //the previous node in the LinkedList of nodes
        private Node<T, U> _next; //the next node in the LinkedList of nodes

		/**
		 * Constructs a node with the specified key, data, previous node, and next node.
		 * @param key corresponding key of node in Hashmap
		 * @param data data contained within node pertaining to the provider
		 * @param previous the previous node in the LinkedList of nodes
		 * @param next the next node in the LinkedList of nodes
		 */
        public Node (T key, U data, Node<T, U> previous, Node<T, U> next){
            _key = key;
			_data = data;
            _next = next;
            _previous = previous;
        }
		/**
		 * Constructs a default node with no specified key, data, previous node, and next node.
		 */
		public Node() {
			_key = null;
			_data = null;
			_next = null;
			_previous = null;
		}
    }

	//Instance variables
	private DataProvider<T, U> _provider; //data provider of caching system
	private int _capacity; //capacity of the cache
	private HashMap<T, Node<T, U>> _cache; //overarching hashmap of the cache
	private int _numMisses; //number of misses when calling for data
	//instance variables for doubly-linked list functionality
    private int _numElements; //number of elements in the LinkedList
	private Node<T, U> _dummyHead; //the dummy head of the LinkedList
	private Node<T, U> _dummyTail; //the dummy tail of the LinkedList

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
		//link dummy nodes together to start LinkedList
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
			//since this is a hit, the node at the specified key contains the Most Recently Used 
			//data in the cache, meaning it is moved to the back of the LinkedList
			U recentData = remove(key)._data; //remove node from current spot in list
			add(key, recentData); //add node to the end of the LinkedList
			return _cache.get(key)._data; //return data of node specified
		}
		// miss
		else {
			//since the cache does not contain the specified node, the node is added to the end of the LinkedList
			add(key, _provider.get(key)); 
			//check if there is a need for eviction due to LRU logic (capacity has been reached)
			if(_numElements > _capacity) {
				removeFirst(); //removed the LRU node
			}
			_numMisses++;
			return _cache.get(key)._data; //return data of node specified
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

	/**
	 * Adds a new node to the LinkedList as well as to the overarching Hashmap
	 * @param key key of the node
	 * @param data data of the node
	 * @return true on success
	 */
	private boolean add (T key, U data){
		Node<T, U> node = new Node<T, U>(key, data, null, null); //instantiate new node to be added

		//add node to LinkedList by inserting it before the dummytail and after the former last non-dummy node
		_dummyTail._previous._next = node; 
		node._previous = _dummyTail._previous;
		_dummyTail._previous = node;
		node._next = _dummyTail;

        _numElements++;
		_cache.put(key, node); //add node to overarching Hashmap
        return true;
	}

	/**
	 * Removes the first non-dummy node from the LinkedList and overarching Hashmap
	 * @return the removed node
	 */
	private Node<T, U> removeFirst () {
		Node<T, U> storedHead = _dummyHead._next; //store the former head of the LinkedList
		T firstKey = storedHead._key; //store the key of the former head

		//remove the former head by linked the dummyhead with the new head (former second of the LinkedList)
		_dummyHead._next = _dummyHead._next._next;
		_dummyHead._next._previous = _dummyHead;

		_cache.remove(firstKey); //remove node from overarching Hashmap
		_numElements--;
		return storedHead; //return the removed head
	}

	/**
	 * Removes a node from the LinkedList and overarching Hashmap given a key
	 * @param key key of the node
	 * @return the node that was removed
	 */
	private Node<T, U> remove (T key){
		Node<T, U> node = _cache.get(key); //store the node to be removed

		//remove node from the LinkedList by linking its previous and next nodes together
		node._previous._next = node._next;
		node._next._previous = node._previous;

		_numElements--;
		_cache.remove(key); //remove node from overarching Hashmap
		return node; //return the removed node
	}

}
