package sayTheSpire.ui.elements;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.Output;

public class ResourceElement extends UIElement {

    private String description, value;

    public ResourceElement(String name, String description, String value) {
        this(name, description, value, null);
    }

    public ResourceElement(String name, String description, String value, Position position) {
        super(name, position);
        this.description = description;
        this.value = value;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.elementType, this.description);
        return null;
    }

    public String getLabel() {
        return this.value;
    }
}
