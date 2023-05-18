import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyScreen;
import com.megacrit.cardcrawl.daily.TimeHelper;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ModHelper;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.buffers.Buffer;

public class DailyScreenPatch {

    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("DailyScreen")).TEXT;

    public static String getAchievementsString() {
        if (!Settings.usesTrophies) {
            return TextParser.parse(TEXT[15]);
        }
        return TextParser.parse(TEXT[18]);
    }

    public static String getCharacterString(DailyScreen screen) {
        return screen.todaysChar != null ? Output.localization.localize("ui.screens.dailyScreen.characterString",
                "label", TEXT[5], "name", screen.todaysChar.getLocalizedCharacterName()) : null;
    }

    public static String getDateString() {
        if (TimeHelper.isOfflineMode()) {
            return Output.localization.localize("ui.screens.dailyScreen.offlineDateString", "date",
                    TimeHelper.getTodayDate(), "label", TEXT[16]);
        }
        if (TimeHelper.isTimeSet) {
            return TimeHelper.getTodayDate();
        }
        return null;
    }

    public static String getProgressString(DailyScreen screen) {
        long lastDaily = (long) ReflectionHacks.getPrivate(screen, DailyScreen.class, "lastDaily");
        if (lastDaily == TimeHelper.daySince1970()) {
            return TEXT[2];
        }
        return null;
    }

    public static String getStatusString(DailyScreen screen) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDateString() + "\n");
        sb.append(getTimeRemainingString());
        String character = getCharacterString(screen);
        if (character != null)
            sb.append(character + "\n");
        String progress = getProgressString(screen);
        if (progress != null)
            sb.append(progress + "\n");
        sb.append(getModsString() + "\n");
        String achievements = getAchievementsString();
        if (achievements != null)
            sb.append(achievements);
        return sb.toString();
    }

    public static String getTimeRemainingString() {
        return TimeHelper.isTimeSet ? Output.localization.localize("ui.screens.dailyScreen.timeRemainingString",
                "label", TEXT[7], "time", TimeHelper.getTimeLeft()) : null;
    }

    public static ArrayList<String> getUIBuffer(DailyScreen screen) {
        ArrayList<String> modInfo = new ArrayList();
        if (!TimeHelper.isTimeSet) {
            modInfo.add(TEXT[1]);
            return modInfo;
        }
        modInfo.add(getDateString());
        modInfo.add(getTimeRemainingString());
        modInfo.add(getCharacterString(screen));
        for (AbstractDailyMod mod : ModHelper.enabledMods) {
            modInfo.add(Output.localization.localize("ui.screens.dailyScreen.modString", "name", mod.name,
                    "description", TextParser.parse(mod.description)));
        }
        modInfo.add(getAchievementsString());
        return modInfo;
    }

    public static String getModsString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractDailyMod mod : ModHelper.enabledMods) {
            sb.append(Output.localization.localize("ui.screens.dailyScreen.modString", "name", mod.name, "description",
                    TextParser.parse(mod.description)) + "\n");
        }
        return sb.toString();
    }

    @SpirePatch(clz = DailyScreen.class, method = "hide")
    public static class HidePatch {

        public static void Postfix(DailyScreen __instance) {
            Buffer buffer = Output.buffers.getBuffer("leaderboard");
            buffer.setEnabled(false);
        }
    }

    @SpirePatch(clz = DailyScreen.class, method = "determineLoadout")
    public static class OpenPatch {

        public static void Postfix(DailyScreen __instance) {
            Output.text(getStatusString(__instance), false);
            Output.setupUIBuffer(getUIBuffer(__instance));
            Buffer buffer = Output.buffers.getBuffer("leaderboard");
            buffer.setEnabled(true);
        }
    }

    @SpirePatch(clz = DailyScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(DailyScreen __instance) {
        }
    }
}
