package sayTheSpire.utils;

import java.util.Map;
import java.util.HashMap;

public class MapBuilder<K, V> {
 
    HashMap<K, V> entries;

    public MapBuilder() {
        entries = new HashMap();
    }

    public MapBuilder put(K k, V v) {
        this.entries.put(k, v);
        return this;
    }

    public HashMap toHashMap() {
        return this.entries;
    }
}
