import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import java.util.ArrayList;
import java.util.HashMap;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.ui.elements.ShopElement;
import sayTheSpire.ui.positions.GridPosition;

public class ShopScreenPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("MerchantImageTextNotInGame");
    public static final String[] TEXT = uiStrings.TEXT;

    static HashMap<Object, ShopElement> items = new HashMap();
    private static ShopElement hoveredItem = null;

    @SpirePatch(clz = ShopScreen.class, method = "createSpeech", paramtypez = { String.class })
    public static class CreateSpeechPatch {

        public static void Prefix(ShopScreen __instance, String msg) {
            Output.event(new DialogueEvent("says", "merchant", TextParser.parse(msg, "talk")));
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "init")
    public static class InitPatch {

        public static void Postfix(ShopScreen __instance) {
            items.clear();
            hoveredItem = null;
            int coloredCardCount = __instance.coloredCards.size();
            for (int c = 0; c < coloredCardCount; c++) {
                AbstractCard card = __instance.coloredCards.get(c);
                ShopElement element = new ShopElement(card, new GridPosition(c + 1, 1));
                // Only top row cards can be on sale and only one card ever can be per shop
                items.put(card, element);
            }
            int colorlessCardCount = __instance.colorlessCards.size();
            for (int c = 0; c < colorlessCardCount; c++) {
                AbstractCard card = __instance.colorlessCards.get(c);
                ShopElement element = new ShopElement(card, new GridPosition(c + 1, 2));
                items.put(card, element);
            }
            ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>) ReflectionHacks.getPrivate(__instance,
                    ShopScreen.class, "relics");
            int relicCount = relics.size();
            for (int r = 0; r < relicCount; r++) {
                StoreRelic storeRelic = relics.get(r);
                AbstractRelic relicObj = storeRelic.relic;
                ShopElement element = new ShopElement(storeRelic, new GridPosition(3 + r, 2));
                items.put(storeRelic, element);
            }
            ArrayList<StorePotion> potions = (ArrayList<StorePotion>) ReflectionHacks.getPrivate(__instance,
                    ShopScreen.class, "potions");
            int potionCount = potions.size();
            for (int p = 0; p < potionCount; p++) {
                StorePotion storePotion = potions.get(p);
                AbstractPotion potionObj = storePotion.potion;
                ShopElement element = new ShopElement(storePotion, new GridPosition(3 + p, 3));
                items.put(storePotion, element);
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "update")
    public static class UpdatePatch {

        public static boolean isPurgeHovered = false;

        public static ShopElement getHoveredCard(ShopScreen screen) {
            int coloredCardCount = screen.coloredCards.size();
            for (int c = 0; c < coloredCardCount; c++) {
                AbstractCard card = screen.coloredCards.get(c);
                if (!card.hb.hovered)
                    continue;
                if (items.containsKey(card)) {
                    return items.get(card);
                } else {
                    ShopElement element = new ShopElement(card, new GridPosition(c + 1, 1));
                    items.put(card, element);
                    return element;
                }
            }

            int colorlessCardCount = screen.colorlessCards.size();
            for (int c = 0; c < colorlessCardCount; c++) {
                AbstractCard card = screen.colorlessCards.get(c);
                if (!card.hb.hovered)
                    continue;
                if (items.containsKey(card)) {
                    return items.get(card);
                } else {
                    ShopElement element = new ShopElement(card, new GridPosition(c + 1, 2));
                    items.put(card, element);
                    return element;
                }
            }
            return null;
        }

        public static ShopElement getHoveredItem(ShopScreen screen) {
            ShopElement element = null;
            element = getHoveredPurge(screen);
            if (element != null)
                return element;
            element = getHoveredCard(screen);
            if (element != null)
                return element;
            element = getHoveredPotion(screen);
            if (element != null)
                return element;
            element = getHoveredRelic(screen);
            if (element != null)
                return element;
            return null;
        }

        public static ShopElement getHoveredPotion(ShopScreen screen) {
            ArrayList<StorePotion> potions = (ArrayList<StorePotion>) ReflectionHacks.getPrivate(screen,
                    ShopScreen.class, "potions");
            int potionCount = potions.size();
            for (int p = 0; p < potionCount; p++) {
                StorePotion potion = potions.get(p);
                if (!potion.potion.hb.hovered)
                    continue;
                if (items.containsKey(potion)) {
                    return items.get(potion);
                } else {
                    ShopElement element = new ShopElement(potion, new GridPosition(3 + p, 3));
                    items.put(potion, element);
                    return element;
                }
            }
            return null;
        }

        public static ShopElement getHoveredPurge(ShopScreen screen) {
            Boolean gameIsPurgeHovered = (Boolean) ReflectionHacks.getPrivate(screen, ShopScreen.class, "purgeHovered");
            if (isPurgeHovered && gameIsPurgeHovered)
                return hoveredItem;
            if (!isPurgeHovered && gameIsPurgeHovered) {
                String label;
                if (screen.purgeAvailable) {
                    label = TEXT[0];
                } else {
                    label = TEXT[0] + " ( " + TEXT[3] + ")";
                }
                ButtonElement button = new ButtonElement(label, TEXT[0]);
                isPurgeHovered = true;
                ShopElement element = new ShopElement(button, ShopElement.ShopElementType.PURGE_BUTTON,
                        new GridPosition(6, 2));
                return element;
            }
            isPurgeHovered = false;
            return null;
        }

        public static ShopElement getHoveredRelic(ShopScreen screen) {
            ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>) ReflectionHacks.getPrivate(screen, ShopScreen.class,
                    "relics");
            int relicCount = relics.size();
            for (int r = 0; r < relicCount; r++) {
                StoreRelic relic = relics.get(r);
                if (!relic.relic.hb.hovered)
                    continue;
                if (items.containsKey(relic)) {
                    return items.get(relic);
                } else {
                    ShopElement element = new ShopElement(relic, new GridPosition(3 + r, 2));
                    items.put(relic, element);
                    return element;
                }
            }
            return null;
        }

        public static void Postfix(ShopScreen __instance) {
            ShopElement newItem = getHoveredItem(__instance);
            if (newItem != null && newItem != hoveredItem) {
                Output.setUI(newItem);
                hoveredItem = newItem;
            }
        }
    }
}
