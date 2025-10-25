package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.utils.OutputUtils;

public class OrbElement extends UIElement {

    private AbstractOrb orb;

    public OrbElement(AbstractOrb orb) {
        super("orb");
        this.orb = orb;
    }

    public String handleBuffers(BufferManager buffers) {
        buffers.getBuffer("orb").setObject(orb);
        buffers.enableBuffer("orb", true);
        return "orb";
    }

    public String getLabel() {
        return this.orb.name;
    }

    public Position getPosition() {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null || player.orbs == null)
            return null;
        int index = player.orbs.indexOf(this.orb);
        if (index < 0)
            return null;
        index = player.orbs.size() - index - 1;
        return new ListPosition(index, player.orbs.size());
    }
}
