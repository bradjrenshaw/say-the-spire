/*
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import sayTheSpire.events.ApplyPowerEvent;
import sayTheSpire.Output;

@SpirePatch(clz=ApplyPowerAction.class, method=SpirePatch.CONSTRUCTOR, paramtypez={AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class})
public class ApplyPowerActionPatch {



  public static void Prefix(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
      Output.event(new ApplyPowerEvent(source, target, powerToApply, null));
}
}
*/
