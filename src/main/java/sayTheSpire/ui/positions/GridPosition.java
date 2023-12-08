package sayTheSpire.ui.positions;

public class GridPosition extends Position {

    public int x, y;

    public GridPosition(int x, int y) {
        super("grid");
        this.x = x;
        this.y = y;
        this.context.put("x", x);
        this.context.put("y", y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
