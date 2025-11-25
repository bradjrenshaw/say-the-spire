import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.map.MapRoomNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sayTheSpire.map.BaseRoomNode;
import sayTheSpire.Output;

@SpirePatch(clz = MapRoomNode.class, method = "playNodeHoveredSound")
public class MapRoomNodePatch {
    public static final Logger logger = LogManager.getLogger(MapRoomNodePatch.class.getName());

    public static void Prefix(MapRoomNode __instance) {
        BaseRoomNode node = new BaseRoomNode(__instance);
        logger.info("map room node patch play node hovered sound");
        Output.setupBuffers(__instance, true, true);
    }
}
