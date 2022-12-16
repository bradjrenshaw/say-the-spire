import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.ui.custom.GremlinMatchCardElement;
import sayTheSpire.ui.positions.GridPosition;

public class GremlinMatchGamePatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Match and Keep!");

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    @SpirePatch(clz = GremlinMatchGame.class, method = SpirePatch.CONSTRUCTOR)
    public static class ConstructorPatch {

        public static void Postfix(GremlinMatchGame __instance) {
            CardGroup cards = (CardGroup) ReflectionHacks.getPrivate(__instance, GremlinMatchGame.class, "cards");
            for (int p = 0; p < 12; p++) {
                AbstractCard card = cards.group.get(p);

                // This screen lays out cards very weirdly relative to their array index
                int x = p % 4 + 1;
                int y = p % 3 + 1;
                UIRegistry.register(card, new GremlinMatchCardElement(card, new GridPosition(x, y)));
            }
        }
    }

    @SpirePatch(clz = GremlinMatchGame.class, method = "updateMatchGameLogic")
    public static class UpdateGameMatchLogicPatch {

        public static int prevAttemptCount = -1;

        public static void Postfix(GremlinMatchGame __instance) {
            CardGroup cards = (CardGroup) ReflectionHacks.getPrivate(__instance, GremlinMatchGame.class, "cards");
            for (AbstractCard card : cards.group) {
                GremlinMatchCardElement element = (GremlinMatchCardElement) UIRegistry.getUI(card);
                if (element != null)
                    element.handleFlip();
            }
            int attemptCount = (int) ReflectionHacks.getPrivate(__instance, GremlinMatchGame.class, "attemptCount");
            if (attemptCount != prevAttemptCount) {
                Output.text(TextParser.parse(OPTIONS[3] + attemptCount), false);
                prevAttemptCount = attemptCount;
            }
            AbstractCard hoveredCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, GremlinMatchGame.class,
                    "hoveredCard");
            if (hoveredCard == null)
                return;
            GremlinMatchCardElement element = (GremlinMatchCardElement) UIRegistry.getUI(hoveredCard);
            if (element == null) {
                Output.text(Output.localization.localize("errors.unknownGremlinMatchCard"), false);
                return;
            }
            if (hoveredCard.hb.justHovered) {
                Output.setUI(element);
            }
        }
    }
}
