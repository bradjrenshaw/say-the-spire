import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import sayTheSpire.Output;
import sayTheSpire.ui.OrbElement;

@SpirePatch(clz = AbstractOrb.class, method = "update")
public class AbstractOrbPatch {

  public static void Postfix(AbstractOrb __instance) {
    if (__instance.hb.justHovered) {
      Output.setUI(new OrbElement(__instance));
    }
  }
}
