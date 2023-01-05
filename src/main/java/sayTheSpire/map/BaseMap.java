package sayTheSpire.map;

import java.util.ArrayList;
import java.util.List;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.utils.MapUtils;
import sayTheSpire.Output;

public class BaseMap extends VirtualMap {

    // Each id should be unique to the map
    private String id;

    public BaseMap() {
        this.id = "base." + AbstractDungeon.id;
    }

    public String getId() {
        return this.id;
    }

    private static ArrayList<ArrayList<MapRoomNode>> getDungeonMap() {
        return CardCrawlGame.dungeon.getMap();
    }

    public VirtualMapNode getNodeFromObject(Object obj) {
        if (obj == null)
            return null;
        if (!(obj instanceof MapRoomNode))
            return null;
        return new BaseRoomNode((MapRoomNode) obj);
    }

    public VirtualMapEdge getParentEdge(VirtualMapNode node) {
        if (node == null)
            return null;
        for (int y = node.getY(); y >= -1; y--) {
            for (int x = 0; x <= 6; x++) {
                VirtualMapNode source = this.getNodeAt(x, y);
                if (source == null)
                    continue;
                if ((source.getIsVisited() && source.isConnectedTo(node)) || (source instanceof BaseStartNode))
                    return new BaseMapEdge(node, source);
            }
        }
        return null;
    }

    public VirtualMapNode getNodeAt(int x, int y) {
        // There is a chance the map just isn't actually loaded yet
        ArrayList<ArrayList<MapRoomNode>> map = getDungeonMap();
        if (map == null)
            return null;

        // This node isn't on the map itself but you start below the map
        if (y == -1)
            return new BaseStartNode();

        if (y >= map.size())
            return null;

        for (MapRoomNode node : map.get(y)) {
            // If the node does not have edges it should not be on the map
            if (node.x == x && node.y == y && node.hasEdges()) {
                return new BaseRoomNode(node);
            }
        }
        return null;
    }

    public VirtualMapNode getPlayerNode() {
        MapRoomNode node = MapUtils.getCurrentNode();
        if (node == null || node.x < 0 || node.x > 6) {
            return new BaseStartNode();
        }
        return this.getNodeAt(node.x, node.y);
    }

}
