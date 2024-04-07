package sayTheSpire;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.buffers.Buffer;
import sayTheSpire.localization.LocalizationContext;

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
        LocalizationContext context = Output.localization.getContext("text.buffers");
        context.put("buffer", current.getLabel());
        String currentItem = current.getCurrentItem();
        context.put("item", currentItem);
        if (current == null) {
            Output.textLocalized("noBuffersAvailable", true, context);
            return;
        }
        if (current.isEmpty()) {
            Output.textLocalized("bufferIsEmpty", true, context);
            return;
        }
        if (currentItem == null) {
            Output.textLocalized("currentItemIsNull", true, context);
            return;
        }
        Output.textLocalized("currentItem", true, context);
    }

    public static void reportCurrentItem(Buffer buffer) {
        LocalizationContext context = Output.localization.getContext("text.buffers");
        if (buffer == null) {
            Output.textLocalized("noBufferSelected", false, context);
            return;
        }
        context.put("buffer", buffer.getLabel());
        String currentItem = buffer.getCurrentItem();
        context.put("item", currentItem);
        if (buffer.isEmpty()) {
            Output.textLocalized("bufferIsEmpty", true, context);
            return;
        }
        if (currentItem == null) {
            Output.textLocalized("currentItemIsNull", false, context);
            return;
        }
        Output.text(currentItem, true);
    }

    public static void setCurrentBuffer(String name) {
        buffers.setCurrentBuffer(name);
    }
}
