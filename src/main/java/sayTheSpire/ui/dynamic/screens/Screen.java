package sayTheSpire.ui.dynamic.screens;

import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.ElementContainer;
import sayTheSpire.ui.input.InputAction;

public class Screen {

    private ElementContainer primaryContainer;
    protected UIContext context;

    public Screen(UIContext context) {
        this.context = context;
        this.primaryContainer = null;
        this.setup();
    }

    protected void setup() {
        return;
    }

    public void onFocus(Boolean moved) {
        ElementContainer container = this.getPrimaryContainer();
        if (container == null)
            return;
        container.onFocus(moved);
    }

    public void onUnfocus(Boolean moved) {
        ElementContainer container = this.getPrimaryContainer();
        if (container == null)
            return;
        container.onUnfocus(moved);
    }

    public void onPush() {
        ElementContainer container = this.getPrimaryContainer();
        if (container == null)
            return;
        container.enterFocus(null, null);
    }

    public void onPop() {
    };

    public Boolean processInputPressed(InputAction action) {
        ElementContainer container = this.getPrimaryContainer();
        if (container == null)
            return false;
        return container.processInputPressed(action);
    }

    public Boolean processInputJustPressed(InputAction action) {
        ElementContainer container = this.getPrimaryContainer();
        if (container == null)
            return false;
        return container.processInputJustPressed(action);
    }

    public Boolean processInputReleased(InputAction action) {
        ElementContainer container = this.getPrimaryContainer();
        if (container == null)
            return false;
        return container.processInputReleased(action);
    }

    public void update() {
        return;
    }

    public ElementContainer getPrimaryContainer() {
        return this.primaryContainer;
    }

    public void setPrimaryContainer(ElementContainer container) {
        if (this.primaryContainer != null) {
            throw new RuntimeException("Cannot change primary container of screen after being set.");
        }
        this.primaryContainer = container;
    }

}
