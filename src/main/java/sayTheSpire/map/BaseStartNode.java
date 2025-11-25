package sayTheSpire.map;

import java.util.List;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.map.MapRoomNode;
import downfall.patches.EvilModeCharacterSelect;

public class BaseStartNode extends BaseRoomNode {
    public static boolean downfall = Loader.isModLoaded("downfall");

    public BaseStartNode() {
        super(0, -1);
    }

    @Override
    public List<VirtualMapEdge> getEdges() {
        ArrayList<VirtualMapEdge> edges = new ArrayList();
        VirtualMap map = this.getMap();
        for (int x = 0; x <= 6; x++) {
            VirtualMapNode target = map.getNodeAt(x, downfall && EvilModeCharacterSelect.evilMode ? 14 : 0);
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

    public String getRoomTypeKey() {
        return "start location";
    }
}
