import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sayTheSpire.Output;

public class AbstractDungeonPatch {

    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(AbstractDungeon __instance) {
            Output.effects.update();
        }
    }
}
