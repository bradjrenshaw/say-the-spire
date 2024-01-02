package sayTheSpire.utils;

import java.util.ArrayList;
import java.util.HashMap;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.TextParser;
import sayTheSpire.Output;

public class CardUtils {

    public static String getCardCostString(AbstractCard card) {
        int cost = 0;
        if (OutputUtils.isInCombat()) {
            cost = card.costForTurn;
        } else {
            cost = card.cost;
        }
        if (card.cost == -1)
            return "X";
        else if (card.freeToPlay())
            return "0";
        else
            return Integer.toString(cost);
    }

    private static String extractUpdatedDescription(AbstractCard card) {
        StringBuilder sb = new StringBuilder();
        int lineCount = card.description.size();
        for (int d = 0; d < lineCount; d++) {
            DescriptionLine line = card.description.get(d);
            for (String token : line.getText().split("\\s+")) {
                sb.append(token);
                sb.append(' ');
            }
            if (d < lineCount - 1)
                sb.append(" NL ");
        }
        return sb.toString();
    }

    public static String getCardDescriptionString(AbstractCard card) {
        String description;
        if (Output.config.getBoolean("advanced.use_updated_card_description"))
            description = extractUpdatedDescription(card);
        else
            description = card.rawDescription;
        return TextParser.parse(description.replaceAll("([^ ])(![A-Z]!)", "$1 $2"), card); // Ensures that
        // dynamic variables
        // have a preceding
        // white space
    }

    public static HashMap<String, String> getCardDynamicVariables(AbstractCard card) {
        HashMap<String, String> variables = new HashMap();
        variables.put("B", Integer.toString(getCardBlock(card)));
        variables.put("D", Integer.toString(getCardDamage(card)));
        variables.put("M", Integer.toString(getCardMagicNumber(card)));
        return variables;
    }

    public static int getCardBlock(AbstractCard card) {
        if (card.isBlockModified) {
            return card.block;
        }
        return card.baseBlock;
    }

    public static int getCardDamage(AbstractCard card) {
        if (card.isDamageModified) {
            return card.damage;
        }
        return card.baseDamage;
    }

    public static int getCardMagicNumber(AbstractCard card) {
        if (card.isMagicNumberModified) {
            return card.magicNumber;
        }
        return card.baseMagicNumber;
    }

    public static String getCardTypeString(AbstractCard card) {
        switch (card.type) {
        case ATTACK:
            return AbstractCard.TEXT[0];
        case SKILL:
            return AbstractCard.TEXT[1];
        case POWER:
            return AbstractCard.TEXT[2];
        case CURSE:
            return AbstractCard.TEXT[3];
        case STATUS:
            return AbstractCard.TEXT[7];
        default:
            return AbstractCard.TEXT[5]; // Should be impossible, returns unknown (in English, different for other
        // localizations)
        }
    }

    public static String getCardRarityString(AbstractCard card) {
        AbstractCard.CardRarity rarity = card.rarity;
        // not sure if this can be null so better check it.
        if (rarity == null) {
            return null;
        }
        return Output.localization.localize("text.cardRarity." + rarity.name().toLowerCase());
    }

    public static String getCardShort(AbstractCard card) {
        if (card.isLocked) {
            return card.LOCKED_STRING;
        }
        if (card.isFlipped) {
            return "face down card";
        }
        return card.name + ", " + getCardCostString(card);
    }

    public static GridPosition getGridPosition(AbstractCard card, ArrayList<AbstractCard> grid, int width) {
        if (grid == null)
            return null;
        int gridIndex = grid.indexOf(card);
        if (gridIndex < 0)
            return null;
        int row = gridIndex / width + 1;
        int column = gridIndex % width + 1;
        return new GridPosition(column, row);
    }
}
