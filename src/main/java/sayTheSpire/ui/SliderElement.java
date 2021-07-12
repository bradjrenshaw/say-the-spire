package sayTheSpire.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.Slider;
import java.text.DecimalFormat;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class SliderElement extends UIElement {

  private static final TutorialStrings tutorialStrings =
      CardCrawlGame.languagePack.getTutorialString("Options Tip");

  public static final String[] MSG = tutorialStrings.TEXT;

  public static final String[] LABEL = tutorialStrings.LABEL;

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OptionsPanel");

  public static final String[] TEXT = uiStrings.TEXT;

  private static DecimalFormat df = new DecimalFormat("#");

  private Slider slider;
  private Slider.SliderType type;

  public SliderElement(Slider slider) {
    this.elementType = "slider";
    this.slider = slider;
    this.type = (Slider.SliderType) ReflectionHacks.getPrivate(slider, Slider.class, "type");
  }

  public void onVolumeChange() {
    Output.text(this.getStatusString(), true);
  }

  public String handleBuffers(BufferManager buffers) {
    return null;
  }

  public String getLabel() {
    return this.getText();
  }

  public Slider getSlider() {
    return this.slider;
  }

  public String getText() {
    String volumes[] = TextParser.parse(TEXT[6]).split("\n");
    switch (this.type) {
      case MASTER:
        return volumes[0];
      case BGM:
        return volumes[1];
      case SFX:
        return volumes[2];
      default:
        return "Unknown slider type, report to mod dev.";
    }
  }

  public String getStatusString() {
    return df.format(this.getVolume() * 100.0f) + "%";
  }

  public float getVolume() {
    return (float) ReflectionHacks.getPrivate(this.slider, Slider.class, "volume");
  }
}
