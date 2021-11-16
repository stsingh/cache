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
		final CapitalLoader provider = new CapitalLoader(); //instantiate data provider
		// add data to data provider
		provider.put("Massachusetts", "Boston");
		provider.put("Texas", "Austin");
		provider.put("Wisconsin", "Cheyenne");
		provider.put("Maine", "Augusta");
		provider.put("Rhode Island", "Providence");
		provider.put("Vermont", "Montpelier");
		provider.put("Connecticut", "Hartford");
		provider.put("California", "Sacramento");
		//instantiate LRU Cache
		final Cache<String,String> cache = new LRUCache<String,String> (provider, 5); 
		//fill cache to capacity
		cache.get("Massachusetts");
		cache.get("Texas");
		cache.get("Wisconsin");
		cache.get("Maine");
		cache.get("Rhode Island");
		//assert that Massachusetts was in the cache in the first place
		assertTrue(cache.isInCache("Massachusetts")); 
		//go over capacity of cache
		cache.get("Vermont");
		cache.get("Connecticut");
		assertTrue(!cache.isInCache("Massachusetts"));//assert that massachusetts was evicted
		assertTrue(!cache.isInCache("Texas"));//assert that texas was evicted (additional test)
	}
	@Test
	public void cachingIsFunctional () {
		final CapitalLoader provider = new CapitalLoader(); //instantiate data provider
		//add data to data provider
		provider.put("Massachusetts", "Boston");
		provider.put("Texas", "Austin");
		//instantiate LRU Cache
		final Cache<String,String> cache = new LRUCache<String,String> (provider, 5); 
		cache.get("Massachusetts");
		// Test that the key has been cached
		assertTrue(cache.isInCache("Massachusetts"));
		cache.get("Massachusetts");
		// Test that the cache fetched the cached key, rather than the provider
		assertTrue(provider.getNumFetches() == 1); 
		// Change the key's value in the provider 
		provider.put("Massachusetts", "Worcester");
		// Test that the retrieved value is coming from the cache, rather than the provider
		assertTrue(cache.get("Massachusetts") == "Boston");
	}
	@Test
	public void numMissesIsFunctional() {
		final CapitalLoader provider = new CapitalLoader(); //instantiate data provider
		// add data to data provider
		provider.put("Massachusetts", "Boston");
		provider.put("Texas", "Austin");
		provider.put("Maine", "Augusta");
		//instantiate the LRU Cache
		final Cache<String,String> cache = new LRUCache<String,String> (provider, 5); 
		//call data through cache from data provider
		cache.get("Massachusetts");
		cache.get("Texas");
		cache.get("Maine");
		//Test that uncached keys are counted as misses
		assertTrue(cache.getNumMisses() == 3);
		//Test that cached keys are not counted as misses
		cache.get("Massachusetts");
		assertTrue(cache.getNumMisses() == 3);
	}
	@Test
	public void isInCacheIsFunctional() {
		final CapitalLoader provider = new CapitalLoader(); //instantiate data provider
		//instantiate the LRU Cache
		final Cache<String,String> cache = new LRUCache<String,String> (provider, 5);
		provider.put("Massachusetts", "Boston");
		// assert that the key does not get added to the cache when being added to the data provider
		assertTrue(!cache.isInCache("Massachusetts"));
		cache.get("Massachusetts");
		// assert that key has been cached after using dedicated method
		assertTrue(cache.isInCache("Massachusetts"));

	}
}
