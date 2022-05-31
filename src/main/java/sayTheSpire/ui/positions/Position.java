package sayTheSpire.ui.positions;

import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.Output;

public class Position {

    protected LocalizationContext context;
    protected String type;

    public Position(String type) {
        this.type = type;
        this.context = Output.localization.getContext("ui.positions");
    }

    public String getPositionString() {
        return this.context.localize(this.type);
    }
}
