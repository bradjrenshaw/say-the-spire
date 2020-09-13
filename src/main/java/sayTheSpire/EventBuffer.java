package sayTheSpire;

import java.util.ArrayList;
import java.util.Collection;
import sayTheSpire.events.Event;
import sayTheSpire.events.EventManager;

public class EventBuffer extends Buffer {

  public EventBuffer(String name) {
    super(name);
  }

  public void add(String toAdd) {
    super.add(toAdd);
    this.movePosition(this.size() - 1);
  }

  public Boolean getEnabled() {
    return true;
  }

  public void setEnabled(Boolean enabled) {
    return;
  }
}
