package sayTheSpire.ui.elements;

import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;

public class ResourceElement extends UIElement {

    private String name, description, value;

    public ResourceElement(String name, String description, String value) {
        this(name, description, value, null);
    }

    public ResourceElement(String name, String description, String value, Position position) {
        super(name, position);
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.name, this.description);
        return null;
    }

    public String getLabel() {
        return this.value;
    }
}
