package sayTheSpire.ui.dynamic.elements;

import java.util.ArrayList;
import sayTheSpire.ui.Direction;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.ui.positions.Position;

public class ListContainer extends ElementContainer {

    private ArrayList<DynamicElement> children;
    private Boolean vertical;
    private int focusIndex;
    private Direction prevDirection, nextDirection;

    public ListContainer(String label) {
        this(label, true);
    }

    public ListContainer(String label, Boolean vertical) {
        super("list", label);
        this.children = new ArrayList();
        this.vertical = vertical;
        this.focusIndex = -1;
        this.prevDirection = this.vertical ? Direction.UP : Direction.LEFT;
        this.nextDirection = vertical ? Direction.DOWN : Direction.RIGHT;
    }

    public Boolean add(DynamicElement element) {
        this.children.add(element);
        element.setParent(this);
        return true;
    }

    public void enterFocus(Position position, Direction direction) {
        super.enterFocus(position, direction);
        this.onFocus(true);
        this.focusIndex = -1;
        if (this.children.isEmpty())
            return;
        if (position == null) {
            this.moveFocusIndex(0, this.nextDirection, true);
            return;
        }
        if ((this.vertical && direction == Direction.DOWN) || (!this.vertical && direction == Direction.RIGHT)) {
            this.moveFocusIndex(0, direction, true);
            return;
        } else if ((this.vertical && direction == Direction.UP) || (!this.vertical && direction == Direction.LEFT)) {
            this.moveFocusIndex(this.children.size() - 1, direction, true);
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

        if (testIndex >= 0 && testIndex < this.children.size()) {
            this.moveFocusIndex(testIndex, direction, true);
        }
    }

    public Boolean processDirectionInput(Direction direction) {
        if (direction == this.prevDirection) {
            if (this.moveFocusIndex(this.focusIndex - 1, this.prevDirection, true)) {
                return true;
            }
        } else if (direction == this.nextDirection) {
            if (this.moveFocusIndex(this.focusIndex + 1, this.nextDirection, true)) {
                return true;
            }
        }
        return false;
    }

    public Boolean processInputJustPressed(InputAction action) {
        DynamicElement focus = this.getFocus();
        if (focus != null && focus.processInputJustPressed(action)) {
            return true;
        }
        switch (action.getKey()) {
        case "up":
        case "alt up":
            if (this.processDirectionInput(Direction.UP)) {
                return true;
            }
            break;
        case "down":
        case "alt down":
            if (this.processDirectionInput(Direction.DOWN)) {
                return true;
            }
            break;
        case "left":
        case "alt left":
            if (this.processDirectionInput(Direction.LEFT)) {
                return true;
            }
            break;
        case "right":
        case "alt right":
            if (this.processDirectionInput(Direction.RIGHT)) {
                return true;
            }
            break;
        }
        return false;
    }

    public Boolean remove(DynamicElement element) {
        ListPosition position = (ListPosition) this.getChildPosition(element);
        if (position == null)
            return false;
        int elementIndex = position.getIndex();
        if (this.focusIndex == elementIndex) {
            element.exitFocus();
        }
        this.children.remove(element);
        if (elementIndex > this.focusIndex) {
            return true;
        } else if (elementIndex == this.focusIndex) {
            if (this.focusIndex >= this.children.size()) {
                this.moveFocusIndex(this.children.size() - 1, this.prevDirection, true);
            } else {
                DynamicElement focus = this.getFocus();
                focus.enterFocus(position, this.nextDirection);
            }
        } else {
            this.moveFocusIndex(this.focusIndex - 1, this.prevDirection, false);
            DynamicElement focus = this.getFocus();
            if (focus != null) {
                focus.enterFocus(position, this.prevDirection);
            }
        }
        return true;
    }

    private Boolean moveFocusIndex(int index, Direction direction, Boolean handleFocus) {
        if (index == this.focusIndex)
            return false;
        DynamicElement focus = this.getFocus();
        Position focusPosition = null;
        if (focus != null) {
            if (handleFocus) {
                focus.exitFocus();
            }
            focusPosition = focus.getPosition();
        }
        if (index >= 0 && index < this.children.size()) {
            this.focusIndex = index;
            if (handleFocus) {
                DynamicElement target = this.getFocus();
                target.enterFocus(focusPosition, direction);
            }
            return true;
        }
        return false;
    }

    public Position getChildPosition(DynamicElement element) {
        int index = this.children.indexOf(element);
        if (index < 0)
            return null;
        return new ListPosition(index, this.children.size());
    }

    public DynamicElement getFocus() {
        if (this.children.isEmpty()) {
            return null;
        }
        if (this.focusIndex < 0 || this.focusIndex >= this.children.size())
            return null;
        return this.children.get(this.focusIndex);
    }

}
