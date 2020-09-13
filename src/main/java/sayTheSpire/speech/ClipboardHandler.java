package sayTheSpire.speech;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardHandler extends SpeechHandler {

  private Clipboard clipboard;

  public Boolean detect() {
    return true;
  }

  public void disposeResources() {
    if (this.clipboard != null) this.clipboard.setContents(null, null); // Clear
  }

  public Boolean load() {
    return true; // do nothing
  }

  public Boolean loadResources() {
    this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    return this.clipboard != null;
  }

  public Boolean output(String text, Boolean interrupt) {
    StringSelection content = new StringSelection(text);
    this.clipboard.setContents(content, null);
    return true;
  }

  public Boolean silence() {
    return true; // do nothing
  }

  public Boolean speak(String text, Boolean interrupt) {
    return this.output(text, interrupt);
  }

  public void unload() {
    // do nothing
  }
}
