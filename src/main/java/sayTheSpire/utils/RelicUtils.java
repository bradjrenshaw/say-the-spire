package sayTheSpire.utils;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class RelicUtils {

    public static String getRelicRarityString(AbstractRelic relic) {
        if (relic.tier == null)
            return null;
        return Output.localization.localize("text.relicRarity." + relic.tier.name().toLowerCase());
    }

    public static String getRelicDescription(AbstractRelic relic) {
        return TextParser.parse(relic.description, relic);
    }

    public static String getRelicFlavorText(AbstractRelic relic) {
        return TextParser.parse(relic.flavorText, relic);
    }
}
