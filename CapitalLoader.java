import java.util.HashMap;
/**
 * relates states to their capitals
 */
public class CapitalLoader implements DataProvider<String, String> {
    private HashMap<String, String> _dir; //directory of capitals
    private int _numFetches; //number of times data has been fetched from the loader

    /**
     * Constructs the CapitalLoader using a premade directory of states and capitals
     * @param stateDir directory of states and capitals
     */
	public CapitalLoader (HashMap<String, String> stateDir) {
        _dir = stateDir;
        _numFetches = 0;
    }

    /**
     * Constructs the CapitalLoader without any predifined capital and state pairs
     */
    public CapitalLoader () {
        _dir = new HashMap<String, String>();
        _numFetches = 0;
    }

    /**
     * Gets the specified state given the key (state)
     * @param key key of capital (state)
     * @return the value (capital) associated with the specified key (state)
     */
	public String get (String key) {
        _numFetches++;
        return _dir.get(key);
	}

    /**
     * Gets the number of times data has been fetched from the CapitalLoader
     * @return the number of times data has been fetched from the CapitalLoader
     */
    public int getNumFetches(){
        return _numFetches;
    }

    /**
     * Adds a State and Capital pair to the directory associated with the CapitalLoader
     * @param key state
     * @param value capital
     */
    public void put(String key, String value){
        _dir.put(key, value);
    }
}