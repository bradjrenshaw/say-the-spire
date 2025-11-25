package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.Direction;
import sayTheSpire.ui.IUIInfo;
import sayTheSpire.ui.dynamic.events.FocusEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.dynamic.events.UnfocusEvent;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.positions.Position;

/**
 * this class represents a dynamic UI element (any UI element used by Say the Spire's own UI, such as settings)
 */
public abstract class DynamicElement extends UIElement {

    protected String label, description;
    protected ElementContainer parent;
    private IUIInfo info;

    public SingleEventDispatcher<FocusEvent> focus;
    public SingleEventDispatcher<UnfocusEvent> unfocus;

    /**
     * 
     * @param parent
     *            The container of this element
     * @param type
     *            The type name of this element (IE button, toggle button)
     * @param label
     *            The display name of this element (IE "Settings")
     */
    DynamicElement(String type, String label) {
        this(type, label, null, null);
    }

    /**
     * 
     * @param parent
     *            The container of this element
     * @param type
     *            The type name of this element (IE button, toggle button)
     * @param label
     *            The display name of this element (IE "Settings")
     * @param description
     *            Additional info about the element (a tooltip) viewable in the UI buffer
     */
    DynamicElement(String type, String label, String description) {
        this(type, label, description, null);
    }

    /**
     * 
     * @param parent
     *            The container of this element
     * @param type
     *            The type name of this element (IE button, toggle button)
     * @param label
     *            The display name of this element (IE "Settings")
     * @param description
     *            Additional info about the element (a tooltip) viewable in the UI buffer
     * @param position
     *            A position object. It is recommended that you do not use this and let the element dynamically
     *            determine its own position.
     */
    DynamicElement(String type, String label, String description, Position position) {
        super(type, position);
        // Any add methods must set the parent of their elements
        this.parent = null;
        this.label = label;
        this.description = description;
        this.info = null;
        this.focus = new SingleEventDispatcher<FocusEvent>();
        this.unfocus = new SingleEventDispatcher<UnfocusEvent>();
    }

    /**
     * Sets up the buffers attached to this element.
     * 
     * @param buffers
     *            the buffer manager object
     * 
     * @return A string corresponding to the buffer name to focus or null to not change the focus
     */
    public String handleBuffers(BufferManager buffers) {
        if (this.info != null) {
            return this.info.handleBuffers(buffers);
        }
        Output.setupUIBufferMany(this.getLabel(), this.getDescription());
        return null;
    }

    /**
     * Triggers when the element is unfocused (for example you move to a different element)
     */
    public void exitFocus() {
        this.onUnfocus(true);
    }

    /**
     * Triggers when the element is focused.
     * 
     * @param position
     *            The position of the previous element (used to possibly dynamically set focus to better match the
     *            spacial orientation of the previous element)
     * @param direction
     *            The directional input pressed to reach this element.
     */
    public void enterFocus(Position position, Direction direction) {
        this.onFocus(true);
    }

    public void onFocus(Boolean moved) {
        Output.setUI(this);
        this.focus.dispatch(new FocusEvent(this));
    }

    public void onUnfocus(Boolean moved) {
        this.unfocus.dispatch(new UnfocusEvent(this));
    }

    public Boolean processInputPressed(InputAction action) {
        return false;
    }

    public Boolean processInputJustPressed(InputAction action) {
        return false;
    }

    public Boolean processInputReleased(InputAction action) {
        return false;
    }

    public void setParent(ElementContainer parent) {
        this.parent = parent;
    }

    public void setInfo(IUIInfo info) {
        this.info = info;
    }

    public String getDescription() {
        if (this.info != null) {
            String infoDescription = this.info.getDescription();
            if (infoDescription != null)
                return infoDescription;
        }
        return this.description;
    }

    public ElementContainer getParent() {
        return this.parent;
    }

    public Position getPosition() {
        if (this.info != null) {
            Position infoPosition = this.info.getPosition();
            if (infoPosition != null)
                return infoPosition;
        }
        ElementContainer parent = this.getParent();
        if (parent != null) {
            return parent.getChildPosition(this);
        }
        return super.getPosition();
    }

    public String getLabel() {
        if (this.info != null) {
            String infoLabel = this.info.getLabel();
            if (infoLabel != null)
                return infoLabel;
        }
        return this.label;
    }
}
