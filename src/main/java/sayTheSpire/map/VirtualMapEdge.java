package sayTheSpire.map;

import java.util.HashSet;

public abstract class VirtualMapEdge {

    public abstract Boolean getIsForward();

    public abstract VirtualMapNode getEnd();

    public abstract VirtualMapNode getStart();

    public abstract HashSet<String> getTags();

    public String getShort(Boolean includeCoordinates) {
        // This needs to be localized later, but I want to get the refactor working first
        VirtualMapNode node = this.getEnd();
        if (node == null)
            return "Error on map edge, report to mod developer";
        StringBuilder sb = new StringBuilder();
        sb.append(node.getLabel());

        if (includeCoordinates) {
            sb.append(", " + node.getX() + ", " + node.getY());
        }

        HashSet<String> tags = this.getTags();
        tags.addAll(node.getTags());

        if (!tags.isEmpty()) {
            sb.append(" (" + String.join(", ", tags) + ")");
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        VirtualMapEdge e = (VirtualMapEdge) o;
        return this.getStart().equals(e.getStart()) && this.getEnd().equals(e.getEnd());
    }

    public abstract VirtualMapEdge invert();

}
