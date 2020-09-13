/*
package UIPatches;

import com.megacrit.cardcrawl.helpers.TipHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.Output;


@SpirePatch(clz=TipHelper.class, method="renderGenericTip", paramtypez={float.class, float.class, String.class, String.class})
public class TipHelperPatch {
    public static String lastTip = "";

    public static void Postfix(float x, float y, String header, String body) {
        if ((Boolean)ReflectionHacks.getPrivateStatic(TipHelper.class, "renderedTipThisFrame") && !(header + "\n" + body).equals(lastTip)) {
            Output.text(header + "\n" + body, false);
            lastTip = header + "\n" + body;
        }
    }
}
*/
