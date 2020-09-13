import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import sayTheSpire.Output;

@SpirePatch(clz = LwjglApplication.class, method = "exit")
public class GdxAppPatch {

  public static void Prefix(LwjglApplication __instance) {
    Output.shutdown();
  }
}
