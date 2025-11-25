package sayTheSpire.ui.navigators;

import com.evacipated.cardcrawl.modthespire.Loader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;
import sayTheSpire.InfoControls;
import sayTheSpire.Output;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.map.BaseMapEdge;
import sayTheSpire.map.VirtualMap;
import sayTheSpire.map.VirtualMapEdge;
import sayTheSpire.map.VirtualMapNode;

public class TreeNavigator extends MapNavigator {
    public static boolean downfall = Loader.isModLoaded("downfall");

    private ArrayList<VirtualMapEdge> pathChoices;
    private Stack<VirtualMapEdge> viewingPath;
    private int pathChoice;
    private LocalizationContext localization;

    public TreeNavigator(VirtualMap map) {
        super(map);
        this.pathChoices = new ArrayList();
        this.viewingPath = new Stack();
        this.pathChoice = -1;
        this.localization = Output.localization.getContext("map.navigators.tree");
    }

    public Boolean changePathChoice(int direction) {
        if (this.pathChoices.isEmpty()) {
            return false;
        }
        int target = this.pathChoice + direction;
        if (target < 0 || target >= this.pathChoices.size())
            return false;
        this.pathChoice = target;
        return true;
    }

    public void setViewingNode(VirtualMapNode node) {
        super.setViewingNode(node);
        this.pathChoices = (ArrayList<VirtualMapEdge>) node.getEdges();
        if (!this.pathChoices.isEmpty()) {
            this.pathChoice = 0;
        }
    }

    private Stack<VirtualMapEdge> followBackwardPath() {
        Stack<VirtualMapEdge> path = new Stack();
        if (this.getViewingNode() == null)
            return path;
        while (true) {
            VirtualMapEdge edge = null;
            if (!this.viewingPath.empty()) {
                edge = this.viewingPath.pop().invert();
            } else {
                edge = this.getMap().getParentEdge(this.getViewingNode());
            }
            if (edge == null)
                return path;
            path.push(edge);
            this.setViewingNode(edge.getEnd());
            if (this.pathChoices.size() != 1)
                break;
        }
        return path;
    }

    private Stack<VirtualMapEdge> followForwardPath() {
        Stack<VirtualMapEdge> path = new Stack();
        if (this.pathChoice < 0 || this.pathChoice >= this.pathChoices.size()) {
            return path;
        }
        while (true) {
            VirtualMapEdge edge = this.pathChoices.get(this.pathChoice);
            this.pathChoice = 0;
            this.setViewingNode(edge.getEnd());
            path.push(edge);
            this.viewingPath.push(edge);
            if (this.pathChoices.size() != 1) {
                break;
            }
        }
        return path;
    }

    private void controlFollowPath(Boolean forward) {
        Stack<VirtualMapEdge> path = null;
        if (forward && !this.pathChoices.isEmpty()) {
            path = this.followForwardPath();
        } else if (!forward) {
            path = this.followBackwardPath();
        } else {
            return;
        }
        if (path.empty()) {
            Output.text(this.localization.localize("no path"), false);
            return;
        }
        StringBuilder pathText = new StringBuilder();
        pathText.append(path.stream().map(e -> e.getShort(false)).collect(Collectors.joining(", ")));
        if (this.pathChoices.size() > 1) {
            pathText.append("\n" + this.localization.localize("choice") + "\n");
            pathText.append(this.pathChoices.get(0).getShort(true));
        }
        Output.text(pathText.toString(), false);
    }

    public void handleFocus(VirtualMapNode node, Boolean isHovered, Boolean shouldAnnounce) {
        if (node == null) {
            Output.text(this.localization.localize("error focus null"), false);
            return;
        }
        VirtualMapNode playerNode = this.getMap().getPlayerNode();
        if (playerNode != null && isHovered) {
            this.viewingPath.clear();
            this.setViewingNode(playerNode);
            BaseMapEdge edge = new BaseMapEdge(playerNode, node);
            this.pathChoice = this.pathChoices.indexOf(edge);
            if (this.pathChoice >= 0 && shouldAnnounce) {
                Output.text(this.pathChoices.get(this.pathChoice).getShort(true), false);
            }
        } else {
            this.setViewingNode(node);
            this.pathChoice = -1;
            if (!this.pathChoices.isEmpty()) {
                this.pathChoice = 0;
            }
            if (shouldAnnounce) {
                StringBuilder sb = new StringBuilder();
                sb.append(node.getShort());
                if (this.pathChoice >= 0) {
                    sb.append("\n" + this.localization.localize("choice") + "\n");
                    sb.append(this.pathChoices.get(this.pathChoice).getShort(true));
                }
                Output.text(sb.toString(), false);
            }
        }
    }

    public void control(InfoControls.Direction direction) {
        switch (direction) {
        case UP:
            this.controlFollowPath(true);
            return;
        case DOWN:
            this.controlFollowPath(false);
            return;
        case LEFT:
            if (this.changePathChoice(-1)) {
                Output.text(this.pathChoices.get(this.pathChoice).getShort(true), false);
            }
            return;
        case RIGHT:
            if (this.changePathChoice(1)) {
                Output.text(this.pathChoices.get(this.pathChoice).getShort(true), false);
            }
            return;
        }
    }
}
