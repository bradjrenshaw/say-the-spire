package sayTheSpire.map;

import java.util.HashSet;

public class BaseMapEdge extends VirtualMapEdge {

    private VirtualMapNode start, end;
    private HashSet<String> tags;

    public BaseMapEdge(VirtualMapNode start, VirtualMapNode end) {
        this.start = start;
        this.end = end;
        this.tags = new HashSet();
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public HashSet<String> getTags() {
        return this.tags;
    }

    public VirtualMapNode getEnd() {
        return this.end;
    }

    public Boolean getIsForward() {
        return this.getEnd().getY() > this.getStart().getY();
    }

    public VirtualMapNode getStart() {
        return this.start;
    }

    public VirtualMapEdge invert() {
        return new BaseMapEdge(this.getEnd(), this.getStart());
    }

}
