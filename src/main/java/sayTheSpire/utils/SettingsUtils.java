/*
 * package sayTheSpire.utils;
 * 
 * import java.text.DecimalFormat; import java.util.ArrayList; import java.util.HashMap; import
 * com.megacrit.cardcrawl.core.CardCrawlGame; import com.megacrit.cardcrawl.localization.TutorialStrings; import
 * com.megacrit.cardcrawl.localization.UIStrings; import com.megacrit.cardcrawl.screens.options.DropdownMenu; import
 * com.megacrit.cardcrawl.screens.options.Slider; import com.megacrit.cardcrawl.screens.options.OptionsPanel; import
 * com.megacrit.cardcrawl.screens.options.ToggleButton; import basemod.ReflectionHacks; import
 * sayTheSpire.ui.ButtonElement; import sayTheSpire.ui.DropdownElement; import sayTheSpire.TextParser;
 * 
 * 
 * public class SettingsUtils {
 * 
 * private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Options Tip");
 * 
 * public static final String[] MSG = tutorialStrings.TEXT;
 * 
 * public static final String[] LABEL = tutorialStrings.LABEL;
 * 
 * private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OptionsPanel");
 * 
 * public static final String[] TEXT = uiStrings.TEXT;
 * 
 * public static String[] ToggleButtons = {"fsToggle", "wfsToggle", "vSyncToggle", "ssToggle", "ambienceToggle",
 * "muteBgToggle", "sumToggle", "blockToggle", "confirmToggle", "effectsToggle", "fastToggle", "cardKeyOverlayToggle",
 * "uploadToggle", "longPressToggle", "bigTextToggle", "playtesterToggle"};
 * 
 * public static OptionsPanel currentOptionsPanel = null;
 * 
 * 
 * 
 * public static HashMap<DropdownMenu, UIElement> dropdowns = new HashMap();
 * 
 * 
 * 
 * public static DecimalFormat sliderFormat = new DecimalFormat("#");
 * 
 * public static HashMap<ToggleButton.ToggleBtnType, ButtonElement> toggleText = null; public static
 * HashMap<Slider.SliderType, SliderElement> sliderText = null;
 * 
 * static { setupOptionsText(); }
 * 
 * public static void setupOptionsText() {
 * 
 * toggleText = new HashMap(); String fullscreenStrings[] = TextParser.parse(TEXT[4]).split("\n");
 * toggleText.put(ToggleButton.ToggleBtnType.FULL_SCREEN, new UIElement(fullscreenStrings[0], LABEL[1], MSG[1]));
 * toggleText.put(ToggleButton.ToggleBtnType.W_FULL_SCREEN, new UIElement(fullscreenStrings[1], LABEL[2], MSG[2]));
 * toggleText.put(ToggleButton.ToggleBtnType.SCREEN_SHAKE, new UIElement(fullscreenStrings[2]));
 * 
 * //ambience options String ambienceOptions[] = TextParser.parse(TEXT[7]).split("\n");
 * toggleText.put(ToggleButton.ToggleBtnType.AMBIENCE_ON, new UIElement(ambienceOptions[0]));
 * toggleText.put(ToggleButton.ToggleBtnType.MUTE_IF_BG, new UIElement(ambienceOptions[1]));
 * 
 * //preferences String preferenceOptions[] = TextParser.parse(TEXT[9]).split("\n");
 * toggleText.put(ToggleButton.ToggleBtnType.SUM_DMG, new UIElement(preferenceOptions[0]));
 * toggleText.put(ToggleButton.ToggleBtnType.BLOCK_DMG, new UIElement(preferenceOptions[1]));
 * toggleText.put(ToggleButton.ToggleBtnType.HAND_CONF, new UIElement(preferenceOptions[2]));
 * toggleText.put(ToggleButton.ToggleBtnType.FAST_MODE, new UIElement(TEXT[10]));
 * 
 * //misc toggleText.put(ToggleButton.ToggleBtnType.UPLOAD_DATA, new UIElement(TEXT[14], TEXT[14], MSG[0])); String
 * vsyncLabel = TextParser.parse(TEXT[17]).split("\n")[2]; toggleText.put(ToggleButton.ToggleBtnType.V_SYNC, new
 * UIElement(vsyncLabel)); //note: fix tip toggleText.put(ToggleButton.ToggleBtnType.PLAYTESTER_ART, new
 * UIElement(TEXT[18])); toggleText.put(ToggleButton.ToggleBtnType.SHOW_CARD_HOTKEYS, new UIElement(TEXT[19]));
 * toggleText.put(ToggleButton.ToggleBtnType.EFFECTS, new UIElement(TEXT[21]));
 * toggleText.put(ToggleButton.ToggleBtnType.LONG_PRESS, new UIElement(TEXT[25]));
 * toggleText.put(ToggleButton.ToggleBtnType.BIG_TEXT, new UIElement(TEXT[26])); sliderText = new HashMap(); String
 * volumes[] = TextParser.parse(TEXT[6]).split("\n"); sliderText.put(Slider.SliderType.MASTER, new
 * UIElement(volumes[0])); sliderText.put(Slider.SliderType.BGM, new UIElement(volumes[1]));
 * sliderText.put(Slider.SliderType.SFX, new UIElement(volumes[2])); }
 * 
 * public static String getDropdownShort(DropdownMenu dropdown) { UIElement element = dropdowns.get(dropdown); String
 * value = getDropdownValueString(dropdown); if (element == null) { return "Unknown dropdown, report to mod developer.";
 * } return element.getName() + " dropdown " + value; }
 * 
 * public static String getDropdownValueString(DropdownMenu dropdown) { DropdownElement element =
 * (DropdownElement)dropdowns.get(dropdown); if (element == null) { return
 * "Error finding matching dropdown menu value, report to mod developer."; } return getDropdownValueString(dropdown,
 * dropdown.getSelectedIndex()); }
 * 
 * public static String getDropdownValueString(DropdownMenu dropdown, int index) { DropdownElement element =
 * (DropdownElement)dropdowns.get(dropdown); if (element == null) { return
 * "Error finding matching dropdown menu value, report to mod developer."; } return element.getOption(index); }
 * 
 * public static void setOptionsPanel(OptionsPanel panel, Boolean force) { if (!force && currentOptionsPanel == panel ||
 * panel == null) return; String graphicsOptions[] = TextParser.parse(TEXT[3]).split("\n"); UIElement fps =
 * dropdowns.get(panel.fpsDropdown); fps.setName(graphicsOptions[1]); UIElement reso =
 * dropdowns.get(panel.resoDropdown); reso.setName(graphicsOptions[0]); UIElement language =
 * dropdowns.get((DropdownMenu)ReflectionHacks.getPrivate(panel, OptionsPanel.class, "languageDropdown"));
 * language.setName(TEXT[13]); currentOptionsPanel = panel; }
 * 
 * public static String getSliderShort(Slider slider) { Slider.SliderType type =
 * (Slider.SliderType)ReflectionHacks.getPrivate(slider, Slider.class, "type"); return sliderText.get(type).getName() +
 * " slider " + getSliderValueString(slider); }
 * 
 * public static String getSliderValueString(Slider slider) { float volume = (float)ReflectionHacks.getPrivate(slider,
 * Slider.class, "volume"); return sliderFormat.format(volume * 100.0F) + '%'; }
 * 
 * public static ToggleButton getToggleButton(OptionsPanel panel, String name) { ToggleButton button =
 * (ToggleButton)ReflectionHacks.getPrivate(panel, OptionsPanel.class, name); return button; }
 * 
 * public static String getToggleButtonShort(ToggleButton button) { ToggleButton.ToggleBtnType type =
 * (ToggleButton.ToggleBtnType)ReflectionHacks.getPrivate(button, ToggleButton.class, "type"); if
 * (toggleText.containsKey(type)) { return TextParser.parse(toggleText.get(type).getName()) + " toggle button " +
 * getToggleButtonStatusString(button); } return "unknown toggle"; }
 * 
 * public static String getToggleButtonStatusString(ToggleButton button) { return button.enabled ? "on" : "off"; } }
 */
