package sayTheSpire.mapNavigator;

import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FakeBossNode extends FakeMapNode {

  private String bossName;

  public FakeBossNode(int x, int y) {
    this(x, y, "unknown");
  }

  public FakeBossNode(int x, int y, String bossName) {
    super(x, y, AbstractRoom.RoomType.BOSS);
    this.bossName = bossName;
  }

  public String getBossName() {
    return this.bossName;
  }
}
