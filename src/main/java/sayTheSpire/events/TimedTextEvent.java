package sayTheSpire.events;

import com.badlogic.gdx.Gdx;

public class TimedTextEvent extends Event {

  private String text;
  private double timer;

  public TimedTextEvent(String text, double timer) {
    this.text = text;
    this.timer = timer;
  }

  public String getText() {
    return this.text;
  }

  public Boolean isComplete() {
    return this.timer <= 0.0;
  }

  public void update() {
    if (this.timer <= 0) return;
    this.timer -= Gdx.graphics.getDeltaTime();
  }
}
