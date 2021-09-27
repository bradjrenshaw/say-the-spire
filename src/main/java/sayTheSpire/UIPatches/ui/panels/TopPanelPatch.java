import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import sayTheSpire.Output;

@SpirePatch(clz = TopPanel.class, method = "updatePotions")
public class TopPanelPatch {

    public static void Postfix(TopPanel __instance) {
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            if (p.hb.justHovered) {
                if (p instanceof PotionSlot) {
                    Output.text("Empty potion slot", true);
                } else {
                    Output.text(p.name, true);
                }
                Output.setupBuffers(p);
            }
        }
    }
}
