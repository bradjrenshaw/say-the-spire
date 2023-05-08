import java.lang.reflect.Method;
import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.runHistory.CopyableTextElement;
import com.megacrit.cardcrawl.screens.runHistory.ModIcons;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryPath;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.runHistory.RunPathElement;
import com.megacrit.cardcrawl.screens.runHistory.TinyCard;
import com.megacrit.cardcrawl.screens.stats.RunData;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.buffers.Buffer;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.elements.DropdownElement;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.ui.elements.RunPathViewElement;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.elements.CardElement.CardLocation;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.TextParser;
import sayTheSpire.Output;

public class RunHistoryScreenPatches {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RunHistoryScreen");

    public static final String[] TEXT = uiStrings.TEXT;

    private static Object prevHovered = null;

    public static DropdownMenu getDropdown(RunHistoryScreen screen, String name) {
        return (DropdownMenu) ReflectionHacks.getPrivate(screen, RunHistoryScreen.class, name);
    }

    public static Boolean dropdownHovered(DropdownMenu dropdown) {
        if (dropdown.rows.isEmpty())
            return false;
        Hitbox hb = dropdown.getHitbox();
        if (hb == null)
            return false;
        return hb.hovered;
    }

    public static Object getHovered(RunHistoryScreen screen) {
        DropdownMenu runsDropdown = getDropdown(screen, "runsDropdown");
        if (dropdownHovered(runsDropdown))
            return runsDropdown;
        DropdownMenu characterFilter = getDropdown(screen, "characterFilter");
        if (dropdownHovered(characterFilter))
            return characterFilter;
        DropdownMenu winLossFilter = getDropdown(screen, "winLossFilter");
        if (dropdownHovered(winLossFilter))
            return winLossFilter;
        DropdownMenu runTypeFilter = getDropdown(screen, "runTypeFilter");
        if (dropdownHovered(runTypeFilter))
            return runTypeFilter;
        ArrayList<TinyCard> cards = (ArrayList<TinyCard>) ReflectionHacks.getPrivate(screen, RunHistoryScreen.class,
                "cards");
        for (TinyCard card : cards) {
            if (card.hb.hovered) {
                return card;
            }
        }
        RunHistoryPath path = (RunHistoryPath) ReflectionHacks.getPrivate(screen, RunHistoryScreen.class, "runPath");
        if (path != null) {
            for (RunPathElement e : path.pathElements) {
                if (e.hb.hovered)
                    return e;
            }
        }
        ArrayList<AbstractRelic> relics = (ArrayList<AbstractRelic>) ReflectionHacks.getPrivate(screen,
                RunHistoryScreen.class, "relics");
        if (relics != null) {
            for (AbstractRelic relic : relics) {
                if (relic.hb.hovered)
                    return relic;
            }
        }
        return null;
    }

