import java.util.ArrayList;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.elements.PotionElement;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

public class ShopScreenPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("MerchantImageTextNotInGame");
    public static final String[] TEXT = uiStrings.TEXT;

    private static ArrayList<UIElement> items = new ArrayList();
    private static UIElement hoveredItem = null;

    @SpirePatch(clz = ShopScreen.class, method = "createSpeech", paramtypez = { String.class })
    public static class CreateSpeechPatch {

        public static void Prefix(ShopScreen __instance, String msg) {
            Output.event(new DialogueEvent("says", "Merchant", TextParser.parse(msg, "talk")));
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "init")
    public static class InitPatch {

        public static void Postfix(ShopScreen __instance) {
            items.clear();
            hoveredItem = null;
            OnSaleTag tag = (OnSaleTag) ReflectionHacks.getPrivate(__instance, ShopScreen.class, "saleTag");
            int coloredCardCount = __instance.coloredCards.size();
            for (int c = 0; c < coloredCardCount; c++) {
                AbstractCard card = __instance.coloredCards.get(c);
                CardElement element = new CardElement(card, CardElement.CardLocation.SHOP, new GridPosition(c + 1, 1));
                // Only top row cards can be on sale and only one card ever can be per shop
                element.setPrice(card.price, card == tag.card);
                UIRegistry.register(card, element);
                items.add(element);
            }
            int colorlessCardCount = __instance.colorlessCards.size();
            for (int c = 0; c < colorlessCardCount; c++) {
                AbstractCard card = __instance.colorlessCards.get(c);
                CardElement element = new CardElement(card, CardElement.CardLocation.SHOP, new GridPosition(c + 1, 2));
                element.setPrice(card.price, false);
                UIRegistry.register(card, element);
                items.add(element);
            }
            ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>) ReflectionHacks.getPrivate(__instance,
                    ShopScreen.class, "relics");
            int relicCount = relics.size();
            for (int r = 0; r < relicCount; r++) {
                StoreRelic storeRelic = relics.get(r);
                AbstractRelic relicObj = storeRelic.relic;
                RelicElement element = new RelicElement(relicObj, RelicElement.RelicLocation.SHOP,
                        new GridPosition(3 + r, 2));
                element.setPrice(storeRelic.price, false);
                UIRegistry.register(storeRelic, element);
                items.add(element);
            }
            ArrayList<StorePotion> potions = (ArrayList<StorePotion>) ReflectionHacks.getPrivate(__instance,
                    ShopScreen.class, "potions");
            int potionCount = potions.size();
            for (int p = 0; p < potionCount; p++) {
                StorePotion storePotion = potions.get(p);
                AbstractPotion potionObj = storePotion.potion;
                PotionElement element = new PotionElement(potionObj, PotionElement.PotionLocation.SHOP,
                        new GridPosition(3 + p, 3));
                element.setPrice(storePotion.price, false);
                UIRegistry.register(storePotion, element);
                items.add(element);
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "update")
    public static class UpdatePatch {

        public static boolean isPurgeHovered = false;

        public static CardElement getHoveredCard(ShopScreen screen) {
            for (AbstractCard card : screen.coloredCards) {
                if (card.hb.hovered)
                    return (CardElement) UIRegistry.getUI(card);
            }
            for (AbstractCard card : screen.colorlessCards) {
                if (card.hb.hovered)
                    return (CardElement) UIRegistry.getUI(card);
            }
            return null;
        }

        public static UIElement getHoveredItem(ShopScreen screen) {
            CardElement hoveredCard = getHoveredCard(screen);
            PotionElement hoveredPotion = getHoveredPotion(screen);
            RelicElement hoveredRelic = getHoveredRelic(screen);
            ButtonElement hoveredPurge = getHoveredPurge(screen);
            if (hoveredCard != null)
                return hoveredCard;
            if (hoveredPotion != null)
                return hoveredPotion;
            if (hoveredRelic != null)
                return hoveredRelic;
            if (hoveredPurge != null)
                return hoveredPurge;
            return null;
        }

        public static PotionElement getHoveredPotion(ShopScreen screen) {
            ArrayList<StorePotion> potions = (ArrayList<StorePotion>) ReflectionHacks.getPrivate(screen,
                    ShopScreen.class, "potions");
            for (StorePotion p : potions) {
                if (p.potion.hb.hovered) {
                    return (PotionElement) UIRegistry.getUI(p);
                }
            }
            return null;
        }

        public static ButtonElement getHoveredPurge(ShopScreen screen) {
            Boolean gameIsPurgeHovered = (Boolean) ReflectionHacks.getPrivate(screen, ShopScreen.class, "purgeHovered");
            if (isPurgeHovered && gameIsPurgeHovered)
                return (ButtonElement) hoveredItem;
            if (!isPurgeHovered && gameIsPurgeHovered) {
                String label;
                if (screen.purgeAvailable) {
                    label = TEXT[0] + " (" + ShopScreen.actualPurgeCost + " gold)";
                } else {
                    label = TEXT[0] + " ( " + TEXT[3] + ")";
                }
                ButtonElement button = new ButtonElement(label, TEXT[0], new GridPosition(6, 2));
                isPurgeHovered = true;
                return button;
            } else {
                isPurgeHovered = false;
            }
            return null;
        }

        public static RelicElement getHoveredRelic(ShopScreen screen) {
            ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>) ReflectionHacks.getPrivate(screen, ShopScreen.class,
                    "relics");
            for (StoreRelic r : relics) {
                if (r.relic.hb.hovered) {
                    return (RelicElement) UIRegistry.getUI(r);
                }
            }
            return null;
        }

        public static void Postfix(ShopScreen __instance) {
            UIElement newItem = getHoveredItem(__instance);
            if (newItem != null && newItem != hoveredItem) {
                Output.setUI((UIElement) newItem);
                hoveredItem = newItem;
            }
        }
    }
}
