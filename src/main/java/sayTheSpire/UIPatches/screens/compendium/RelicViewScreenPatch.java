import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.compendium.RelicViewScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.ui.positions.CategoryListPosition;

public class RelicViewScreenPatch {

    @SpirePatch(clz = RelicViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(RelicViewScreen __instance) {
            AbstractRelic hoveredRelic = (AbstractRelic) ReflectionHacks.getPrivate(__instance, RelicViewScreen.class,
                    "hoveredRelic");
            if (hoveredRelic != null && hoveredRelic.hb.justHovered) {
                ArrayList<AbstractRelic> relicGroup = (ArrayList<AbstractRelic>) ReflectionHacks.getPrivate(__instance,
                        RelicViewScreen.class, "relicGroup");
                LocalizationContext localization = Output.localization.getContext("ui.screens.RelicViewScreen");
                String key = hoveredRelic.tier.name().toLowerCase();
                CategoryListPosition position = null;
                String localizedType = localization.localize("types." + key);
                if (localizedType == null)
                    localizedType = key;
                localization.put("category", localizedType);
                String category = localization.localize("categoryLabel");
                if (category != null)
                    position = new CategoryListPosition(relicGroup.indexOf(hoveredRelic), relicGroup.size(), category);
                RelicElement element = new RelicElement(hoveredRelic, RelicElement.RelicLocation.COMPENDIUM, position);
                Output.setUI(element);
            }
        }
    }
}
