package sayTheSpire.ui.positions;

/**
 * This is for a position that doesn't necessarily follow a coordinate system, for example "top left"
 */
public class DescriptivePosition extends AbstractPosition {

    private String description;

    public DescriptivePosition(String description) {
        this.description = description;
    }

    public String getPositionString() {
        return this.description;
    }
}
