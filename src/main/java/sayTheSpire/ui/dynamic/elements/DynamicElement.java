package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.ui.Direction;
import sayTheSpire.ui.dynamic.events.FocusEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.dynamic.events.UnfocusEvent;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.positions.Position;

/**
 * this class represents a dynamic UI element (any UI element used by Say the Spire's own UI, such as settings)
 */
public abstract class DynamicElement extends UIElement {

    protected String label, description;
    protected DynamicElement parent;
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
    DynamicElement(DynamicElement parent, String type, String label) {
        this(parent, type, label, null, null);
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
    DynamicElement(DynamicElement parent, String type, String label, String description) {
        this(parent, type, label, description, null);
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
    DynamicElement(DynamicElement parent, String type, String label, String description, Position position) {
        super(type, position);
        this.parent = parent;
        this.shouldUseBaseLocalization = false;
        this.label = label;
        this.description = description;
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
        Output.setupUIBufferMany(this.getLabel(), this.getDescription());
        return null;
    }

    /**
     * Triggers when the element is unfocused (for example you move to a different element)
     */
    public void exitFocus() {
        this.onUnfocus();
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
        this.onFocus();
    }

    public void onFocus() {
        this.focus.dispatch(new FocusEvent(this));
    }

    public void onUnfocus() {
        this.unfocus.dispatch(new UnfocusEvent(this));
    }

    /**
     * Executes code when a cancel input is received by the current element (IE it is focused by its parent element)
     * 
     * @return True if input should be propigated to parent elements; false otherwise.
     */
    public Boolean processCancelInput() {
        return false;
    }

    /**
     * Executes code when a confirm input is received by the current element (IE it is focused by its parent element)
     * 
     * @return True if input should be propigated to parent elements; false otherwise.
     */
    public Boolean processConfirmInput() {
        return false;
    }

    /**
     * Executes code when a direction input is received by the current element (IE it is focused by its parent element)
     * 
     * @param direction
     *            The directional input pressed
     * 
     * @return True if input should be propigated to parent elements; false otherwise. * @param direction
     */
    public Boolean processDirectionInput(Direction direction) {
        return false;
    }

    public String getDescription() {
        return this.description;
    }

    public DynamicElement getParent() {
        return this.parent;
    }

    public Position getPosition() {
        ElementContainer parent = (ElementContainer) this.getParent();
        if (parent != null) {
            return parent.getChildPosition(this);
        }
        return super.getPosition();
    }

    public String getLabel() {
        return this.label;
    }
}
