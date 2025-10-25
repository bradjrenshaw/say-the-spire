package sayTheSpire.utils;

import sayTheSpire.TextParser;

public class EventUtils {

    public static String parseBodyText(String text) {
        return TextParser.parse(text, "event");
    }
}
