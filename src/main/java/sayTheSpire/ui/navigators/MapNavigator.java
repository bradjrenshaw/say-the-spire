package sayTheSpire.ui.navigators;

import sayTheSpire.InfoControls;
import sayTheSpire.Output;
import sayTheSpire.map.VirtualMap;
import sayTheSpire.map.VirtualMapNode;

/**
 * This class is responsible for all handling of viewing the map. Different MapNavigators are used for different
 * methods, such as tree or grid navigation.
 */
public abstract class MapNavigator {

    private VirtualMap map;
    private VirtualMapNode viewingNode;

    public MapNavigator(VirtualMap map) {
        this.map = map;
        this.viewingNode = null;
    }

    public VirtualMap getMap() {
        return this.map;
    }

    public VirtualMapNode getViewingNode() {
        return viewingNode;
    }

    public void setViewingNode(VirtualMapNode node) {
        this.viewingNode = node;
    }

    public void handleFocus(VirtualMapNode node, Boolean isFocus, Boolean shouldAnnounce) {
        if (node == null)
            return;
        this.setViewingNode(node);
        if (shouldAnnounce) {
            Output.text(node.getShort(), false);
        }
    }

    public abstract void control(InfoControls.Direction direction);

}
