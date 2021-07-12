package sayTheSpire.buffers;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A buffer represents a list of data about a given UI Element or in game display. Buffers are often
 * associated with a particular in game object.
 */
public class Buffer {

  private String name;
  protected int position;
  private Boolean enabled;
  private ArrayList<String> contents;

  public Buffer() {
    this("");
  }

  public Buffer(String name) {
    this.contents = new ArrayList<String>();
    this.name = name;
    this.position = 0;
    this.enabled = false;
  }

  public void add(String toAdd) {
    if (toAdd == null) return;
    this.contents.add(toAdd);
  }

  public void addMany(Collection<String> collection) {
    for (String text : collection) {
      this.add(text);
    }
  }

  public void clear() {
    this.contents.clear();
  }

  public Boolean isEmpty() {
    return this.contents.isEmpty();
  }

  public Boolean movePosition(int newPosition) {
    if (newPosition < 0 || newPosition >= this.contents.size()) return false;
    this.position = newPosition;
    return true;
  }

  public Boolean moveToNext() {
    return this.movePosition(this.position + 1);
  }

  public Boolean moveToPrevious() {
    return this.movePosition(this.position - 1);
  }

  /** This method is called by the BufferManager. Do not call this method manually. */
  public void update() {
    return;
  }

  public String getCurrentItem() {
    if (this.position >= this.contents.size()) this.position = 0;
    if (this.contents.isEmpty()) return null;
    return this.contents.get(this.position);
  }

  public int size() {
    return this.contents.size();
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getObject() {
    return null;
  }

  public void setObject(Object object) {
    return;
  }

  public int getPosition() {
    return this.position;
  }
}
