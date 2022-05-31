package sayTheSpire.localization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import org.bigtesting.interpolatd.Interpolator;
import org.bigtesting.interpolatd.Substitutor;

public class LocalizationContext {

    private static Logger logger = LogManager.getLogger(LocalizationContext.class.getName());

    private LocalizationManager manager;
    private HashMap<String, Object> data;
    private Interpolator<HashMap<String, Object>> interpolator;
    private String root;

    public LocalizationContext(LocalizationManager manager, String root) {
        this.manager = manager;
        this.root = root;
        this.data = new HashMap();
        this.interpolator = new Interpolator();
        this.interpolator.when().enclosedBy("{").and("}").handleWith(new Substitutor<HashMap<String, Object>>() {
            public String substitute(String captured, HashMap<String, Object> arg) {
                Object result = arg.getOrDefault(captured, null);
                if (result == null)
                    return captured;
                return result.toString();
            }
        });
    }

    public String localize(String path) {
        String result;
        if (this.root == null || this.root.equals(""))
            result = this.manager.getStringAtPath(path);
        else
            result = this.manager.getStringAtPath(this.root + "." + path);
        if (result == null) {
            logger.warn("Could not find localization string at path " + path + ".");
            return null;
        }
        return this.interpolator.interpolate(result, this.data);
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public void putAll(HashMap<String, Object> data) {
        this.data.putAll(data);
    }
}
