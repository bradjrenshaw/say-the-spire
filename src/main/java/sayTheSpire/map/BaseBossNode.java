package sayTheSpire.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BaseBossNode extends BaseRoomNode {

    private String bossName;

    public BaseBossNode(int x, int y, String bossName) {
        super(x, y);
        this.bossName = bossName;
    }

    public String getBossName() {
        return this.bossName;
    }

    public List<VirtualMapEdge> getEdges() {
        return new ArrayList<VirtualMapEdge>();
    }

    public String getName() {
        return "boss";
    }

    public HashSet<String> getTags() {
        HashSet<String> tags = super.getTags();
        tags.add(this.getBossName());
        return tags;
    }
}
