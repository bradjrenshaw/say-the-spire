package sayTheSpire;

import java.util.ArrayList;

public class BufferManager {

  private ArrayList<Buffer> buffers;
  private int position;

  public BufferManager() {
    this.buffers = new ArrayList<Buffer>();
    this.position = 0;
  }

  public void add(Buffer toAdd) {
    this.buffers.add(toAdd);
  }

  public Boolean enableBuffer(String name) {
    Buffer buffer = this.getBuffer(name);
    if (buffer == null) return false;
    buffer.setEnabled(true);
    return true;
  }

  public int moveToNext() {
    if (this.buffers.isEmpty()) {
      return -1; // No position
    }
    int currentPosition = this.position;
    for (int b = currentPosition + 1; b != position; b++) {
      if (b >= this.buffers.size()) {
        b = -1;
        continue;
      } else if (this.buffers.get(b).getEnabled()) {
        currentPosition = b;
        break;
      }
    }
    this.position = currentPosition;
    return currentPosition;
  }

  public int moveToPrevious() {
    if (this.buffers.isEmpty()) {
      return -1; // No position
    }
    int currentPosition = this.position;
    for (int b = currentPosition - 1; b != position; b--) {
      if (b < 0) {
        b = this.buffers.size();
        continue;
      } else if (this.buffers.get(b).getEnabled()) {
        currentPosition = b;
        break;
      }
    }
    this.position = currentPosition;
    return currentPosition;
  }

  public void setAllEnabled(Boolean enabled) {
    for (Buffer buffer : this.buffers) {
      buffer.setEnabled(enabled);
    }
  }

  public void updateAll() {
    for (Buffer buffer : this.buffers) {
      buffer.update();
    }
  }

  public Buffer getBuffer(String name) {
    for (Buffer buffer : this.buffers) {
      if (buffer.getName().equals(name)) return buffer;
    }
    return null;
  }

  public ArrayList<Buffer> getBuffers() {
    return this.buffers;
  }

  public Buffer getCurrentBuffer() {
    if (this.buffers.isEmpty()) return null;
    Buffer buffer = this.buffers.get(this.position);
    if (!buffer.getEnabled()) return null;
    return buffer;
  }

  public void setCurrentBuffer(String name) {
    for (int b = 0; b < this.buffers.size(); b++) {
      if (this.buffers.get(b).getName().equals(name)) {
        this.position = b;
        return;
      }
    }
  }

  public int getPosition() {
    return this.position;
  }
}
