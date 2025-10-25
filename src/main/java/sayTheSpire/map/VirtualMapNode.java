package sayTheSpire.map;

import java.util.HashSet;
import java.util.List;

/**
 * Represents a node on the map. This could be a room from the base game or possibly another type (for example a node in
 * the run history or from another mod).
 */
public abstract class VirtualMapNode {

    private final int x, y;

    public VirtualMapNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<VirtualMapEdge> getEdges() {
        return null;
    }

    public Boolean getIsVisited() {
        return false;
    }

    public abstract String getName();

    public String getLabel() {
        return this.getName();
    }

    public HashSet<String> getTags() {
        return new HashSet<String>();
    }

    public String getShort() {
        // This needs to be localized but I want to get the refactor working first
        HashSet<String> tags = this.getTags();
        StringBuilder sb = new StringBuilder();
        sb.append(this.getLabel());
        sb.append(", " + this.getX() + ", " + this.getY());
        if (!tags.isEmpty()) {
            sb.append(" (" + String.join(", ", tags) + ")");
        }
        return sb.toString();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract Boolean hasEdges();

    public Boolean isConnectedTo(VirtualMapNode destination) {
        for (VirtualMapEdge edge : this.getEdges()) {
            if (this.equals(edge.getStart()) && destination.equals(edge.getEnd()))
                return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        // Note that this implementation assumes that a node is uniquely identified by its name and current map position
        // (x, y).
        if (o == null)
            return false;
        if (this == o)
            return true;
        if (this.getClass() != o.getClass())
            return false;
        VirtualMapNode n = (VirtualMapNode) o;
        return this.getName().equals(n.getName()) && this.getX() == n.getX() && this.getY() == n.getY();
    }
}
