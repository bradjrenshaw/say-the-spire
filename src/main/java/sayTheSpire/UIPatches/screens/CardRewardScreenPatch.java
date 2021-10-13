import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;

@SpirePatch(clz = CardRewardScreen.class, method = "update")
public class CardRewardScreenPatch {

    public static AbstractCard prevHoveredCard = null;

    public static AbstractCard getHoveredCard(CardRewardScreen screen) {
        for (AbstractCard card : screen.rewardGroup) {
            if (card.hb.hovered) {
                return card;
            }
        }
        return null;
    }

    public static void Postfix(CardRewardScreen __instance) {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.CARD_REWARD)
            return;
        AbstractCard current = getHoveredCard(__instance);
        if (current != prevHoveredCard) {
            if (current != null) {
                Output.setUI(new CardElement(current));
            }
            prevHoveredCard = current;
        }
    }
}
