package sayTheSpire.ui.positions;

public class CategoryListPosition extends ListPosition {

    public CategoryListPosition(int index, int totalCount, String category) {
        super(index, totalCount);
        this.type = "categoryList";
        this.context.put("category", category);
    }

}
