package sayTheSpire.ui.elements;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;

/**
 * A virtual UI element represents a game object and various properties it may have. Virtual UI Elements also include
 * handling for being interacted with that the game does not have (for example, a UI virtual toggle button may announce
 * the status when clicked) Any virtual UI elements should inherit this base class
 */
public abstract class UIElement {

    protected String elementType = null;
    protected AbstractPosition position;

    public UIElement(String type) {
        this(type, null);
    }

    public UIElement(String elementType, AbstractPosition position) {
        this.elementType = elementType;
        this.position = position;
    }

    /**
     * This method handles setting up buffers when this element is focused.
     *
     * @param buffers
     *            The BufferManager object (probably Output.buffers)
     * 
     * @return A String with a buffer name to set as the focus, or null to leave that up to other sources
     */
    public abstract String handleBuffers(BufferManager buffers);

    /** Updates any needed values and performs any triggers on UI elements. */
    public void update() {
    }

    public AbstractPosition getPosition() {
        return this.position;
    }

    /** Returns the label of the element (usually the name) */
    public String getLabel() {
        return null;
    }

    /**
     * Returns the status of the element as a string (checked for checkboxes, on/off for toggle buttons, etc). Null
     * means no status string is available.
     */
    public String getStatusString() {
        return null;
    }

    /**
     * Returns extras for an element as a string (extras can be things such as price in the shop). Null means there are
     * none.
     */
    public String getExtrasString() {
        return null;
    }

    public String getTypeString() {
        return elementType;
    }
}