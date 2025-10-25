package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import java.lang.reflect.Field;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;

public class DropdownElement extends UIElement {

    private String name;
    private DropdownMenu dropdown;
    private int prevIndex;
    private int prevVisibleStart;
    private int delayFrames;
    private Boolean autoAnnounceHover;

    public DropdownElement(DropdownMenu dropdown, String name) {
        this(dropdown, name, true);
    }

    public DropdownElement(DropdownMenu dropdown, String name, Boolean autoAnnounceHover) {
        super("dropdown");
        this.dropdown = dropdown;
        this.name = name;
        this.autoAnnounceHover = autoAnnounceHover;
        this.prevIndex = -1;
        this.prevVisibleStart = -1;
        this.delayFrames = 0;
    }

    public Boolean getAutoAnnounceHover() {
        return this.autoAnnounceHover;
    }

    public DropdownMenu getDropdownMenu() {
        return this.dropdown;
    }

    public int getIndex() {
        DropdownMenu dropdown = this.getDropdownMenu();
        int rowCount = dropdown.rows.size();
        for (int i = dropdown.topVisibleRowIndex; i < rowCount; i++) {
            try {
                Object row = dropdown.rows.get(i);
                Field field = row.getClass().getDeclaredField("hb");
                field.setAccessible(true);
                Hitbox hb = (Hitbox) field.get(row);
                if (hb.hovered)
                    return i;
            } catch (Exception e) {
                continue;
            }
        }
        return -1;
    }

    public String getLabel() {
        return this.name;
    }

    public DropdownRowElement getRowElement(int index) {
        DropdownMenu dropdown = this.getDropdownMenu();
        int rowCount = dropdown.rows.size();
        if (index < 0 || index >= rowCount)
            return null;
        Object row = dropdown.rows.get(index);
        if (row == null)
            return null;
        return new DropdownRowElement(row, index, rowCount);
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.name);
        return null;
    }

    public void update() {
        if (!this.getDropdownMenu().isOpen) {
            return;
        }
        int index = this.getIndex();
        int visibleStart = dropdown.topVisibleRowIndex;

        // need to delay when scrolling, otherwise the cursor will read unfocused rows during the transition
        // 3 frames seems to be enough
        if (visibleStart != this.prevVisibleStart) {
            this.prevVisibleStart = visibleStart;
            this.delayFrames = 3;
            return; // give it time for hitboxes to update
        }
        if (this.delayFrames > 0) {
            this.delayFrames -= 1;
            return;
        }
        if (index != this.prevIndex && index >= 0) {
            DropdownRowElement row = this.getRowElement(index);
            if (row == null)
                return;
            Output.setUI(row);
            this.prevIndex = index;
        }
    }

    public String getStatusString() {
        int index = this.getDropdownMenu().getSelectedIndex();
        if (index < 0)
            return null;
        DropdownRowElement row = this.getRowElement(index);
        if (row == null)
            return null;
        return row.getLabel();
    }
}
