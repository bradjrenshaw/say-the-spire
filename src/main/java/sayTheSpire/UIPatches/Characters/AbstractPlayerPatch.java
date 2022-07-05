package sayTheSpire.UIPatches.Characters;

import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import sayTheSpire.events.CombatOrbEvent;
import sayTheSpire.events.ObtainEvent;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.Output;

public class AbstractPlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "channelOrb", paramtypez = { AbstractOrb.class })
    public static class ChannelOrbPatch {

        @SpireInsertPatch(locator = Locator.class, localvars = { "orbToSet", "index" })
        public static void Insert(AbstractPlayer __instance, AbstractOrb orbToSet, AbstractOrb localOrb, int index) {
            int slot = __instance.maxOrbs - index;
            Output.event(new CombatOrbEvent(localOrb, slot, CombatOrbEvent.ACTION.CHANNEL));
        }

        private static class Locator extends SpireInsertLocator {

            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractOrb.class, "playChannelSFX");
                return LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), matcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "evokeOrb")
    public static class EvokeOrbPatch {

        public static void Prefix(AbstractPlayer __instance) {
            if (__instance.orbs.isEmpty() || (__instance.orbs.get(0) instanceof EmptyOrbSlot))
                return;
            AbstractOrb orb = __instance.orbs.get(0);
            int slot = __instance.maxOrbs;
            Output.event(new CombatOrbEvent(orb, slot, CombatOrbEvent.ACTION.EVOKE));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "evokeNewestOrb")
    public static class EvokeNewestOrbPatch {

        public static void Prefix(AbstractPlayer __instance) {
            if (__instance.orbs.isEmpty() || (__instance.orbs.get(__instance.orbs.size() - 1) instanceof EmptyOrbSlot))
                return;
            AbstractOrb orb = __instance.orbs.get(__instance.orbs.size() - 1);
            int slot = __instance.maxOrbs - __instance.orbs.size() + 1;
            Output.event(new CombatOrbEvent(orb, slot, CombatOrbEvent.ACTION.EVOKE));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "evokeWithoutLosingOrb")
    public static class EvokeWithoutLosingOrbPatch {

        public static void Prefix(AbstractPlayer __instance) {
            if (__instance.orbs.isEmpty() || (__instance.orbs.get(0) instanceof EmptyOrbSlot))
                return;
            AbstractOrb orb = __instance.orbs.get(0);
            int slot = __instance.maxOrbs;
            Output.event(new CombatOrbEvent(orb, slot, CombatOrbEvent.ACTION.EVOKE));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "gainGold", paramtypez = { int.class })
    public static class GainGoldPatch {

        public static void Prefix(AbstractPlayer __instance, int gold) {
            Output.event(new ObtainEvent(gold));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "obtainPotion", paramtypez = { AbstractPotion.class })
    public static class ObtainPotionPatch {

        public static void Postfix(AbstractPlayer __instance, AbstractPotion potionToObtain) {
            // Avoid triggering the message if the player can't actually pick up the potion
            if (!potionToObtain.isObtained)
                return;
            Output.event(new ObtainEvent(potionToObtain.name));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "update")
    public static class UpdatePatch {

        // A patch here is necessary because of how convoluted the various card interactions are
        public static AbstractCard prevHoveredCard = null;

        public static void Postfix(AbstractPlayer __instance) {
            if (__instance.hb.justHovered) {
                Output.text(__instance.title, true);
                Output.buffers.setCurrentBuffer("player");
            }
            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
                return;
            if (__instance.hoveredCard != prevHoveredCard) {
                if (__instance.hoveredCard != null) {
                    Output.setUI(new CardElement(__instance.hoveredCard, CardElement.CardLocation.HAND));
                }
                prevHoveredCard = __instance.hoveredCard;
            }
        }
    }
}
