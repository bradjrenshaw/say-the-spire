package sayTheSpire.events;

public class DialogueEvent extends TextEvent {

  String source;
  String type;

  public DialogueEvent(String type, String source, String message) {
    super(message);
    this.source = source;
    this.type = type;
  }

  public String getText() {
    return this.source + " " + this.type + ": " + this.message;
  }

  public Boolean isComplete() {
    return this.source != null && this.message != null;
  }
}
