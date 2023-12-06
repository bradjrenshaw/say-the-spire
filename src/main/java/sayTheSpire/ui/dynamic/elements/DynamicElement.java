package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.ui.dynamic.events.FocusEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.dynamic.events.UnfocusEvent;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.positions.Position;

public abstract class DynamicElement extends UIElement {

    protected String label, description;
    protected DynamicElement parent;
    public SingleEventDispatcher<FocusEvent> focus;
    public SingleEventDispatcher<UnfocusEvent> unfocus;

    DynamicElement(DynamicElement parent, String type) {
        this(parent, type, type, null, null);
    }

    DynamicElement(DynamicElement parent, String type, String label, String description) {
        this(parent, type, label, description, null);
    }

    DynamicElement(DynamicElement parent, String type, String label, String description, Position position) {
        super(type, position);
        this.parent = parent;
        this.shouldUseBaseLocalization = false;
        this.label = label;
        this.description = description;
        this.focus = new SingleEventDispatcher<FocusEvent>();
        this.unfocus = new SingleEventDispatcher<UnfocusEvent>();
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.getLabel(), this.getDescription());
        return null;
    }

    public void onFocus() {
        this.focus.dispatch(new FocusEvent(this));
    }

    public void onUnfocus() {
        this.unfocus.dispatch(new UnfocusEvent(this));
    }

    public Boolean processCancelInput() {
        return false;
    }

    public Boolean processConfirmInput() {
        return false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLabel() {
        return this.label;
    }
}
