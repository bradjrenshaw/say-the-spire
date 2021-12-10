import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

import org.apache.commons.lang3.ObjectUtils.Null;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import sayTheSpire.ui.elements.AchievementElement;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.Output;

public class StatsScreenPatch {

    @SpirePatch(clz = StatsScreen.class, method = "update")
    public static class UpdatePatch {

        private static UIElement prevElement = null;
        private static AchievementItem prevAchievement = null;

        private static AchievementElement getHoveredAchievement(StatsScreen screen) {
            for (AchievementItem achievement : screen.achievements.items) {
                if (achievement.hb.hovered && achievement != prevAchievement) {
                    prevAchievement = achievement;
                    return new AchievementElement(achievement);
                }
            }
            return null;
        }

        private static UIElement getHoveredElement(StatsScreen screen) {
            AchievementElement achievement = getHoveredAchievement(screen);
            if (achievement != null)
                return achievement;
            return null;
        }

        public static void Postfix(StatsScreen __instance) {
            UIElement element = getHoveredElement(__instance);
            if (element != prevElement) {
                prevElement = element;
                if (element != null)
                    Output.setUI(element);
            }
        }
    }
}
