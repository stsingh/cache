import java.util.function.BooleanSupplier;

/**
 * CS 2103 2021 B-term (Whitehill)
 * A cache that associates keys with values.
 */
interface Cache<T, U> extends DataProvider<T, U> {
	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	int getNumMisses ();
	/**
	 * Returns whether the object with the specified key is contained in the cache.
	 * @param the key of the object
	 * @return whether the object is contained in the cache.
	 */
	boolean isInCache (T key);
    int getNumElements();
}
