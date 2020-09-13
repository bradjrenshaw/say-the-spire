package sayTheSpire.events;

import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import basemod.ReflectionHacks;


public class ProceedButtonShowEvent extends TimedTextEvent {

    private ProceedButton button;
    
    public ProceedButtonShowEvent(ProceedButton button, String originalLabel) {
        super(originalLabel, 0.2);
        this.button = button;
    }

    private Boolean getHidden() {
        return (boolean)ReflectionHacks.getPrivate(this.button, ProceedButton.class, "isHidden");
    }

    private String getLabel() {
        return (String)ReflectionHacks.getPrivate(this.button, ProceedButton.class, "label");
    }

    public Boolean shouldAbandon() {
        return this.getHidden() || !this.getLabel().equals(this.getText());
    }
}
