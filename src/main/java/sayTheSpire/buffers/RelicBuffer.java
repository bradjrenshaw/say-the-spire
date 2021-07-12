package sayTheSpire.buffers;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ListIterator;
import sayTheSpire.utils.RelicUtils;
import sayTheSpire.TextParser;


public class RelicBuffer extends Buffer {

  AbstractRelic relic;

  public RelicBuffer(String name) {
    super(name);
    this.relic = null;
  }

  public Object getObject() {
    return this.relic;
  }

  public void setObject(Object object) {
    this.relic = (AbstractRelic) object;
  }

  public void update() {
    AbstractRelic relic = this.relic;
    this.clear();
    if (relic == null) {
      this.add("No relic available.");
      return;
    }
    this.add(relic.name);
    if (relic.counter >= 0) {
      this.add(relic.counter + " counter");
    }
    this.add(RelicUtils.getRelicDescription(relic));
    this.add(RelicUtils.getRelicFlavorText(relic));
    ListIterator iter = relic.tips.listIterator(1);
    while (iter.hasNext()) {
      PowerTip tip = (PowerTip) iter.next();
      this.add(tip.header + "\n" + TextParser.parse(tip.body, relic));
    }
  }
}
