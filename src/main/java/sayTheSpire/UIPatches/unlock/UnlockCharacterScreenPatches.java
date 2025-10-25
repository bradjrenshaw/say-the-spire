import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockCharacterScreen;
import sayTheSpire.Output;

public class UnlockCharacterScreenPatches {

    @SpirePatch(clz = UnlockCharacterScreen.class, method = "open", paramtypez = { AbstractUnlock.class })
    public static class OpenPatch {

        public static void Postfix(UnlockCharacterScreen __instance, AbstractUnlock unlock) {
            Output.text(unlock.player.title, false);
        }
    }
}
