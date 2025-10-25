package sayTheSpire;

import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.ui.elements.UIElement;

public class SayTheSpire {

    /**
     * Sends text to the current speech handler (aka speaks/brailles/etc text).
     * 
     * @param text
     *            The raw text to be spoken
     * @param interrupt
     *            Whether or not any currently speaking text should be interrupted with this text (it is usually better
     *            not to interrupt)
     */
    public void output(String text, Boolean interrupt) {
        Output.text(text, interrupt);
    }

    /**
     * Sets the current UI Element. Note that this is a sayTheSpire.ui.elements.UIElement object, not a base game
     * object. The UIElement holds information to be spoken that is handled automatically, such as label, additional
     * tags, the element type (such as button), etc. The UIElements also can have alternative functionality, such as
     * announcing when a toggle button becomes checked or unchecked. Use this when you want the screenreader to receive
     * information about a focused element, (such as when a button is hovered),
     * 
     * @param element
     *            The UIElement currently focused.
     */
    public void setUI(UIElement element) {
        Output.setUI(element);
    }

    /**
     * Adds a tag to a MapRoomNode instance (only works if you aren't using a custom map). These are for extra
     * information spoken for each node, for example player location or some other special feature visible on the map.
     * 
     * @param node
     *            The MapRoomNode instance to attach a tag to
     * @param tag
     *            The tag to be added. Will not be automatically localized by Say the Spire; you will need to localize
     *            this yourself.
     * 
     * @return True if added, false otherwise
     */
    public Boolean addNodeTag(MapRoomNode node, String tag) {
        if (Output.mapManager == null)
            return false;
        return Output.mapManager.addNodeTag(node, tag);
    }

    /**
     * Moves a tag from one node to another.
     * 
     * @param source
     *            The source node
     * @param tag
     *            The tag to move
     * @param destination
     *            The node to move the tag to
     * @param addIfNotPresent
     *            If true, the tag will be added to the destination node even if it was not present in source
     * 
     * @return True if successfully moved, false otherwise
     */
    public Boolean moveNodeTag(MapRoomNode source, String tag, MapRoomNode destination, Boolean addIfNotPresent) {
        if (Output.mapManager == null)
            return false;
        return Output.mapManager.moveNodeTag(source, tag, destination, addIfNotPresent);
    }

    /**
     * Removes a tag from a given node.
     * 
     * @param node
     *            The node to remove the tag from
     * @param tag
     *            The tag to be removed
     * 
     * @return True if successfully removed, false otherwise
     */
    public Boolean removeNodeTag(MapRoomNode node, String tag) {
        if (Output.mapManager == null)
            return false;
        return Output.mapManager.removeNodeTag(node, tag);
    }
}
