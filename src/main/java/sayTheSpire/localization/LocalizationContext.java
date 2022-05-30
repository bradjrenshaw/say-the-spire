package sayTheSpire.localization;

import java.util.HashMap;
import org.bigtesting.interpolatd.Interpolator;
import org.bigtesting.interpolatd.Substitutor;

public class LocalizationContext {

    private HashMap<String, Object> data;
    private Interpolator<HashMap<String, Object>> interpolator;

    public LocalizationContext() {
        this.data = new HashMap();
        this.interpolator = new Interpolator();
        this.interpolator.when().enclosedBy("{").and("}").handleWith(new Substitutor<HashMap<String, Object>>() {
            public String substitute(String captured, HashMap<String, Object> arg) {
                Object result = arg.getOrDefault(captured, null);
                if (result == null)
                    return null;
                return result.toString();
            }
        });
    }

    public String localize(String text) {
        return this.interpolator.interpolate(text, this.data);
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public void putAll(HashMap<String, Object> data) {
        this.data.putAll(data);
    }
}
