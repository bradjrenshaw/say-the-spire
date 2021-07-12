package sayTheSpire.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.utils.OutputUtils;

public class DropdownElement extends UIElement {

  private static final TutorialStrings tutorialStrings =
      CardCrawlGame.languagePack.getTutorialString("Options Tip");

  public static final String[] MSG = tutorialStrings.TEXT;

  public static final String[] LABEL = tutorialStrings.LABEL;

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OptionsPanel");

  public static final String[] TEXT = uiStrings.TEXT;

  private static String graphicsOptions[] = TextParser.parse(TEXT[3]).split("\n");

  private String options[];
  private String name;
  private DropdownMenu dropdown;
  private int index;

  public DropdownElement(DropdownMenu dropdown) {
    this.elementType = "dropdown";
    this.dropdown = dropdown;
    this.index = dropdown.getSelectedIndex();
    initialize();
  }

  public void initialize() {
    OptionsPanel panel = null;
    if (OutputUtils.isInDungeon()) {
      panel = AbstractDungeon.settingsScreen.panel;
    } else if (CardCrawlGame.mainMenuScreen.isSettingsUp) {
      panel = CardCrawlGame.mainMenuScreen.optionPanel;
    }
    DropdownMenu fps = panel.fpsDropdown;
    DropdownMenu reso = panel.resoDropdown;
    DropdownMenu language =
        (DropdownMenu) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "languageDropdown");
    if (fps == null || reso == null || language == null) {
      return;
    }
    if (this.dropdown == fps) {
      this.name = TextParser.parse(graphicsOptions[1]);
      this.options =
          (String[]) ReflectionHacks.getPrivate(panel, OptionsPanel.class, "FRAMERATE_LABELS");
      if (this.options == null) {
      }
    } else if (this.dropdown == reso) {
      this.name = TextParser.parse(graphicsOptions[0]);
      this.options = new String[Settings.displayOptions.size()];
      for (int i = 0; i < Settings.displayOptions.size(); i++) {
        this.options[i] = TextParser.parse(Settings.displayOptions.get(i).uiString());
      }
    } else if (this.dropdown == language) {
      this.name = TextParser.parse(TEXT[13]);
      this.options = panel.languageLabels();
    }
  }

  public DropdownMenu getDropdownMenu() {
    return this.dropdown;
  }

  public int getIndex() {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getOption(int index) {
    if (this.options == null) return "options is null, great";
    return this.options[index];
  }

  public String getLabel() {
    return this.name;
  }

  public int getOptionCount() {
    return this.options.length;
  }

  public String getStatusString() {
    return this.getOption(this.index);
  }

  public String handleBuffers(BufferManager buffers) {
    Output.setupUIBufferMany(this.name);
    return null;
  }
}
