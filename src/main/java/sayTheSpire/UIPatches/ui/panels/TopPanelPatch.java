import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import sayTheSpire.ui.elements.PotionElement;
import sayTheSpire.ui.elements.ResourceElement;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

@SpirePatch(clz = TopPanel.class, method = "updatePotions")
public class TopPanelPatch {

    private static Boolean handleIcons(TopPanel __instance) {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null)
            return false;
        if (__instance.goldHb.justHovered) {
            Output.setUI(new ResourceElement(TopPanel.LABEL[4], TopPanel.MSG[4], String.valueOf(player.gold)));
            return true;
        } else if (__instance.hpHb.justHovered) {
            Output.setUI(new ResourceElement(TopPanel.LABEL[3], TopPanel.MSG[3],
                    player.currentHealth + "/" + player.maxHealth));
            return true;
        } else if (__instance.ascensionHb.justHovered) {
            String ascensionString = (String) ReflectionHacks.getPrivate(__instance, TopPanel.class, "ascensionString");
            Output.setUI(new ResourceElement(CharacterSelectScreen.TEXT[8], TextParser.parse(ascensionString),
                    String.valueOf(AbstractDungeon.ascensionLevel)));
        }
        return false;
    }

    private static void handlePotions(TopPanel __instance) {
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            if (p.hb.justHovered) {
                PotionElement potionElement = new PotionElement(p, PotionElement.PotionLocation.MAIN_SCREEN);
                Output.setUI(potionElement);
            }
        }
    }

    public static void Postfix(TopPanel __instance) {
        if (handleIcons(__instance))
            return;
        handlePotions(__instance);
    }
}
