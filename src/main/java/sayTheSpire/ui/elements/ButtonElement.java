package sayTheSpire.ui.elements;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;
import sayTheSpire.Output;

public class ButtonElement extends UIElement {

    private String name;

    private String label;

    private String description;

    public ButtonElement(String name) {
        this(name, null, null);
    }

    public ButtonElement(String name, String label, String description) {
        this(name, label, description, null);
    }

    public ButtonElement(String name, String label, String description, AbstractPosition position) {
        super("button", position);
        this.name = name;
        this.label = label;
        this.description = description;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.name, this.description);
        return null;
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
