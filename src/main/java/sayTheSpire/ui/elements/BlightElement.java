package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.utils.BlightUtils;
import sayTheSpire.utils.OutputUtils;

public class BlightElement extends GameObjectElement {

    public enum BlightLocation {
        MAIN_SCREEN, CHEST, OTHER
    };

    private BlightLocation location;
    private AbstractBlight blight;

    public BlightElement(AbstractBlight blight, BlightLocation location) {
        this(blight, location, null);
    }

    public BlightElement(AbstractBlight blight, BlightLocation location, Position position) {
        super("blight", position);
        this.blight = blight;
        this.location = location;
    }

    public String handleBuffers(BufferManager buffers) {
        buffers.getBuffer("blight").setObject(this.blight);
        buffers.enableBuffer("blight", true);
        return "blight";
    }

    public String getLabel() {
        return BlightUtils.getBlightShort(this.blight);
    }

    public Position getPosition() {
        switch (this.location) {
        case MAIN_SCREEN:
            return this.getInventoryPosition();
        default:
            return super.getPosition();
        }
    }

    public Position getInventoryPosition() {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null)
            return null;
        int index = player.blights.indexOf(this.blight);
        if (index < 0)
            return null;
        return new ListPosition(index, player.blights.size());
    }
}
