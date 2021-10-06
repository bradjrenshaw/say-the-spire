import java.util.ArrayList;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.compendium.RelicViewScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.ui.positions.CategoryListPosition;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.Output;

public class RelicViewScreenPatch {

    @SpirePatch(clz = RelicViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(RelicViewScreen __instance) {
            AbstractRelic hoveredRelic = (AbstractRelic) ReflectionHacks.getPrivate(__instance, RelicViewScreen.class,
                    "hoveredRelic");
            if (hoveredRelic != null && hoveredRelic.hb.justHovered) {
                ArrayList<AbstractRelic> relicGroup = (ArrayList<AbstractRelic>) ReflectionHacks.getPrivate(__instance,
                        RelicViewScreen.class, "relicGroup");
                String category = hoveredRelic.tier.name().toLowerCase() + " relics";
                RelicElement element = new RelicElement(hoveredRelic, RelicElement.RelicLocation.COMPENDIUM,
                        new CategoryListPosition(relicGroup.indexOf(hoveredRelic), relicGroup.size(), category));
                Output.setUI(element);
            }
        }
    }
}
