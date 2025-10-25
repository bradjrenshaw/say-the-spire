import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;

public class HandCardSelectScreenPatch {

    private static void openLogic(HandCardSelectScreen screen, String msg) {
        Output.text(msg, true);
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "open", paramtypez = { String.class, int.class,
            boolean.class, boolean.class, boolean.class, boolean.class, boolean.class })
    public static class LargeOpenPatch {

        public static void Postfix(HandCardSelectScreen __instance, String msg, int amount, boolean anyNumber,
                boolean canPickZero, boolean forTransform, boolean forUpgrade, boolean upTo) {
            openLogic(__instance, msg);
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "open", paramtypez = { String.class, int.class,
            boolean.class, boolean.class })
    public static class SmallOpenPatch {

        public static void Postfix(HandCardSelectScreen __instance, String msg, int amount, boolean anyNumber,
                boolean canPickZero) {
            openLogic(__instance, msg);
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "setHoveredCard", paramtypez = { AbstractCard.class })
    public static class setHoveredCardPatch {

        public static void Prefix(HandCardSelectScreen __instance, AbstractCard card) {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT && card != null) {
                Output.setUI(new CardElement(card, CardElement.CardLocation.HAND_SELECT));
            }
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "update")
    public static class updatePatch {

        public static AbstractCard selectedCard = null;

        public static void Postfix(HandCardSelectScreen __instance) {
            AbstractCard current = __instance.selectedCards.getHoveredCard();
            if (current != selectedCard) {
                if (current != null) {
                    Output.setUI(new CardElement(current, CardElement.CardLocation.HAND_SELECT));
                }
                selectedCard = current;
            }
        }
    }
}
