package sayTheSpire.utils;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import sayTheSpire.TextParser;

public class BlightUtils {

    public static String getBlightShort(AbstractBlight blight) {
        if (blight.counter >= 0) {
            return blight.name + " (" + blight.counter + ")";
        }
        return blight.name;
    }

    public static String getBlightDescription(AbstractBlight blight) {
        return TextParser.parse(blight.description, blight);
    }

}
