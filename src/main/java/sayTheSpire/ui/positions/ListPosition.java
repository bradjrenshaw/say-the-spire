package sayTheSpire.ui.positions;

public class ListPosition extends AbstractPosition {

    protected int index, totalCount;

    public ListPosition(int index, int totalCount) {
        this.index = index;
        this.totalCount = totalCount;
    }

    public String getPositionString() {
        return (this.index + 1) + " of " + this.totalCount;
    }
}
