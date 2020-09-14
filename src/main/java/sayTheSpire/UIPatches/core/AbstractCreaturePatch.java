import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import sayTheSpire.events.GainBlockEvent;
import sayTheSpire.Output;

public class AbstractCreaturePatch {

  @SpirePatch(clz=AbstractCreature.class, method="addBlock")
public static class addBlockPatch {

  @SpireInsertPatch(locator=Locator.class, localvars={"tmp"})
  public static void Insert(AbstractCreature __instance, float tmp) {
    Output.event(new GainBlockEvent(__instance, (int)Math.floor(tmp)));
  }

  private static class Locator extends SpireInsertLocator {

    public int[] Locate(CtBehavior ctMethodToPatch)
    throws CannotCompileException, PatchingException {
      Matcher matcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "gainBlockAnimation");
      return LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
    }
  }
}


}
