import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import sayTheSpire.ui.elements.OrbElement;
import sayTheSpire.Output;

public class AbstractOrbPatch {

    @SpirePatch(clz = AbstractOrb.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(AbstractOrb __instance) {
            if (__instance.hb.justHovered) {
                Output.setUI(new OrbElement(__instance));
            }
        }
    }
}
