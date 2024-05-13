package sayTheSpire.ui;

import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;

public interface IUIInfo {

    public String getLabel();

    default String getDescription() {
        return null;
    }

    default String getExtrasString() {
        return null;
    }

    default String getKey() {
        return null;
    }

    default String getTypeKey() {
        return null;
    }

    default Position getPosition() {
        return null;
    }

    default String getStatusString() {
        return null;
    }

    default String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(getLabel(), getDescription());
        return null;
    }

}
