package sayTheSpire;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import com.moandjiezana.toml.Toml;
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils;
import com.moandjiezana.toml.TomlWriter;


public class STSConfig {

    private Toml toml;

    public STSConfig() {
        File dir = new File(getDirectoryPath());
        dir.mkdirs();
        File file = new File(getFilePath());
        this.toml = new Toml(getDefaults());
        try {
            Toml fileToml = this.toml.read(file);
            if (fileToml != null) this.toml = fileToml;
                System.out.println("STSConfig: Config loaded from existing file.");
        }catch (Exception e) {
            System.out.println("STSConfig: No config file found, using defaults.");
        }
            }

    public void save() throws IOException {
        TomlWriter writer = new TomlWriter();
        File file = new File(getFilePath());
        writer.write(this.toml.toMap(), file);
    }

    public static Toml getDefaults() {
        HashMap<String, Object> defaults = new HashMap();
        HashMap<String, Object> uiDefaults = new HashMap();
        uiDefaults.put("read_positions", true);
        uiDefaults.put("read_banner_text", true);
        uiDefaults.put("read_proceed_text", true);
        HashMap<String, Object> mapDefaults = new HashMap();
        mapDefaults.put("read_reversed_paths", true);
                defaults.put("ui", uiDefaults);
        defaults.put("map", mapDefaults);

        Toml defaultToml = new Toml();
        String defaultCode = (new TomlWriter()).write(defaults);
        return defaultToml.read(defaultCode);
    }

    public static String getDirectoryPath() {
        return ConfigUtils.CONFIG_DIR + File.separator + "sayTheSpire" + File.separator;
    }

    public static String getFilePath() {
        return getDirectoryPath() + "settings.ini";
    }

    public String getString(String key) {
        return this.toml.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return this.toml.getString(key, defaultValue);
    }

    public Long getLong(String key) {
        return this.toml.getLong(key);
    }

    public Long getLong(String key, Long defaultValue) {
        return this.toml.getLong(key, defaultValue);
    }

    public Boolean getBoolean(String key) {
        return this.toml.getBoolean(key);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return this.toml.getBoolean(key, defaultValue);
    }

    public Double getDouble(String key) {
        return this.toml.getDouble(key);
    }

    public Double getDouble(String key, Double defaultValue) {
        return this.toml.getDouble(key, defaultValue);
    }
}
