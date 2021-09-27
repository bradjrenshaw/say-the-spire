package sayTheSpire.mapNavigator;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.utils.MapUtils;
import sayTheSpire.utils.OutputUtils;

public class MapNavigator {

    private ArrayList<MapRoomNode> pathChoices;
    private ArrayList<MapRoomNode> viewingPath;
    private int pathChoice;
    private MapRoomNode viewingNode;

    public MapNavigator() {
        this.pathChoices = new ArrayList();
        this.viewingPath = new ArrayList();
        this.pathChoice = -1;
        this.viewingNode = null;
    }

    public Boolean changePathChoice(int direction) {
        if (this.pathChoices.isEmpty())
            return false;
        int target = this.pathChoice + direction;
        if (target < 0 || target >= this.pathChoices.size())
            return false;
        this.setPathChoice(target);
        return true;
    }

    public ArrayList<MapRoomNode> followLinearPath(Boolean forward) {
        ArrayList<MapRoomNode> nodes = new ArrayList();
        if (this.viewingNode == null)
            return nodes;
        if (forward) {
            if (this.pathChoices.isEmpty())
                return nodes;
            this.viewingPath.add(this.viewingNode);
            this.setViewingNode(this.pathChoices.get(this.pathChoice));
        }
        Boolean backwardFirst = true;
        do {
            nodes.add(this.viewingNode);
            if (!backwardFirst && this.pathChoices.size() != 1) {
                break;
            }
            backwardFirst = false;
        } while (this.moveToNextSegmentNode(forward));
        return nodes;
    }

    public Boolean moveToNextSegmentNode(Boolean forward) {
        MapRoomNode nextNode = null;
        if (this.viewingNode == null)
            return false;
        if (forward) {
            ArrayList<MapRoomNode> choices = getPathChoices(this.viewingNode);
            if (choices.size() != 1)
                return false;
            this.viewingPath.add(this.viewingNode);
            nextNode = choices.get(0);
        } else {
            MapRoomNode parent = this.popViewingPath();
            if (parent == null) {
                parent = getParent(viewingNode);
            }
            nextNode = parent;
        }
        if (nextNode == null)
            return false;
        this.setViewingNode(nextNode);
        return true;
    }

    public MapRoomNode getParent(MapRoomNode node) {
        if (node.y == 0) {
            return new MapRoomNode(-1, -1);
        } else if (node.y < 0) {
            return null;
        }
        // required since it is possible to skip rows with some events
        for (MapRoomNode p : AbstractDungeon.map.get(node.y - 1)) {
            if (viewingPath.contains(p) || p.taken) {
                return p;
            }
        }
        return null;
    }

    public MapRoomNode getPathChoice() {
        if (this.pathChoice < 0 || this.pathChoice >= this.pathChoices.size())
            return null;
        return this.pathChoices.get(this.pathChoice);
    }

    public void setPathChoice(int choice) {
        if (choice < 0 || choice >= this.pathChoices.size()) {
            this.pathChoice = -1;
        }
        this.pathChoice = choice;
    }

    public ArrayList<MapRoomNode> getPathChoices() {
        return this.getPathChoices(this.viewingNode);
    }

    public ArrayList<MapRoomNode> getPathChoices(MapRoomNode node) {
        ArrayList<MapRoomNode> nodes = new ArrayList();
        ArrayList<ArrayList<MapRoomNode>> map = CardCrawlGame.dungeon.getMap();
        int targetRow = node.y + 1;

        if (node == null) {
            return nodes;
        } else if (MapUtils.isBossAvailable(node)) {
            nodes.add(new FakeBossNode(-1, node.y + 1, MapUtils.getLocalizedBossName()));
            return nodes;
        } else if (node.y >= map.size() && node.x == -1 && !(node instanceof FakeBossNode)) {
            targetRow = 0;
        } else if (node.y >= map.size()) {
            return nodes;
        }
        for (MapRoomNode r : map.get(targetRow)) {
            if (!r.hasEdges())
                continue; // this can happen, if so the nodes aren't part of the intended map
            if ((targetRow == 0 && r.y == 0) || (OutputUtils.playerIsFlying() && node.wingedIsConnectedTo(r))
                    || node.isConnectedTo(r)) {
                nodes.add(r);
            }
        }
        return nodes;
    }

    public ArrayList<MapRoomNode> getStartingPathChoices() {
        ArrayList<MapRoomNode> nodes = new ArrayList();
        for (MapRoomNode node : AbstractDungeon.map.get(0)) {
            if (node.hasEdges()) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    public MapRoomNode getViewingNode() {
        return this.viewingNode;
    }

    public void setViewingNode(MapRoomNode node) {
        if (this.viewingNode == node)
            return;
        this.viewingNode = node;
        this.pathChoices = this.getPathChoices(node);
        this.setPathChoice(0);
    }

    public void handleFocusedNode(MapRoomNode node) {
        this.viewingPath.clear();
        pathChoice = -1;
        MapRoomNode current = MapUtils.getCurrentNode();
        this.pathChoices = getPathChoices(current);
        if (!pathChoices.isEmpty()) {
            this.pathChoice = pathChoices.indexOf(node);
        }
        this.viewingNode = current;
        if (this.pathChoice < 0) {
            Output.text("The map viewer has broken (choice < 0), report to dev.", false);
        }
    }

    public void handleViewingNode(MapRoomNode node, Boolean viewing) {
        this.pathChoices = getPathChoices(node);
        this.viewingNode = node;
        if (!this.pathChoices.isEmpty()) {
            this.pathChoice = 0;
            if (viewing && this.pathChoices.size() > 1) {
                Output.text("choice", false);
            }
            Output.text(MapUtils.getMapNodeShort(pathChoices.get(pathChoice)), true);
        } else {
            Output.text("No paths available.", true);
        }
    }

    public MapRoomNode popViewingPath() {
        if (viewingPath.isEmpty())
            return null;
        MapRoomNode node = viewingPath.get(viewingPath.size() - 1);
        viewingPath.remove(node);
        return node;
    }
}
