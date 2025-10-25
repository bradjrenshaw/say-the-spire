package sayTheSpire.buffers;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import java.util.ListIterator;
import sayTheSpire.TextParser;

public class PotionBuffer extends Buffer {

    private AbstractPotion potion;

    public PotionBuffer(String name) {
        super("potion", name);
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
            this.addLocalized("noObj");
            return;
        }
        this.context.put("name", this.potion.name);
        String rarityString = this.getPotionRarityString();
        this.context.put("rarity", rarityString);
        if (rarityString != null) {
            this.addLocalized("content.nameAndRarity");
        } else {
            this.add(this.potion.name);
        }
        this.add(TextParser.parse(potion.description, potion));
        ListIterator iter = potion.tips.listIterator(1);
        while (iter.hasNext()) {
            PowerTip tip = (PowerTip) iter.next();
            this.add(tip.header + "\n" + TextParser.parse(tip.body, potion));
        }
    }

    public String getPotionRarityString() {
        AbstractPotion.PotionRarity rarity = this.potion.rarity;
        // not sure if this can be null so better check it.
        if (rarity == null) {
            return null;
        }
        return this.context.localize(".text.potionRarity." + rarity.name().toLowerCase());
    }
}
