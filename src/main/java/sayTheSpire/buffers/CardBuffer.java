package sayTheSpire.buffers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.TextParser;

public class CardBuffer extends Buffer {

    private AbstractCard card;
    private Boolean isUpgradePreview;
    private boolean noFurtherUpgrade;

    public CardBuffer(String name) {
        this(name, false);
    }

    public CardBuffer(String name, Boolean isUpgradePreview) {
        super(name);
        this.card = null;
        this.isUpgradePreview = isUpgradePreview;
        this.noFurtherUpgrade = false;
    }

    public Object getObject() {
        return this.card;
    }

    public void setObject(Object object) {
        AbstractCard card = (AbstractCard) object;
        if (card == null) {
            this.card = null;
            return;
        }
        if (this.isUpgradePreview) {
            if (!card.canUpgrade()) {
                this.noFurtherUpgrade = true;
            } else {
                this.noFurtherUpgrade = false;
                AbstractCard upgradedCard = card.makeStatEquivalentCopy();
                upgradedCard.upgrade();
                upgradedCard.isLocked = card.isLocked;
                upgradedCard.isFlipped = card.isFlipped;
                card = upgradedCard;
            }
        }
        this.card = card;
    }

    public void update() {
        this.clear();
        if (this.card == null) {
            this.add("No card selected.");
            return;
        } else if (this.card.isLocked) {
            this.add(this.card.LOCKED_STRING);
            return;
        } else if (this.card.isFlipped) {
            this.add("face down card");
            return;
        } else if (this.isUpgradePreview && this.noFurtherUpgrade) {
            this.add("This card cannot be upgraded any further.");
            return;
        }
        this.add(card.name);
        this.add(CardUtils.getCardCostString(card) + " energy");
        // add card type and rarity as one buffer item.
        String typeAndRarity = CardUtils.getCardTypeString(card) + " type";
        String rarity = CardUtils.getCardRarityString(card);
        // not sure if rarity info can be missing so better check for it
        if (rarity != null) {
            typeAndRarity += ", " + rarity + " rarity";
        }
        this.add(typeAndRarity);
        this.add(CardUtils.getCardDescriptionString(card));
        for (String keyword : card.keywords) {
            String name = TextParser.parse(keyword);
            if (name.equals("")) {
                name = keyword; // to prevent any weird instances of orbs being treated as keywords for example
                // and being omited
            }
            String body = GameDictionary.keywords.get(keyword);
            if (body == null) {
                this.add(name + "\nUnknown keyword found, report to mod developer.");
            } else {
                body = TextParser.parse(body);
                this.add(name + "\n" + body);
            }
        }
    }
}
