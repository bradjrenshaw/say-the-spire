package sayTheSpire.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.Output;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.utils.MapUtils;
import sayTheSpire.utils.OutputUtils;

/**
 * Represents a room node from the base game.
 */
public class BaseRoomNode extends VirtualMapNode {

    private MapRoomNode node;
    private LocalizationContext localization;

    public BaseRoomNode(MapRoomNode node) {
        this(-1, -1);
        this.node = node;
    }

    public BaseRoomNode(int x, int y) {
        super(x, y);
        this.node = null;
        this.localization = Output.localization.getContext("map");
        localization.put("player", OutputUtils.getPlayerName());
    }

    public List<VirtualMapEdge> getEdges() {
        ArrayList<VirtualMapEdge> edges = new ArrayList();
        if (this.getY() >= 0 && this.node == null) {
            return edges;
        }

        VirtualMap map = this.getMap();
        int targetY = this.getY() + 1;
        if (MapUtils.isBossAvailable(this)) {
            BaseBossNode bossNode = new BaseBossNode(-1, targetY, MapUtils.getLocalizedBossName());
            edges.add(new BaseMapEdge(this, bossNode));
            return edges;
        }
        for (int targetX = 0; targetX <= 6; targetX++) {
            BaseRoomNode target = (BaseRoomNode) map.getNodeAt(targetX, targetY);
            if (target == null)
                continue;

            // If this happens the node isn't actually part of the map
            MapRoomNode targetNode = target.getGameNode();
            if (targetNode != null && !targetNode.hasEdges())
                continue;

            Boolean flightConnected = false;
            if (this.getGameNode().wingedIsConnectedTo(targetNode) && !this.getGameNode().isConnectedTo(targetNode)
                    && targetY > 0) {
                flightConnected = true;
            } else if (!this.getGameNode().isConnectedTo(targetNode) && targetY != 0) {
                continue;
            }
            BaseMapEdge edge = new BaseMapEdge(this, target);
            if (flightConnected)
                edge.addTag(this.localization.localize("tags.requires flight"));
            edges.add(edge);
        }
        return edges;
    }

    public Boolean getIsVisited() {
        MapRoomNode gameNode = this.getGameNode();
        if (gameNode == null)
            return false;
        return gameNode.taken;
    }

    public VirtualMap getMap() {
        return new BaseMap();
    }

    public MapRoomNode getGameNode() {
        return this.node;
    }

    public String getRoomTypeKey() {
        MapRoomNode node = this.getGameNode();
        if (node == null)
            return "unknown";
        if (node.room instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) {
            return "elite monster";
        } else if (node.room instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) {
            return "monster";
        } else if (node.room instanceof com.megacrit.cardcrawl.rooms.ShopRoom) {
            return "merchant";
        } else if (node.room instanceof com.megacrit.cardcrawl.rooms.RestRoom) {
            return "rest";
        } else if (node.room instanceof com.megacrit.cardcrawl.rooms.TreasureRoom) {
            return "treasure";
        } else {
            return "unknown";
        }
    }

    public String getLabel() {
        return this.localization.localize("names." + this.getRoomTypeKey());
    }

    public HashSet<String> getTags() {
        HashSet<String> tags = new HashSet();
        MapRoomNode node = this.getGameNode();
        if (node == null)
            return tags;
        MapRoomNode utilNode = MapUtils.getCurrentNode();
        if (utilNode != null && node == utilNode) {
            tags.add(this.localization.localize("tags.player location"));
        }
        if (node.hasEmeraldKey)
            tags.add(this.localization.localize("tags.burning icon"));
        if (Output.mapManager != null) {
            HashSet<String> nodeTags = Output.mapManager.getNodeTags(node);
            if (nodeTags != null)
                tags.addAll(nodeTags);
        }
        return tags;
    }

    public int getX() {
        if (this.node != null)
            return this.node.x;
        return super.getX();
    }

    public int getY() {
        if (this.node != null)
            return this.node.y;
        return super.getY();
    }

    public Boolean hasEdges() {
        MapRoomNode node = this.getGameNode();
        if (node == null)
            return false;
        return MapUtils.isBossAvailable(this) || node.hasEdges();
    }

}
