package sayTheSpire.ui.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A utility class that provides a builder object to more easily create input mapping default
 * configs.
 */
public class MappingBuilder {

  private HashMap<String, ArrayList<InputMapping>> mappings;
  private InputMapping lastMapping;
  private ArrayList<InputMapping> currentActionList;
  private String currentActionName, defaultInputType;
  private Boolean isDefault;

  public MappingBuilder() {
    this.mappings = new HashMap();
    this.currentActionList = null;
    this.currentActionName = null;
    this.defaultInputType = "keyboard";
    this.lastMapping = null;
    this.isDefault = false;
  }

  public MappingBuilder action(String name) {
    if (!this.mappings.containsKey(name)) {
      this.mappings.put(name, new ArrayList<InputMapping>());
    }
    this.currentActionName = name;
    this.currentActionList = this.mappings.get(name);
    return this;
  }

  public MappingBuilder mapping(Integer... keys) {
    if (this.currentActionList == null) {
      throw new RuntimeException("MappingBuilder: Tried to add mapping to null action.");
    }
    if (keys.length <= 0) {
      throw new RuntimeException("MappingBuilder: empty mapping specified.");
    }
    HashSet<Integer> mappingCodes = new HashSet(Arrays.asList(keys));
    InputMapping mapping =
        new InputMapping(
            this.currentActionName, this.defaultInputType, this.isDefault, mappingCodes);
    this.lastMapping = mapping;
    this.currentActionList.add(mapping);
    return this;
  }

  public MappingBuilder setDefaultInputType(String type) {
    this.defaultInputType = type;
    return this;
  }

  public MappingBuilder setIsDefault(Boolean value) {
    this.isDefault = value;
    return this;
  }

  public HashMap<String, ArrayList<InputMapping>> toHashMap() {
    return this.mappings;
  }
}
