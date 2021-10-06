package sayTheSpire.ui.positions;

public class CategoryListPosition extends ListPosition {

    private String category;

    public CategoryListPosition(int index, int totalCount, String category) {
        super(index, totalCount);
        this.category = category;
    }

    public String getPositionString() {
        return super.getPositionString() + " " + this.category;
    }
}
