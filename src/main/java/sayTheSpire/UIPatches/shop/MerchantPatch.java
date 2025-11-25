import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

@SpirePatch(clz = Merchant.class, method = "update")
public class MerchantPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = { "msg" })
    public static void Insert(Merchant __instance, String msg) {
        Output.event(new DialogueEvent("says", "merchant", TextParser.parse(msg)));
    }

    private static class Locator extends SpireInsertLocator {

        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.NewExprMatcher(SpeechBubble.class);
            return LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
        }
    }
}
