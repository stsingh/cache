import java.util.HashMap;

public class CapitalLoader implements DataProvider<String, String> {
    private HashMap<String, String> _dir;
    private int _numFetches;
	public CapitalLoader (HashMap<String, String> wordDir) {
        _dir = wordDir;
        _numFetches = 0;
    }
    public CapitalLoader () {
        _dir = new HashMap<String, String>();
        _numFetches = 0;
    }

	public String get (String key) {
        _numFetches++;
        return _dir.get(key);
	}
    public int getNumFetches(){
        return _numFetches;
    }
    public void put(String key, String value){
        _dir.put(key, value);
    }
}