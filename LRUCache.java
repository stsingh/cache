import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	private DataProvider<T, U> _provider;
	private int _capacity;
	private HashMap<T, U> _cache;
	private NewLinkedList<T> _list;
	private int _numMisses;

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
		_cache = new HashMap<T, U>();
		_list = new NewLinkedList<T>();
		_numMisses = 0;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get(T key) {
		if(isInCache(key)) {
			_list.remove(key);
			_list.add(key);
			return _cache.get(key);
		}
		else {
			_cache.put(key, _provider.get(key));
			_list.add(key);
			if(_list.size() > _capacity) {
				T toRemove = _list.remove();
				_cache.remove(toRemove);
			}
			_numMisses++;
			return _cache.get(key);
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
}