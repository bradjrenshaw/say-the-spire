package sayTheSpire;

import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import sayTheSpire.ui.input.InputConfig;

public class STSConfig {

  private Toml settingsToml;
  private InputConfig inputConfig;

  public STSConfig() {
    File dir = new File(getDirectoryPath());
    dir.mkdirs();
    this.loadInput();
    this.loadSettings();
  }

  private void loadInput() {
    File file = new File(getInputFilePath());
    try {
      Toml inputToml = new Toml().read(file);
      this.inputConfig = inputToml.to(InputConfig.class);
      System.out.println("STSConfig: Input settings file loaded successfully.");
    } catch (Exception e) {
      System.out.println("STSConfig: No input mappings file found, using defaults.");
      this.inputConfig = new InputConfig();
    }
  }

  private void loadSettings() {
    File file = new File(getSettingsFilePath());
    HashMap<String, Object> defaults = this.getDefaults();
    try {
      HashMap<String, Object> fileSettings =
          (HashMap<String, Object>) new Toml().read(file).toMap();
      merge(defaults, fileSettings);
      this.settingsToml = new Toml().read(new TomlWriter().write(defaults));
      System.out.println("STSConfig: Config loaded from existing file.");
    } catch (Exception e) {
      System.out.println("STSConfig: No config file found, using defaults.");
      this.settingsToml = new Toml().read(new TomlWriter().write(defaults));
    }
  }

  public static void merge(HashMap<String, Object> base, HashMap<String, Object> merger) {
    for (Map.Entry<String, Object> entry : merger.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (base.containsKey(key) && base.get(key) instanceof HashMap && value instanceof HashMap) {
        merge((HashMap<String, Object>) base.get(key), (HashMap<String, Object>) value);
      } else {
        base.put(key, value);
      }
    }
  }

  public void save() throws IOException {
    TomlWriter writer = new TomlWriter();
    File file = new File(getSettingsFilePath());
    writer.write(this.settingsToml.toMap(), file);
    File inputFile = new File(getInputFilePath());
    writer.write(this.inputConfig, inputFile);
  }

  public static HashMap<String, Object> getDefaults() {
    HashMap<String, Object> defaults = new HashMap();
    HashMap<String, Object> uiDefaults = new HashMap();
    uiDefaults.put("read_positions", true);
    uiDefaults.put("read_banner_text", true);
    uiDefaults.put("read_proceed_text", true);
    HashMap<String, Object> mapDefaults = new HashMap();
    mapDefaults.put("read_reversed_paths", true);

    HashMap<String, Object> combatDefaults = new HashMap();
    combatDefaults.put("block_text", true);
    combatDefaults.put("buff_debuff_text", true);

    HashMap<String, Object> inputDefaults = new HashMap();
    inputDefaults.put("virtual_input", false);

    defaults.put("ui", uiDefaults);
    defaults.put("map", mapDefaults);
    defaults.put("combat", combatDefaults);
    defaults.put("input", inputDefaults);
    return defaults;
  }

  public static String getDirectoryPath() {
    return ConfigUtils.CONFIG_DIR + File.separator + "sayTheSpire" + File.separator;
  }

  public static String getInputFilePath() {
    return getDirectoryPath() + "input.ini";
  }

  public InputConfig getInputConfig() {
    return this.inputConfig;
  }

  public static String getSettingsFilePath() {
    return getDirectoryPath() + "settings.ini";
  }

  public String getString(String key) {
    return this.settingsToml.getString(key);
  }

  public String getString(String key, String defaultValue) {
    return this.settingsToml.getString(key, defaultValue);
  }

  public Long getLong(String key) {
    return this.settingsToml.getLong(key);
  }

  public Long getLong(String key, Long defaultValue) {
    return this.settingsToml.getLong(key, defaultValue);
  }

  public Boolean getBoolean(String key) {
    return this.settingsToml.getBoolean(key);
  }

  public Boolean getBoolean(String key, Boolean defaultValue) {
    return this.settingsToml.getBoolean(key, defaultValue);
  }

  public Double getDouble(String key) {
    return this.settingsToml.getDouble(key);
  }

  public Double getDouble(String key, Double defaultValue) {
    return this.settingsToml.getDouble(key, defaultValue);
  }
}
