package sayTheSpire.ui.dynamic.elements;

import java.util.ArrayList;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.Direction;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.positions.ListPosition;

public class ListContainer extends ElementContainer {

    private ArrayList<DynamicElement> children;
    private Boolean vertical;
    private int focusIndex;
    private Direction prevDirection, nextDirection;

    public ListContainer(ElementContainer parent, String label) {
        this(parent, label, true);
    }

    public ListContainer(ElementContainer parent, String label, Boolean vertical) {
        super(parent, "list", label);
        this.children = new ArrayList();
        this.vertical = vertical;
        this.focusIndex = -1;
        this.prevDirection = this.vertical ? Direction.UP : Direction.LEFT;
        this.nextDirection = vertical ? Direction.DOWN : Direction.RIGHT;
    }

    public Boolean add(DynamicElement element) {
        this.children.add(element);
        return true;
    }

    public void enterFocus(Position position, Direction direction) {
        super.enterFocus(position, direction);
        this.onFocus(true);
        this.focusIndex = -1;
        if (this.children.isEmpty())
            return;
        if (position == null) {
            this.moveFocusIndex(0, true);
            return;
        }
        if ((this.vertical && direction == Direction.DOWN) || (!this.vertical && direction == Direction.RIGHT)) {
            this.moveFocusIndex(0, true);
            return;
        } else if ((this.vertical && direction == Direction.UP) && (!this.vertical && direction == Direction.LEFT)) {
            this.moveFocusIndex(this.children.size() - 1, true);
            return;
        }
        int testIndex = -1;
        if (position instanceof GridPosition) {
            GridPosition grid = (GridPosition) position;
            if (direction == Direction.UP || direction == Direction.DOWN) {
                testIndex = grid.getX();
            } else if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                testIndex = grid.getY();
            }
        } else if (position instanceof ListPosition) {
            ListPosition list = (ListPosition) position;
            testIndex = list.getIndex();
        }

        if (testIndex > 0 && testIndex < this.children.size()) {
            this.moveFocusIndex(testIndex, true);
        }
    }

    public Boolean processDirectionInput(Direction direction) {
        DynamicElement focus = this.getFocus();
        if (focus == null)
            return false;
        if (direction == this.prevDirection && this.focusIndex > 0) {
            focus.exitFocus();
            this.move((ListPosition) focus.getPosition(), this.prevDirection);
            return true;
        } else if (direction == this.nextDirection && this.focusIndex < this.children.size() - 1) {
            focus.exitFocus();
            this.move((ListPosition) focus.getPosition(), this.nextDirection);
            return true;
        }
        return false;
    }

    public Boolean processInputJustPressed(InputAction action) {
        if (super.processInputJustPressed(action))
            return true;
        switch (action.getName()) {
        case "up":
        case "alt up":
            return this.processDirectionInput(Direction.UP);
        case "down":
        case "alt down":
            return this.processDirectionInput(Direction.DOWN);
        case "left":
        case "alt left":
            return this.processDirectionInput(Direction.LEFT);
        case "right":
        case "alt right":
            return this.processDirectionInput(Direction.RIGHT);
        }
        return false;
    }

    public Boolean remove(DynamicElement element) {
        ListPosition position = (ListPosition) this.getChildPosition(element);
        if (position == null)
            return false;
        element.exitFocus();
        int elementIndex = position.getIndex();
        this.children.remove(element);
        if (elementIndex >= this.focusIndex) {
            this.move(position, this.prevDirection);
        }
        return true;
    }

    private void moveFocusIndex(int index, Boolean shouldFocus) {
        this.focusIndex = index;
        this.getFocus().enterFocus(null, this.nextDirection);
    }

    private void move(ListPosition position, Direction direction) {
        if (direction == this.prevDirection) {
            this.focusIndex--;
        } else if (direction == this.nextDirection) {
            this.focusIndex++;
        }
        if (this.focusIndex >= 0 && this.focusIndex < this.children.size()) {
            this.getFocus().enterFocus(position, direction);
        }
    }

    public Position getChildPosition(DynamicElement element) {
        int index = this.children.indexOf(element);
        if (index < 0)
            return null;
        return new ListPosition(index, this.children.size());
    }

    public DynamicElement getFocus() {
        return this.children.get(this.focusIndex);
    }

}
