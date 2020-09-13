package sayTheSpire;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import java.util.ListIterator;

public class PotionBuffer extends Buffer {

  private AbstractPotion potion;

  public PotionBuffer(String name) {
    super(name);
    this.potion = null;
  }

  public Object getObject() {
    return this.potion;
  }

  public void setObject(Object object) {
    this.potion = (AbstractPotion) object;
  }

  public void update() {
    this.clear();
    AbstractPotion potion = this.potion;
    if (potion == null) {
      this.add("No potion available.");
      return;
    } else if (potion instanceof PotionSlot) {
      this.add("Empty potion slot.");
      return;
    }
    this.add(potion.name);
    this.add(TextParser.parse(potion.description, potion));
    ListIterator iter = potion.tips.listIterator(1);
    while (iter.hasNext()) {
      PowerTip tip = (PowerTip) iter.next();
      this.add(tip.header + "\n" + TextParser.parse(tip.body, potion));
    }
  }
}
