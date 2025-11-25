package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.runHistory.RunPathElement;
import java.lang.reflect.Method;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;

public class RunPathViewElement extends UIElement {

    private RunPathElement element;

    public RunPathViewElement(RunPathElement element, Position position) {
        super("button", position);
        this.element = element;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.getLabel(), this.getTooltipText());
        return "UI";
    }

    public String getLabel() {
        String floorFormat = (String) ReflectionHacks.getPrivateStatic(RunPathElement.class,
                "TEXT_SIMPLE_FLOOR_FORMAT");
        int floor = (int) ReflectionHacks.getPrivate(this.element, RunPathElement.class, "floor");
        return String.format(floorFormat, Integer.valueOf(floor));
    }

    public String getTooltipText() {
        try {
            Method method = this.element.getClass().getDeclaredMethod("getTipDescriptionText");
            method.setAccessible(true);
            return TextParser.parse((String) method.invoke(this.element));
        } catch (Exception e) {
            return "tooltip text broke";
        }
    }

    public String getTypeKey() {
        return null;
    }
}
