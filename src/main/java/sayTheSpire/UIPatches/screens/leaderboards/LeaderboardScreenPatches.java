import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardEntry;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.buffers.LeaderboardBuffer;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.elements.LeaderboardFilterButtonElement;
import sayTheSpire.ui.positions.CategoryListPosition;

public class LeaderboardScreenPatches {

    public static int prevIndex = -1;

    public static void updateLeaderboardBuffer(LeaderboardScreen screen, LeaderboardBuffer buffer) {
        buffer.clear();
        for (LeaderboardEntry entry : screen.entries) {
            buffer.add(entry);
        }
    }

    public static void registerButtonList(ArrayList<FilterButton> buttons, String filterName) {
        int count = buttons.size();
        if (count == 0)
            return;
        for (int i = 0; i < count; i++) {
            FilterButton button = buttons.get(i);
            CategoryListPosition position = new CategoryListPosition(i, count,
                    Output.localization.localize("ui.screens.LeaderboardScreen." + filterName));
            UIRegistry.register(button, new LeaderboardFilterButtonElement(button, position));
        }
    }

    @SpirePatch(clz = LeaderboardScreen.class, method = "hide")
    public static class HidePatch {

        public static void Postfix(LeaderboardScreen __instance) {
            LeaderboardBuffer buffer = (LeaderboardBuffer) Output.buffers.getBuffer("leaderboard");
            buffer.setEnabled(false);
        }
    }

    @SpirePatch(clz = LeaderboardScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(LeaderboardScreen __instance) {
            registerButtonList(__instance.charButtons, "character buttons");
            registerButtonList(__instance.regionButtons, "region buttons");
            registerButtonList(__instance.typeButtons, "type buttons");
        }
    }

    @SpirePatch(clz = LeaderboardScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(LeaderboardScreen __instance) {
            LeaderboardBuffer buffer = (LeaderboardBuffer) Output.buffers.getBuffer("leaderboard");
            if (__instance.waiting) {
                buffer.clear();
                prevIndex = -1;
            } else if (__instance.currentStartIndex != prevIndex) {
                Output.textLocalized("ui.screens.dailyScreen.scoreIndicesString", false, "start",
                        __instance.currentStartIndex, "end",
                        __instance.currentStartIndex + __instance.entries.size() - 1);
                updateLeaderboardBuffer(__instance, buffer);
                prevIndex = __instance.currentStartIndex;
            }
        }
    }
}
