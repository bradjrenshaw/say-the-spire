import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;
import sayTheSpire.Output;
import sayTheSpire.utils.ActionUtils;

@SpirePatch(clz = ShowMoveNameAction.class, method = "update")
public class ShowMoveNameActionPatch {

    public static void Prefix(ShowMoveNameAction __instance) {
        Output.text(ActionUtils.getShowMoveNameText(__instance), false);
    }
}