    static void setupExtraBuffer(RunHistoryScreen screen) {
        Buffer buffer = Output.buffers.getBuffer("UI extra");
        if (buffer == null)
            return;
        buffer.clear();
        buffer.setEnabled(true);
        RunData data = (RunData) ReflectionHacks.getPrivate(screen, RunHistoryScreen.class, "viewedRun");
        if (data == null) {
            buffer.add(TextParser.parse(TEXT[4]));
        } else {
            try {
                Method characterText = RunHistoryScreen.class.getDeclaredMethod("characterText", String.class);
                characterText.setAccessible(true);
                String name = (String) characterText.invoke(screen, data.character_chosen);
                buffer.add(name);
                CopyableTextElement seed = (CopyableTextElement) ReflectionHacks.getPrivate(screen,
                        RunHistoryScreen.class, "seedElement");
                String seedText = seed.getText();
                if (seedText != null && !seedText.isEmpty()) {
                    buffer.add(seedText);
                }
                CopyableTextElement customSeed = (CopyableTextElement) ReflectionHacks.getPrivate(screen,
                        RunHistoryScreen.class, "secondSeedElement");
                String customSeedText = customSeed.getText();
                if (customSeedText != null && !customSeedText.isEmpty()) {
                    buffer.add(customSeedText);
                }
                String specialMode = "";
                if (data.is_daily) {
                    specialMode = TEXT[27];
                } else if (data.is_ascension_mode) {
                    specialMode = TEXT[5] + data.ascension_level;
                }
                String result = "";
                if (data.victory) {
                    result = TEXT[8] + specialMode;
                } else {
                    if (data.killed_by == null) {
                        result = String.format(TEXT[7], Integer.valueOf(data.floor_reached)) + specialMode;
                    } else {
                        result = String.format(TEXT[6], Integer.valueOf(data.floor_reached),
                                MonsterHelper.getEncounterName(data.killed_by)) + specialMode;
                    }
                }
                buffer.add(result);
                buffer.add(String.format(TEXT[22], Integer.valueOf(data.score)));
                ModIcons icons = (ModIcons) ReflectionHacks.getPrivate(screen, RunHistoryScreen.class, "modIcons");
                ArrayList<AbstractDailyMod> mods = (ArrayList<AbstractDailyMod>) ReflectionHacks.getPrivate(icons,
                        ModIcons.class, "dailyModList");
                for (AbstractDailyMod m : mods) {
                    buffer.add(m.name + "\n" + m.description);
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    @SpirePatch(clz = RunHistoryScreen.class, method = "changedSelectionTo")
    public static class ChangedSelectionToPatch {

        public static void Postfix(RunHistoryScreen __instance) {
            // This resets the dropdown object entirely so it needs to be reregistered
            DropdownMenu runsDropdown = getDropdown(__instance, "runsDropdown");
            UIRegistry.register(runsDropdown, new DropdownElement(runsDropdown, "runs"));
        }
    }

    @SpirePatch(clz = RunHistoryScreen.class, method = "refreshData")
    public static class RefreshDataPatch {

        public static void Postfix(RunHistoryScreen __instance) {
            DropdownMenu runsDropdown = getDropdown(__instance, "runsDropdown");
            UIRegistry.register(runsDropdown, new DropdownElement(runsDropdown, "runs", false));
            DropdownMenu characterFilter = getDropdown(__instance, "characterFilter");
            UIRegistry.register(characterFilter, new DropdownElement(characterFilter, "character filter", false));
            DropdownMenu winLossFilter = getDropdown(__instance, "winLossFilter");
            UIRegistry.register(winLossFilter, new DropdownElement(winLossFilter, "win loss filter", false));
            DropdownMenu runTypeFilter = getDropdown(__instance, "runTypeFilter");
            UIRegistry.register(runTypeFilter, new DropdownElement(runTypeFilter, "run type", false));
        }
    }

    @SpirePatch(clz = RunHistoryScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(RunHistoryScreen __instance) {
            Object hovered = getHovered(__instance);
            if (hovered != null && hovered != prevHovered) {
                UIElement element = UIRegistry.getUI(hovered);
                if (element != null)
                    Output.setUI(element);
                else if (hovered instanceof TinyCard) {
                    TinyCard card = (TinyCard) hovered;
                    Output.setUI(new CardElement(card.card, CardLocation.OTHER,
                            new GridPosition(card.col + 1, card.row + 1)));
                } else if (hovered instanceof RunPathElement) {
                    RunPathElement path = (RunPathElement) hovered;
                    Output.setUI(new RunPathViewElement(path, new GridPosition(path.col + 1, path.row + 1)));
                } else if (hovered instanceof AbstractRelic) {
                    AbstractRelic relic = (AbstractRelic) hovered;
                    ArrayList<AbstractRelic> relics = (ArrayList<AbstractRelic>) ReflectionHacks.getPrivate(__instance,
                            RunHistoryScreen.class, "relics");
                    GridPosition position = null;
                    if (relics != null) {
                        int index = relics.indexOf(relic);
                        if (index >= 0) {
                            position = new GridPosition(index % 15 + 1, index / 15 + 1);
                        }
                    }
                    Output.setUI(new RelicElement(relic, RelicElement.RelicLocation.OTHER, position));
                }
                setupExtraBuffer(__instance);
            }
            prevHovered = hovered;
        }
    }
}
