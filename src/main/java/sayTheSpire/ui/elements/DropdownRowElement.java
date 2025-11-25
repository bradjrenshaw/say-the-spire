package sayTheSpire.ui.elements;

import java.lang.reflect.Field;
import sayTheSpire.TextParser;
import sayTheSpire.ui.positions.ListPosition;

public class DropdownRowElement extends UIElement {

    private Object row;

    public DropdownRowElement(Object row, int index, int count) {
        super("button", new ListPosition(index, count));
        this.row = row;
    }

    public String getLabel() {
        try {
            Field field = this.row.getClass().getDeclaredField("text");
            field.setAccessible(true);
            return TextParser.parse((String) field.get(this.row));
        } catch (Exception e) {
            return "name for dropdown not found";
        }
    }

}
