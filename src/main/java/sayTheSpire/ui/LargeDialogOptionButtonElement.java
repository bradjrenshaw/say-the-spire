package sayTheSpire.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import sayTheSpire.Buffer;
import sayTheSpire.BufferManager;
import sayTheSpire.CardBuffer;
import sayTheSpire.RelicBuffer;
import sayTheSpire.TextParser;

public class LargeDialogOptionButtonElement extends UIElement {

  private AbstractCard previewCard;
  private AbstractRelic previewRelic;
  private LargeDialogOptionButton button;
  private String msg;

  public LargeDialogOptionButtonElement(LargeDialogOptionButton button) {
    this.elementType = "button";
    this.button = button;
    this.msg = TextParser.parse(button.msg);
    this.previewCard =
        (AbstractCard)
            ReflectionHacks.getPrivate(button, LargeDialogOptionButton.class, "cardToPreview");
    this.previewRelic =
        (AbstractRelic)
            ReflectionHacks.getPrivate(button, LargeDialogOptionButton.class, "relicToPreview");
  }

  public String handleBuffers(BufferManager buffers) {
    buffers.setAllEnabled(false);
    Buffer UI = buffers.getBuffer("UI");
    UI.clear();
    UI.add(this.msg);
    UI.add("Accessibility Note: For card and relic previews, see other available buffers.");
    UI.setEnabled(true);
    if (this.previewCard != null) {
      CardBuffer cardBuffer = (CardBuffer) buffers.getBuffer("current card");
      cardBuffer.setObject(this.previewCard);
      cardBuffer.update();
      cardBuffer.setEnabled(true);
      CardBuffer upgradeBuffer = (CardBuffer) buffers.getBuffer("upgrade preview");
      upgradeBuffer.setObject(this.previewCard);
      upgradeBuffer.update();
      upgradeBuffer.setEnabled(true);
    }
    if (this.previewRelic != null) {
      RelicBuffer relicBuffer = (RelicBuffer) buffers.getBuffer("relic");
      relicBuffer.setObject(this.previewRelic);
      relicBuffer.update();
      relicBuffer.setEnabled(true);
    }
    return "UI";
  }

  public String getLabel() {
    return this.msg;
  }
}
