import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;
import com.megacrit.cardcrawl.screens.options.Slider;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.DropdownElement;

public class OptionsPanelPatch {

    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Options Tip");

    public static final String[] MSG = tutorialStrings.TEXT;

    public static final String[] LABEL = tutorialStrings.LABEL;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OptionsPanel");

    public static final String[] TEXT = uiStrings.TEXT;

    private static String graphicsOptions[] = TextParser.parse(TEXT[3]).split("\n");

    public static void initializeFPSDropdown(OptionsPanel panel, DropdownMenu dropdown) {
        String name = TextParser.parse(graphicsOptions[1]);
        String options[] = (String[]) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "FRAMERATE_LABELS");
        UIRegistry.register(dropdown, new DropdownElement(dropdown, name, options));
    }

    public static void initializeLanguageDropdown(OptionsPanel panel, DropdownMenu dropdown) {
        String name = TextParser.parse(TEXT[13]);
        String options[] = panel.languageLabels();
        UIRegistry.register(dropdown, new DropdownElement(dropdown, name, options));
    }

    public static void initializeResoDropdown(OptionsPanel panel, DropdownMenu dropdown) {
        String name = TextParser.parse(graphicsOptions[0]);
        String options[] = new String[Settings.displayOptions.size()];
        for (int i = 0; i < Settings.displayOptions.size(); i++) {
            options[i] = TextParser.parse(Settings.displayOptions.get(i).uiString());
        }
        UIRegistry.register(dropdown, new DropdownElement(dropdown, name, options));
    }

    @SpirePatch(clz = OptionsPanel.class, method = SpirePatch.CONSTRUCTOR)
    @SpirePatch(clz = OptionsPanel.class, method = "refresh")
    public static class ConstructorRefreshPatch {

        public static void Postfix(OptionsPanel panel) {
            DropdownMenu fps = panel.fpsDropdown;
            DropdownMenu reso = panel.resoDropdown;
            DropdownMenu language = (DropdownMenu) ReflectionHacks.getPrivate(panel, OptionsPanel.class,
                    "languageDropdown");
            initializeFPSDropdown(panel, fps);
            initializeResoDropdown(panel, reso);
            initializeLanguageDropdown(panel, language);
        }
    }

    @SpirePatch(clz = OptionsPanel.class, method = "update")
    public static class UpdatePatch {
        public static void handleInfoControls(OptionsPanel panel) {
            Slider masterSlider = (Slider) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "masterSlider");
            Slider bgmSlider = (Slider) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "bgmSlider");
            Slider sfxSlider = (Slider) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "sfxSlider");
            if (masterSlider.bgHb.hovered || bgmSlider.bgHb.hovered || sfxSlider.bgHb.hovered) {
                Output.bufferContext = "";
            } else {
                Output.bufferContext = "options";
            }
        }

        public static void Postfix(OptionsPanel __instance) {
            handleInfoControls(__instance);
        }
    }
}
