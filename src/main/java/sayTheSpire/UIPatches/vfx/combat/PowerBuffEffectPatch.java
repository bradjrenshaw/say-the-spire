/*
 * import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect; import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
 * import sayTheSpire.events.ApplyPowerEvent; import sayTheSpire.Output;
 * 
 * 
 * @SpirePatch(clz = PowerBuffEffect.class, method=SpirePatch.CONSTRUCTOR, paramtypez={float.class, float.class,
 * String.class}) public class PowerBuffEffectPatch {
 * 
 * public static void Prefix(PowerBuffEffect __instance, float x, float y, String msg) { Output.event(new
 * ApplyPowerEvent(msg)); } }
 */
