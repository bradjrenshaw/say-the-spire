package sayTheSpire.UIPatches.credits;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.credits.CreditLine;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.Output;

public class CreditsScreenPatches {

    private static float scrollStart = 0;
    private static TreeSet<CreditLine> prevVisibleLines = null;
    private static Boolean readThanks = false;

    @SpirePatch(clz = CreditsScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(CreditsScreen __instance) {
            scrollStart = (float) ReflectionHacks.getPrivateStatic(CreditsScreen.class, "SCROLL_START_Y");
            prevVisibleLines = new TreeSet(new CreditLineComparator());
            readThanks = false;
        }
    }

    @SpirePatch(clz = CreditsScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(CreditsScreen __instance) {
            ArrayList<CreditLine> lines = (ArrayList<CreditLine>) ReflectionHacks.getPrivate(__instance,
                    CreditsScreen.class, "lines");
            if (lines == null)
                return;
            TreeSet<CreditLine> visibleLines = new TreeSet(new CreditLineComparator());
            float scrollY = (float) ReflectionHacks.getPrivate(__instance, CreditsScreen.class, "currentY");
            for (CreditLine line : lines) {
                float lineY = (float) ReflectionHacks.getPrivate(line, CreditLine.class, "y");
                if (lineY + scrollY >= 0 && lineY + scrollY <= Settings.HEIGHT) {
                    visibleLines.add(line);
                }
            }
            TreeSet<CreditLine> difference = new TreeSet<>(new CreditLineComparator());
            difference.addAll(visibleLines);
            difference.removeAll(prevVisibleLines);
            for (CreditLine line : difference) {
                Output.text(line.text, false);
            }
            prevVisibleLines = visibleLines;
            float thankYouTimer = (float) ReflectionHacks.getPrivate(__instance, CreditsScreen.class, "thankYouTimer");
            if (thankYouTimer <= 0.0f && !readThanks) {
                String thanksMsg = (String) ReflectionHacks.getPrivate(__instance, CreditsScreen.class, "THANKS_MSG");
                Output.text(thanksMsg, false);
                readThanks = true;
            }
        }
    }
}
