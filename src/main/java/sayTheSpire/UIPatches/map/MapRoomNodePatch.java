import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.Output;
import sayTheSpire.utils.MapUtils;

@SpirePatch(clz = MapRoomNode.class, method = "playNodeHoveredSound")
public class MapRoomNodePatch {

  public static void Prefix(MapRoomNode __instance) {
    Output.text(MapUtils.getMapNodeShort(__instance), true);
    Output.setupBuffers(__instance, false);
  }
}
