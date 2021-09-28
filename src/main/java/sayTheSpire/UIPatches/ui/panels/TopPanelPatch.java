import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import sayTheSpire.ui.PotionElement;
import sayTheSpire.Output;

@SpirePatch(clz = TopPanel.class, method = "updatePotions")
public class TopPanelPatch {

    public static void Postfix(TopPanel __instance) {
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            if (p.hb.justHovered) {
                PotionElement potionElement = new PotionElement(p, PotionElement.PotionLocation.MAIN_SCREEN);
                Output.setUI(potionElement);
            }
        }
    }
}
