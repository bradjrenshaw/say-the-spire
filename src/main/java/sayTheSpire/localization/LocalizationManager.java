package sayTheSpire.localization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sayTheSpire.Output;

public class LocalizationManager {

    private static Logger logger = LogManager.getLogger(LocalizationManager.class.getName());

    private JsonElement english;
    private JsonElement lang;
    private JsonElement temp;

    public LocalizationManager() {
        this.english = null;
        this.lang = null;
        this.temp = null;
        this.load();
    }

    public String getStringAtPath(String path) {
        JsonElement element = this.getElementAtPath(path);
        if (element == null)
            return null;
        return element.getAsString();
    }

    public JsonElement getElementAtPath(String path) {
        String[] splitPath = path.split("\\.");
        JsonElement result = null;
        if (this.temp != null)
            result = this.getElementAtSpecificPathArray(splitPath, this.temp);
        if (result == null && this.lang != null)
            result = this.getElementAtSpecificPathArray(splitPath, this.lang);
        if (result == null)
            result = this.getElementAtSpecificPathArray(splitPath, this.english);
        return result;
    }

    private JsonElement getElementAtSpecificPathArray(String[] path, JsonElement root) {
        JsonElement element = root;
        for (String key : path) {
            if (!element.isJsonObject())
                return null;
            JsonObject obj = element.getAsJsonObject();
            if (!obj.has(key))
                return null;
            element = obj.get(key);
        }
        return element;
    }

    private void load() {
        JsonParser parser = new JsonParser();
        try {
            String defaultJson = loadJson("localization/eng/say-the-spire.json");
            this.english = parser.parse(defaultJson);
        } catch (Exception e) {
            logger.error("Fatal error: Unable to load default language json.", e);
            return;
        }
        logger.info("Localization manager successfully loaded.");
    }

    public String localize(String path, Object... args) {
        LocalizationContext context = this.getContext("");
        if (args.length % 2 == 0) {
            for (int i = 0; i < args.length; i += 2) {
                String key = (String) args[i];
                Object value = args[i + 1];
                if (key == null || value == null) {
                    Output.text("key or value is null", false);
                    continue;
                }
                context.put(key, value);
            }
        }
        return context.localize(path);
    }

    public LocalizationContext getContext(String path) {
        return new LocalizationContext(this, path);
    }

    private static String loadJson(String path) {
        try {
            return Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
        } catch (GdxRuntimeException e) {
            return null;
        }
    }
}
