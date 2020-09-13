package sayTheSpire.mapNavigator;

import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/** */
public class FakeMapNode extends MapRoomNode {

  private AbstractRoom.RoomType roomType;

  public FakeMapNode(int x, int y) {
    this(x, y, AbstractRoom.RoomType.MONSTER);
  }

  public FakeMapNode(int x, int y, AbstractRoom.RoomType roomType) {
    super(x, y);
    this.roomType = roomType;
  }

  public AbstractRoom.RoomType getRoomType() {
    return this.roomType;
  }
}
