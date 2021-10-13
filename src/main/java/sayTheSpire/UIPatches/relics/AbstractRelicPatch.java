import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.Output;
import sayTheSpire.utils.RelicUtils;
import sayTheSpire.utils.OutputUtils;

@SpirePatch(clz = AbstractRelic.class, method = "update")
public class AbstractRelicPatch {

    public static void Prefix(AbstractRelic __instance) {
        if (__instance.hb.justHovered) {
            AbstractPlayer player = OutputUtils.getPlayer();
            RelicElement.RelicLocation location = RelicElement.RelicLocation.OTHER;
            if (player != null && player.relics.indexOf(__instance) >= 0) {
                location = RelicElement.RelicLocation.MAIN_SCREEN;
            } else {
                return;
            }
            RelicElement element = new RelicElement(__instance, location);
            Output.setUI(element);
        }
    }
}
