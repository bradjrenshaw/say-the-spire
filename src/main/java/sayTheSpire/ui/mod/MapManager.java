package sayTheSpire.ui.mod;

import com.megacrit.cardcrawl.map.MapRoomNode;
import java.util.HashMap;
import java.util.HashSet;
import sayTheSpire.InfoControls;
import sayTheSpire.map.BaseMap;
import sayTheSpire.map.VirtualMap;
import sayTheSpire.map.VirtualMapNode;
import sayTheSpire.ui.navigators.*;
import sayTheSpire.utils.OutputUtils;

public class MapManager {

    private VirtualMap map;
    private MapNavigator navigator;
    private HashMap<MapRoomNode, HashSet<String>> nodeTags;

    public MapManager() {
        this.navigator = null;
        this.map = null;
        this.nodeTags = new HashMap();
    }

    public void control(InfoControls.Direction direction) {
        if (this.navigator != null) {
            this.navigator.control(direction);
        }
    }

    // Fix both of these later. This will allow for custom maps
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
            this.nodeTags.clear();
        }
    }

    public Boolean addNodeTag(MapRoomNode node, String tag) {
        if (this.nodeTags.containsKey(node)) {
            this.nodeTags.get(node).add(tag);
            return true;
        }
        HashSet<String> tags = new HashSet();
        tags.add(tag);
        this.nodeTags.put(node, tags);
        return true;
    }

    public HashSet<String> getNodeTags(MapRoomNode node) {
        return this.nodeTags.getOrDefault(node, null);
    }

    public Boolean moveNodeTag(MapRoomNode source, String tag, MapRoomNode destination, Boolean addIfNotInSource) {
        if (!this.removeNodeTag(source, tag) && !addIfNotInSource)
            return false;
        return this.addNodeTag(destination, tag);
    }

    public Boolean removeNodeTag(MapRoomNode node, String tag) {
        if (!this.nodeTags.containsKey(node))
            return false;
        HashSet<String> tags = this.nodeTags.get(node);
        if (!tags.contains(tag))
            return false;
        tags.remove(tag);
        if (tags.isEmpty())
            this.nodeTags.remove(node);
        return true;
    }
}
