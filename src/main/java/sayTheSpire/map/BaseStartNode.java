package sayTheSpire.map;

import java.util.List;
import java.util.ArrayList;
import com.megacrit.cardcrawl.map.MapRoomNode;

public class BaseStartNode extends BaseRoomNode {

    public BaseStartNode() {
        super(0, -1);
    }

    public List<VirtualMapEdge> getEdges() {
        ArrayList<VirtualMapEdge> edges = new ArrayList();
        VirtualMap map = this.getMap();
        for (int x = 0; x <= 6; x++) {
            VirtualMapNode target = map.getNodeAt(x, 0);
            if (target == null)
                continue;
            if (target.hasEdges())
                edges.add(new BaseMapEdge(this, target));
        }
        return edges;
    }

    public Boolean getIsVisited() {
        return true;
    }

    public String getName() {
        return "start location";
    }
}
