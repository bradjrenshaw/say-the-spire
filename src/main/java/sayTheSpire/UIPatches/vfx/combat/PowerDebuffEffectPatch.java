/*
 * import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect; import
 * com.evacipated.cardcrawl.modthespire.lib.SpirePatch; import sayTheSpire.events.ApplyPowerEvent; import
 * sayTheSpire.Output;
 * 
 * 
 * @SpirePatch(clz = PowerDebuffEffect.class, method=SpirePatch.CONSTRUCTOR, paramtypez={float.class, float.class,
 * String.class}) public class PowerDebuffEffectPatch {
 * 
 * public static void Prefix(PowerDebuffEffect __instance, float x, float y, String msg) { Output.event(new
 * ApplyPowerEvent(msg)); } }
 */
