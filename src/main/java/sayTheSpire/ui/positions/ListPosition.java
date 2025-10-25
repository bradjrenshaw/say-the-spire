package sayTheSpire.ui.positions;

public class ListPosition extends Position {

    protected int index, totalCount;

    public ListPosition(int index, int totalCount) {
        super("list");
        this.index = index;
        this.totalCount = totalCount;
        this.context.put("position", index + 1);
        this.context.put("total", totalCount);
    }

}
