package sayTheSpire;

public class BufferControls {

  public static BufferManager buffers = null;

  public static void nextBuffer() {
    buffers.moveToNext();
    reportCurrentBuffer();
  }

  public static void nextItem() {
    Buffer current = buffers.getCurrentBuffer();
    if (current != null) current.moveToNext();
    reportCurrentItem(current);
  }

  public static void previousBuffer() {
    buffers.moveToPrevious();
    reportCurrentBuffer();
  }

  public static void previousItem() {
    Buffer current = buffers.getCurrentBuffer();
    if (current != null) current.moveToPrevious();
    reportCurrentItem(current);
  }

  public static void reportCurrentBuffer() {
    Buffer current = buffers.getCurrentBuffer();
    if (current == null) {
      Output.text("No buffers available.", true);
      return;
    }
    current.update();
    String currentItem = current.getCurrentItem();
    if (currentItem == null) {
      Output.text(
          "Buffer " + current.getName() + " current item is null, report to mod dev.", true);
      return;
    }
    Output.text(current.getName() + ": " + currentItem, true);
  }

  public static void reportCurrentItem(Buffer buffer) {
    if (buffer == null) {
      Output.text("Tried to read current item of null buffer, report to mod dev.", false);
      return;
    }
    buffer.update();

    String currentItem = buffer.getCurrentItem();
    if (currentItem == null) {
      Output.text(
          "Buffer " + buffer.getName() + " current item is null, report to mod dev.", false);
      return;
    }
    Output.text(currentItem, true);
  }
}
