package sayTheSpire.ui.positions;

public class GridPosition extends AbstractPosition {

    public int x, y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getPositionString() {
        return this.x + ", " + this.y;
    }
}
