import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import sayTheSpire.ui.elements.BlightElement;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;

public class AbstractBlightPatch {

    @SpirePatch(clz = AbstractBlight.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(AbstractBlight __instance) {
            if (!__instance.hb.justHovered)
                return;
            BlightElement.BlightLocation location = BlightElement.BlightLocation.OTHER;
            AbstractPlayer player = OutputUtils.getPlayer();
            if (player != null && player.blights.indexOf(__instance) >= 0) {
                location = BlightElement.BlightLocation.MAIN_SCREEN;
            }
            BlightElement element = new BlightElement(__instance, location);
            Output.setUI(element);
        }
    }
}
