package sayTheSpire.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import java.util.HashMap;
import sayTheSpire.TextParser;

public class CardUtils {

  public static String getCardCostString(AbstractCard card) {
    int cost = 0;
    if (OutputUtils.isInCombat()) {
      cost = card.costForTurn;
    } else {
      cost = card.cost;
    }
    if (card.cost == -1) return "X";
    else if (card.freeToPlay()) return "0";
    else return Integer.toString(cost);
  }

  public static String getCardDescriptionString(AbstractCard card) {
    StringBuilder sb = new StringBuilder();
    for (DescriptionLine d : card.description) {
      String text = d.getText();
      sb.append(TextParser.parse(text, card) + "\n");
    }
    return sb.toString();
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
        return AbstractCard.TEXT[
            5]; // Should be impossible, returns unknown (in English, different for other
        // localizations)
    }
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
}
