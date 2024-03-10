package sayTheSpire;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils;
import java.io.File;
import java.io.IOException;
import sayTheSpire.config.Setting;
import sayTheSpire.config.SettingsManager;
import sayTheSpire.ui.input.InputActionCollection;

public class STSConfig {

    private static final Logger logger = LogManager.getLogger(STSConfig.class.getName());

    private SettingsManager settings;
    private JsonObject inputObj;

    public STSConfig() {
        File dir = new File(getDirectoryPath());
        dir.mkdirs();
        this.loadInput();
        this.loadSettings();
    }

    private void loadInput() {
        try {
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(new FileReader(getInputFilePath()));
            this.inputObj = root.getAsJsonObject();
            logger.info("Input settings file loaded successfully.");
        } catch (Exception e) {
            logger.info("Issue loading input mappings file.");
            e.printStackTrace();
            this.inputObj = null;
        }
    }

    private void loadSettings() {
        File file = new File(getSettingsFilePath());
        this.settings = new SettingsManager();
        Boolean shouldCreateFile = false;
        try {
            if (file.exists()) {
                JsonParser parser = new JsonParser();
                JsonElement root = parser.parse(new FileReader(getSettingsFilePath()));
                this.settings.fromJsonElement(root);
                logger.info("Config loaded from existing file.");
            } else {
                logger.info("settings.json does not exist; creating.");
                shouldCreateFile = true;
            }
        } catch (Exception e) {
            logger.error("Issue parsing settings.json; recreating file with defaults.", e);
            shouldCreateFile = true;
        } finally {
            if (shouldCreateFile) {
                this.saveSettings();
            }
        }
    }

    public void save() throws IOException {
        this.saveSettings();
        this.saveInput();
    }

    public void saveInput() {
        try (FileWriter file = new FileWriter(getInputFilePath())) {
            InputActionCollection actions = Output.inputManager.getActionCollection();
            JsonElement json = actions.toJsonElement();
            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
            file.write(gson.toJson(json));
            file.flush();
            logger.info("Successfully wrote input mappings file.");
        } catch (Exception e) {
            logger.error("Error writing to input mappings file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveSettings() {
        try (FileWriter file = new FileWriter(getSettingsFilePath())) {
            JsonElement json = settings.toJsonElement();
            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
            file.write(gson.toJson(json));
            file.flush();
            logger.info("Successfully wrote settings file.");
        } catch (Exception e) {
            logger.error("Error writing to settings file.", e);
        }
    }

    public HashSet<String> getExcludedTypenames() {
        return new HashSet<String>(this.getList("ui.exclude_read_typenames"));
    }

    public static String getDirectoryPath() {
        return ConfigUtils.CONFIG_DIR + File.separator + "sayTheSpire" + File.separator;
    }

    public static String getInputFilePath() {
        return getDirectoryPath() + "input.json";
    }

    public JsonObject getInputObj() {
        return this.inputObj;
    }

    public SettingsManager getSettings() {
        return this.settings;
    }

    public static String getSettingsFilePath() {
        return getDirectoryPath() + "settings.json";
    }

    public String getString(String key) {
        Setting setting = this.settings.getBaseSetting(key);
        return setting.getValue().toString();
    }

    public Boolean getBoolean(String key) {
        Setting setting = this.settings.getBaseSetting(key);
        Object value = setting.getValue();
        if (!(value instanceof Boolean)) {
            throw new RuntimeException("Value is not of type Boolean.");
        }
        return (Boolean) value;
    }

    public List getList(String key) {
        Setting setting = this.settings.getBaseSetting(key);
        Object value = setting.getValue();
        if (!(value instanceof List)) {
            throw new RuntimeException("Value is not of type List.");
        }
        return (List<String>) value;
    }

}
