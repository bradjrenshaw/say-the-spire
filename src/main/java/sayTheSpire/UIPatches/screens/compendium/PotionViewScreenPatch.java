import java.util.ArrayList;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.compendium.PotionViewScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.ui.elements.PotionElement;
import sayTheSpire.ui.positions.CategoryListPosition;
import sayTheSpire.Output;

public class PotionViewScreenPatch {

    public static PotionElement findPotionInList(PotionViewScreen screen, String listName) {
        ArrayList<AbstractPotion> list = (ArrayList<AbstractPotion>) ReflectionHacks.getPrivate(screen,
                PotionViewScreen.class, listName);
        if (list == null)
            return null;
        int listCount = list.size();
        for (int p = 0; p < listCount; p++) {
            AbstractPotion potion = list.get(p);
            if (potion.hb.justHovered) {
                String category;
                switch (listName) {
                case "commonPotions":
                    category = "common potions";
                    break;
                case "uncommonPotions":
                    category = "uncommon potions";
                    break;
                case "rarePotions":
                    category = "rare potions";
                    break;
                default:
                    category = "unknown";
                }
                CategoryListPosition position = new CategoryListPosition(p, listCount, category);
                return new PotionElement(potion, PotionElement.PotionLocation.COMPENDIUM, position);
            }
        }
        return null;
    }

    public static PotionElement getJustHoveredPotion(PotionViewScreen screen) {
        PotionElement potion = findPotionInList(screen, "commonPotions");
        if (potion == null)
            potion = findPotionInList(screen, "uncommonPotions");
        if (potion == null)
            potion = findPotionInList(screen, "rarePotions");
        if (potion != null) {
            return potion;
        }
        return null;
    }

    @SpirePatch(clz = PotionViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(PotionViewScreen __instance) {
            PotionElement potion = getJustHoveredPotion(__instance);
            if (potion != null) {
                Output.setUI(potion);
            }
        }
    }
}
