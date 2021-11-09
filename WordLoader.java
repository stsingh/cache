public class WordLoader implements DataProvider<Integer, String> {
    private String[] _wordDir;
    private int _numFetches;
	public WordLoader (String[] wordDir) {
		_wordDir = wordDir;
        _numFetches = 0;
	}

	public String get(Integer key) {
        _numFetches++;
        if(key > _wordDir.length-1)
            throw new IllegalArgumentException("Out of Bounds");
		return _wordDir[key.intValue()];
	}

    public int getNumFetches() {
        return _numFetches;
    }
}