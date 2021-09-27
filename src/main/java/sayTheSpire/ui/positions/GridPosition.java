package sayTheSpire.ui.positions;

public class GridPosition extends AbstractPosition {

    protected int x, y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getPositionString() {
        return this.x + ", " + this.y;
    }
}
