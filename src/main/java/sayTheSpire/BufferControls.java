package sayTheSpire;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.buffers.Buffer;

public class BufferControls {

    public static BufferManager buffers = null;

    public static void nextBuffer() {
        buffers.moveToNext();
        reportCurrentBuffer();
    }

    public static void nextItem() {
        Buffer current = buffers.getCurrentBuffer();
        if (current != null)
            current.moveToNext();
        reportCurrentItem(current);
    }

    public static void previousBuffer() {
        buffers.moveToPrevious();
        reportCurrentBuffer();
    }

    public static void previousItem() {
        Buffer current = buffers.getCurrentBuffer();
        if (current != null)
            current.moveToPrevious();
        reportCurrentItem(current);
    }

    public static void reportCurrentBuffer() {
        Buffer current = buffers.getCurrentBuffer();
        if (current == null) {
            Output.text("No buffers available.", true);
            return;
        }
        if (current.isEmpty()) {
            Output.text(current.getLocalizedName() + " is empty.", true);
            return;
        }
        String currentItem = current.getCurrentItem();
        if (currentItem == null) {
            Output.text("Buffer " + current.getLocalizedName() + " current item is null, report to mod dev.", true);
            return;
        }
        Output.text(current.getLocalizedName() + ": " + currentItem, true);
    }

    public static void reportCurrentItem(Buffer buffer) {
        if (buffer == null) {
            Output.text("No buffer selected.", false);
            return;
        }
        if (buffer.isEmpty()) {
            Output.text("empty", true);
            return;
        }
        String currentItem = buffer.getCurrentItem();
        if (currentItem == null) {
            Output.text("Buffer " + buffer.getLocalizedName() + " current item is null, report to mod dev.", false);
            return;
        }
        Output.text(currentItem, true);
    }

    public static void setCurrentBuffer(String name) {
        buffers.setCurrentBuffer(name);
    }
}
