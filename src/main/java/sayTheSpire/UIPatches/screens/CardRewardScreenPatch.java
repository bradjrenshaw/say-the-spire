import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.ListPosition;

public class CardRewardScreenPatch {

    private static Boolean initialWait = false;
    private static AbstractCard prevHoveredCard = null;

    @SpirePatch(clz = CardRewardScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(CardRewardScreen __instance, ArrayList<AbstractCard> cards, RewardItem rItem,
                String header) {
            initialWait = true;
            prevHoveredCard = null;
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "update")
    public static class UpdatePatch {

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
                    int index = __instance.rewardGroup.indexOf(current);
                    if (initialWait && index != 0)
                        return;
                    ListPosition position = new ListPosition(index, __instance.rewardGroup.size());
                    Output.setUI(new CardElement(current, CardElement.CardLocation.CARD_REWARD, position));
                    initialWait = false;
                }
                prevHoveredCard = current;
            }
        }
    }
}
