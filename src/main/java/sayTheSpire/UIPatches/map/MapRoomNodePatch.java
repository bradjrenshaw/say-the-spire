import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.map.BaseRoomNode;
import sayTheSpire.Output;

@SpirePatch(clz = MapRoomNode.class, method = "playNodeHoveredSound")
public class MapRoomNodePatch {

    public static void Prefix(MapRoomNode __instance) {
        BaseRoomNode node = new BaseRoomNode(__instance);
        Output.setupBuffers(__instance, true, true);
    }
}
