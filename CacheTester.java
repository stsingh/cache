import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 * IMPORTANT: make sure that your tests access ONLY the methods in the Cache interface! Do NOT
 * try to access any methods or variables that are specific to your own LRUCache!
 */
public class CacheTester {
	@Test
	public void leastRecentlyUsedIsCorrect () {
		// In this (arbitrary) example, the DataProvider associates integer keys with String values.
		final DataProvider<Integer,String> provider = new WordLoader(null); // Need to instantiate an actual DataProvider
		final Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 5);
	}
}
