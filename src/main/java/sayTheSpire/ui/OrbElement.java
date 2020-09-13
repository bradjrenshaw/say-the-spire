package sayTheSpire.ui;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import sayTheSpire.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.utils.OutputUtils;

public class OrbElement extends UIElement {

  private AbstractOrb orb;

  public OrbElement(AbstractOrb orb) {
    this.elementType = "orb";
    this.orb = orb;
  }

  public String handleBuffers(BufferManager buffers) {
    Output.setupBuffers(this.orb);
    return null;
  }

  public String getLabel() {
    return this.orb.name;
  }

  public String getPositionString() {
    AbstractPlayer player = OutputUtils.getPlayer();
    if (player == null || player.orbs == null) return null;
    int index = player.orbs.indexOf(this.orb);
    if (index < 0) return null;
    index = player.orbs.size() - index;
    return index + " of " + player.orbs.size();
  }
}
