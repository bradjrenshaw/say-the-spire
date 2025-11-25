import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.EventTextEvent;

public class CardCrawlGamePatch {

    @SpirePatch(clz = CardCrawlGame.class, method = "create")
    public static class CardCrawlGameCreatePatch {

        public static void Prefix() {
            Output.preSetup();
        }
    }

    @SpirePatch(clz = CardCrawlGame.class, method = "dispose")
    public static class DisposePatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CardCrawlGame __instance) {
            Output.shutdown();
        }

        public static class Locator extends SpireInsertLocator {

            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(LogManager.class, "shutdown");
                return LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
            }
        }
    }

    @SpirePatch(clz = CardCrawlGame.class, method = "update")
    public static class updatePatchPrefix {

        public static void Prefix(CardCrawlGame __instance) {
            Output.uiManager.updateFirst();
        }
    }

    @SpirePatch(clz = CardCrawlGame.class, method = "update")
    public static class UpdatePatchPostfix {

        public static void updateGameEvents() {
            if (Output.eventText != null) {
                Output.event(new EventTextEvent(TextParser.parse(Output.eventText)));
                Output.eventText = null;
            }
        }

        public static void Postfix(CardCrawlGame __instance) {
            Output.update();
            updateGameEvents();
        }
    }
}
