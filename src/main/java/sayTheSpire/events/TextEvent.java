package sayTheSpire.events;

public class TextEvent extends Event {

  String message;

  public TextEvent(String message) {
    super();
    this.message = message;
  }

  public String getText() {
    return this.message;
  }

  public Boolean isComplete() {
    return this.message != null && !this.message.equals("");
  }

}
