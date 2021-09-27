package sayTheSpire;

import com.megacrit.cardcrawl.map.MapRoomNode;
import java.util.ArrayList;
import sayTheSpire.mapNavigator.MapNavigator;
import sayTheSpire.utils.MapUtils;

public class MapControls {

    public static MapNavigator navigator = null;

    public static void changePathChoice(int direction) {
        if (navigator == null) {
            Output.text("Map navigator object not created, report to developer.", true);
            return;
        }
        if (!navigator.changePathChoice(direction)) {
            return;
        }
        reportCurrentChoice();
    }

    public static void followPath(Boolean forward) {
        ArrayList<MapRoomNode> path = navigator.followLinearPath(forward);
        if (path.isEmpty()) {
            Output.text("No path available.", true);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (MapRoomNode r : path) {
            sb.append(MapUtils.getRoomTypeString(r) + MapUtils.getNodeExtra(r) + ", ");
        }

        if (forward || Output.config.getBoolean("map.read_reversed_paths"))
            Output.text(sb.toString(), false);
        if (navigator.getPathChoices().size() > 1) {
            Output.text("choice", false);
        }
        reportCurrentChoice();
    }

    public static void reportCurrentChoice() {
        if (navigator == null)
            return;
        MapRoomNode choice = navigator.getPathChoice();
        if (choice == null)
            return;
        Output.text(MapUtils.getMapNodeShort(choice), true);
    }
}
