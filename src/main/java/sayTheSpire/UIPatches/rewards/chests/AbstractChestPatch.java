import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rewards.chests.LargeChest;
import com.megacrit.cardcrawl.rewards.chests.MediumChest;
import com.megacrit.cardcrawl.rewards.chests.SmallChest;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.Output;

public class AbstractChestPatch {

    @SpirePatch(clz = AbstractChest.class, method = "update")
    public static class UpdatePatch {

        public static void patchBossChest(BossChest chest) {
            int relicCount = chest.relics.size();
            for (int index = 0; index < relicCount; index++) {
                AbstractRelic relic = chest.relics.get(index);
                if (relic.hb.justHovered) {
                    RelicElement element = new RelicElement(relic, RelicElement.RelicLocation.BOSS_REWARDS,
                            new ListPosition(index, relicCount));
                    Output.setUI(element);
                    return;
                }
            }
        }

        public static void Postfix(AbstractChest __instance) {
            Hitbox hb = (Hitbox) ReflectionHacks.getPrivate(__instance, AbstractChest.class, "hb");
            if (hb.justHovered) {
                String text = "";
                if (__instance instanceof SmallChest)
                    text = "small";
                else if (__instance instanceof MediumChest)
                    text = "medium";
                else if (__instance instanceof LargeChest)
                    text = "large";
                else if (__instance instanceof BossChest)
                    text = "boss";
                text += " treasure chest";
                Output.text(text, true);
                if (__instance instanceof BossChest)
                    patchBossChest((BossChest) __instance);
            }
        }
    }
}
