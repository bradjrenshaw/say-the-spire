package sayTheSpire.map;

import java.util.List;

public abstract class VirtualMap {

    public abstract String getId();

    public abstract VirtualMapNode getNodeAt(int x, int y);

    public VirtualMapNode getNodeFromObject(Object obj) {
        throw new UnsupportedOperationException("getNodeFromObject not implemented for object of this type.");
    }

    public abstract VirtualMapEdge getParentEdge(VirtualMapNode node);

    public abstract VirtualMapNode getPlayerNode();
}
