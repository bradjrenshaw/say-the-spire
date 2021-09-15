package sayTheSpire.ui.input;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import com.badlogic.gdx.Input.Keys;
import java.util.HashSet;

public class InputMapping {

  public enum Modifiers {
    CONTROL,
    SHIFT,
    ALT
  };

  private String actionName;
  private String inputType;
  private HashSet<Modifiers> modifiers;
  private int keycode;
  private Boolean isDefault;

  public InputMapping(
      String actionName, String inputType, Boolean isDefault, HashSet<Modifiers> modifiers, int keycode) {
    this.actionName = actionName;
    this.inputType = inputType;
    this.isDefault = isDefault;
    this.modifiers = modifiers;
    this.keycode = keycode;
  }

  public InputMapping(
      String actionName, String inputType, Boolean isDefault, HashSet<Integer> keycodes) {
    this.actionName = actionName;
    this.inputType = inputType;
    this.isDefault = isDefault;
    this.modifiers = new HashSet();
    switch (inputType) {
      case "keyboard":
        this.keycode = -4000; // hacky
        for (int testCode : keycodes) {
          if (testCode == Keys.CONTROL_LEFT || testCode == Keys.CONTROL_RIGHT)
            this.modifiers.add(Modifiers.CONTROL);
          else if (testCode == Keys.SHIFT_LEFT || testCode == Keys.SHIFT_RIGHT)
            this.modifiers.add(Modifiers.SHIFT);
          else if (testCode == Keys.ALT_LEFT || testCode == Keys.ALT_RIGHT)
            this.modifiers.add(Modifiers.ALT);
          else {
            if (this.keycode != -4000)
              throw new RuntimeException(
                  "Tried to assign multiple non-modifier keycodes to keyboard InputMapping.");
            this.keycode = testCode;
          }
        }
        break;
      case "controller":
        if (keycodes.size() != 1) {
          throw new RuntimeException(
              "Tried to assign incorrect number of keycodes to controller mapping.");
        }
        this.keycode = (int) keycodes.toArray()[0];
        break;
      default:
        throw new RuntimeException("Invalid InputMapping type " + inputType + ".");
    }
  }

  public InputMapping(String actionName, String inputType, Boolean isDefault, int keycode) {
    this(actionName, inputType, isDefault, new HashSet<Modifiers>(), keycode);
  }

  public InputMapping(JSONObject mappingObj) {
    String actionName = (String)mappingObj.get("actionName");
    String inputType = (String)mappingObj.get("inputType");
    JSONArray modifiersArray = (JSONArray)mappingObj.get("modifiers");
    
    //Yes this really is required
    int keycode = ((Long)mappingObj.get("keycode")).intValue();
    HashSet<Modifiers> modifiers = new HashSet();
    for (Object obj:modifiersArray) {
      switch((String)obj) {
        case "CONTROL":
        modifiers.add(Modifiers.CONTROL);
        break;
        case "SHIFT":
        modifiers.add(Modifiers.SHIFT);
        break;
  case "ALT":
        modifiers.add(Modifiers.ALT);
        break;
      }
    }
    this.actionName = actionName;
    this.inputType = inputType;
    this.modifiers = modifiers;
    this.keycode = keycode;
    this.isDefault = false;
  }

  public String getActionName() {
    return this.actionName;
  }

  public String getInputType() {
    return this.inputType;
  }

  public Boolean getIsDefault() {
    return this.isDefault;
  }

  public int getKeycode() {
    return this.keycode;
  }

  public HashSet<Modifiers> getModifiers() {
    return this.modifiers;
  }

  public JSONObject toJSONObject() {
    JSONObject obj = new JSONObject();
    obj.put("actionName", this.getActionName());
    obj.put("inputType", this.getInputType());
    JSONArray modifiersArray = new JSONArray();
    for (Modifiers modifier:this.modifiers) {
     modifiersArray.add(modifier.toString());
    }
    obj.put("modifiers", modifiersArray);
   obj.put("keycode", this.getKeycode());
   return obj;
  }
}
