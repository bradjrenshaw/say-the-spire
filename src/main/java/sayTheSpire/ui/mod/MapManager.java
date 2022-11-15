package sayTheSpire.ui.mod;

import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.ui.navigators.*;
import sayTheSpire.map.VirtualMap;
import sayTheSpire.map.VirtualMapNode;
import sayTheSpire.map.BaseMap;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.InfoControls;

public class MapManager {

    private VirtualMap map;
    private MapNavigator navigator;

    public MapManager() {
        this.navigator = null;
        this.map = null;
    }

    public void control(InfoControls.Direction direction) {
        if (this.navigator != null) {
            this.navigator.control(direction);
        }
    }

    // Fix both of these later
    private VirtualMap getMap() {
        if (!OutputUtils.isInDungeon())
            return null;
        return new BaseMap();
    }

    public void handleNode(Object obj, Boolean isHovered, Boolean shouldAnnounce) {
        if (this.map == null || this.navigator == null)
            return;
        VirtualMapNode node = this.map.getNodeFromObject(obj);
        this.navigator.handleFocus(node, isHovered, shouldAnnounce);
    }

    public void updateFirst() {
        VirtualMap newMap = this.getMap();
        if (newMap == null) {
            this.map = null;
            this.navigator = null;
            return;
        }
        if (this.map == null || !newMap.getId().equals(this.map.getId())) {
            this.map = newMap;
            this.navigator = new TreeNavigator(newMap);
        }
    }
}
