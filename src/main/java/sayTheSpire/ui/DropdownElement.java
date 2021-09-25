package sayTheSpire.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.utils.OutputUtils;


public class DropdownElement extends UIElement {

  private String options[];
  private String name;
  private DropdownMenu dropdown;
  private int index;

  public DropdownElement(DropdownMenu dropdown, String name, String options[]) {
    this.elementType = "dropdown";
    this.dropdown = dropdown;
    this.index = dropdown.getSelectedIndex();
    this.name = name;
    this.options = options;
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
    if (this.options == null) return "Unknown option";
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
