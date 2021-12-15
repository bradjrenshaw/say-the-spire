import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.ObtainRelicGetHook;
import sayTheSpire.events.ObtainTextEvent;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;

public class AbstractRelicPatch {

    private static void obtainRelic(AbstractRelic relic) {
        Output.event(new ObtainTextEvent(relic.name + " obtained"));
    }

    @SpirePatch(clz = AbstractRelic.class, method = "obtain")
    @SpirePatch(clz = AbstractRelic.class, method = "instantObtain", paramtypez = {})
    public static class SingleObtainPatch {

        public static void Postfix(AbstractRelic __instance) {
            obtainRelic(__instance);
        }
    }

    @SpirePatch(clz = AbstractRelic.class, method = "update")
    public static class UpdatePatch {

        public static void Prefix(AbstractRelic __instance) {
            if (__instance.hb.justHovered) {
                AbstractPlayer player = OutputUtils.getPlayer();
                RelicElement.RelicLocation location = RelicElement.RelicLocation.OTHER;
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW)
                    return;
                if (player != null && player.relics.indexOf(__instance) >= 0) {
                    location = RelicElement.RelicLocation.MAIN_SCREEN;
                }
                RelicElement element = new RelicElement(__instance, location);
                Output.setUI(element);
            }
        }
    }
}
