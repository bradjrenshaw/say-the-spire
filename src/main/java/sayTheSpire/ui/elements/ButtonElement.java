package sayTheSpire.ui.elements;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.Output;

public class ButtonElement extends UIElement {

    private String name;

    private String description;

    public ButtonElement(String name) {
        this(name, null);
    }

    public ButtonElement(String name, String description) {
        this(name, description, null);
    }

    public ButtonElement(String name, String description, Position position) {
        super("button", position);
        this.name = name;
        this.description = description;
    }

    public String getLabel() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
}
