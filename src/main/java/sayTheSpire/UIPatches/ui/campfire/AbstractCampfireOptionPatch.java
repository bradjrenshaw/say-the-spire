import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import sayTheSpire.Output;
import sayTheSpire.utils.CampfireUtils;

@SpirePatch(clz = AbstractCampfireOption.class, method = "update")
public class AbstractCampfireOptionPatch {

    public static void Postfix(AbstractCampfireOption __instance) {
        if (__instance.hb.justHovered) {
            Output.text(CampfireUtils.getOptionLabel(__instance), true);
            Output.setupUIBuffer(CampfireUtils.getOptionUIBuffer(__instance));
        }
    }
}
