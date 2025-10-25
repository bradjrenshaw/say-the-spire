package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;

public class StatElement extends UIElement {

    private CharStat charStat;
    private String charName;

    public StatElement(String charName, CharStat charStat) {
        this(charName, charStat, null);
    }

    public StatElement(String charName, CharStat charStat, Position position) {
        super("character stats", position);
        this.charName = charName;
        this.charStat = charStat;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBuffer(this.getBuffer());
        Output.setupUIBuffer(this.getBuffer());
        return null;
    }

    private ArrayList<String> getBuffer() {
        ArrayList<String> result = new ArrayList();
        result.add(this.charName);
        if (this.charStat != null) {
            String info = (String) ReflectionHacks.getPrivate(this.charStat, CharStat.class, "info");
            String info2 = (String) ReflectionHacks.getPrivate(this.charStat, CharStat.class, "info2");
            result.add(TextParser.parse(info));
            if (info2 != null)
                result.add(TextParser.parse(info2));
        }
        return result;
    }

    public String getLabel() {
        return this.charName;
    }
}
