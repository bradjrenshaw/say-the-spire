import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import sayTheSpire.Output;
import sayTheSpire.events.ObtainEvent;
import sayTheSpire.events.RelicFlashEvent;
import sayTheSpire.ui.elements.RelicElement;
import sayTheSpire.utils.OutputUtils;

public class AbstractRelicPatch {

    private static void obtainRelic(AbstractRelic relic) {
        Output.event(new ObtainEvent(relic.name));
    }

    @SpirePatch(clz = AbstractRelic.class, method = "flash")
    public static class FlashPatch {

        public static void Postfix(AbstractRelic __instance) {
            Output.event(new RelicFlashEvent(__instance));
        }
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
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW
                        || CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RUN_HISTORY)
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
