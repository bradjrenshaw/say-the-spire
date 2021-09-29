import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sayTheSpire.Output;
import sayTheSpire.ui.CardElement;

@SpirePatch(clz = AbstractCard.class, method = "update")
public class AbstractCardPatch {

    public static void Prefix(AbstractCard __instance) {
        CardElement.CardLocation location = CardElement.CardLocation.OTHER;
        if (AbstractDungeon.screen != null) {
            switch (AbstractDungeon.screen) {
            case MASTER_DECK_VIEW:
                location = CardElement.CardLocation.MASTER_DECK_VIEW;
                break;
            case DISCARD_VIEW:
            case EXHAUST_VIEW:
            case GAME_DECK_VIEW:
            case BOSS_REWARD:
            case TRANSFORM:
                break;
            default:
                return; // using card hover logic in other screens breaks things horribly because
            // AbstractCard.hb.justHovered can be set repeatedly for some reason and
            // AbstractCard.hb.hovered follows some strange alien logic I'm unfamiliar with
            }
        }
        if (__instance.hb.justHovered) {
            Output.setUI(new CardElement(__instance, location));
        }
    }
}
