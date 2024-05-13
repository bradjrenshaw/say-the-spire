package sayTheSpire.ui.elements;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.Output;

public class TextElement extends UIElement {

    private String name;
    private String description;

    public TextElement(String name, String description, Position position) {
        super("text", position);
        this.name = name;
        this.description = description;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.name, this.description);
        return null;
    }

    public String getTypeKey() {
        return null;
    }
}
