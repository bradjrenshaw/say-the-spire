import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import sayTheSpire.Output;
import sayTheSpire.events.PowerEvent;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class ApplyPowerActionPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(ApplyPowerAction __instance) {
        AbstractGameEffect effect = AbstractDungeon.effectList.get(AbstractDungeon.effectList.size() - 1);
        Output.event(new PowerEvent(__instance.source, __instance.target, effect));
    }

    private static class Locator extends SpireInsertLocator {

        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher buffMatcher = new Matcher.NewExprMatcher(PowerBuffEffect.class);
            Matcher debuffMatcher = new Matcher.NewExprMatcher(PowerDebuffEffect.class);
            int buffArray[] = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), buffMatcher);
            int debuffArray[] = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), debuffMatcher);
            int combinedArray[] = new int[buffArray.length + debuffArray.length];
            System.arraycopy(buffArray, 0, combinedArray, 0, buffArray.length);
            System.arraycopy(debuffArray, 0, combinedArray, buffArray.length, debuffArray.length);
            for (int c = 0; c < combinedArray.length; c++) {
                combinedArray[c] = combinedArray[c] + 1;
            }
            return combinedArray;
        }
    }
}
