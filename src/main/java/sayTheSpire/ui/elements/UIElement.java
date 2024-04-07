package sayTheSpire.ui.elements;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.Output;

/**
 * A virtual UI element represents a game object and various properties it may have. Virtual UI Elements also include
 * handling for being interacted with that the game does not have (for example, a UI virtual toggle button may announce
 * the status when clicked) Any virtual UI elements should inherit this base class
 */
public abstract class UIElement {

    protected String elementType = null;
    protected Position position;
    protected final LocalizationContext localization;
    protected Boolean shouldUseBaseLocalization;

    public UIElement(String type) {
        this(type, null);
    }

    public UIElement(String elementType, Position position) {
        this.elementType = elementType;
        this.position = position;
        this.localization = Output.localization.getContext("ui.elements." + elementType);
        this.shouldUseBaseLocalization = true;
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

    /**
     * Updates any needed values and performs any triggers on UI elements. Note that this won't do anything unless the
     * element is in the UIRegistry.
     */
    public void update() {
    }

    public String getElementType() {
        return this.elementType;
    }

    public String getFocusString() {
        StringBuilder sb = new StringBuilder();
        String label = this.getLabel();
        if (label != null)
            sb.append(label);
        String extras = this.getExtrasString();
        if (extras != null)
            sb.append(" " + extras);
        if (Output.config.getBoolean("ui.read_types")) {
            String type = this.getTypeString();
            if (type != null && !Output.config.getExcludedTypenames().contains(type)) {
                if (this.shouldUseBaseLocalization) {
                    String localizedType = Output.localization.localize("ui.types." + type);
                    if (localizedType != null)
                        type = localizedType;
                }
                sb.append(" " + type);
            }
        }
        String status = this.getStatusString();
        if (status != null)
            sb.append(" " + status);
        if (Output.config.getBoolean("ui.read_positions")) {
            Position position = this.getPosition();
            if (position != null) {
                sb.append(". " + position.getPositionString());
            }
        }
        return sb.toString();
    }

    /**
     * Returns an AbstractPosition corresponding to a contextual position of the element.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns the label of the element (usually the name). Note that any getLabel() should be assumed to be localized;
     * any getKey() should not.
     */
    public String getLabel() {
        return null;
    }

    /**
     * Returns the status of the element as a string (checked for checkboxes, on/off for toggle buttons, etc). Null
     * means no status string is available. This method is assumed to return localized output.
     */
    public String getStatusString() {
        return null;
    }

    /**
     * Returns extras for an element as a string (extras can be things such as price in the shop). Null means there are
     * none. This method is assumed to return localized output.
     */
    public String getExtrasString() {
        return null;
    }

    /** Returns the type of the element (such as "button"). The output of this method is assumed to be localized. */
    public String getTypeString() {
        return elementType;
    }
}
