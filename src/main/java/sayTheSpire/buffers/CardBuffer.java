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
        super("card", name);
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
            this.addLocalized("noObj");
            return;
        } else if (this.card.isLocked) {
            this.add(this.card.LOCKED_STRING);
            return;
        } else if (this.card.isFlipped) {
            this.addLocalized("faceDown");
            return;
        } else if (this.isUpgradePreview && this.noFurtherUpgrade) {
            this.addLocalized("noFurtherUpgrade");
            return;
        }
        this.context.put("cost", CardUtils.getCardCostString(card));
        this.context.put("type", CardUtils.getCardTypeString(this.card));
        this.context.put("rarity", CardUtils.getCardRarityString(this.card));

        this.add(card.name);
        this.addLocalized("content.cost");

        // add card type and rarity as one buffer item.
        this.addLocalized("content.typeAndRarity");
        this.add(CardUtils.getCardDescriptionString(card));
        for (String keyword : card.keywords) {
            String name = TextParser.parse(keyword);
            if (name.equals("")) {
                name = keyword; // to prevent any weird instances of orbs being treated as keywords for example
                // and being omited
            }
            String body = GameDictionary.keywords.get(keyword);
            if (body == null) {
                this.context.put("unknownKeyword", name);
                this.addLocalized("content.unknownKeyword");
            } else {
                body = TextParser.parse(body);
                this.add(name + "\n" + body);
            }
        }
    }
}
