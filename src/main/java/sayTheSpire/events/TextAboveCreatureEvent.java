package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import sayTheSpire.utils.OutputUtils;

public class TextAboveCreatureEvent extends Event {

  protected AbstractCreature creature;
  protected String text;

  public TextAboveCreatureEvent(AbstractCreature creature, String text) {
    super();
    this.creature = creature;
    this.text = text;
  }

  public String getText() {
    return OutputUtils.getCreatureName(this.creature) + ": " + this.text;
  }

  public Boolean isComplete() {
    return this.creature != null && this.text != null;
  }

  public Boolean shouldRead() {
    return this.isComplete();
  }
}
