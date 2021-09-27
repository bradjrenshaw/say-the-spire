package sayTheSpire;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.utils.OutputUtils;

public class TextParser {

    public static String handleWordCardContext(String word) {
        word = handleWordEmphasized(word);
        return word;
    }

    public static String handleWordDynamicVariables(String word, HashMap<String, String> dynamicVariables) {
        if (word == null || dynamicVariables == null)
            return word;
        String tempWord = word.trim();
        if (tempWord.length() != 3 || !tempWord.startsWith("!") || !tempWord.endsWith("!"))
            return word;
        String variable = tempWord.substring(1, 2);
        return dynamicVariables.getOrDefault(variable, tempWord);
    }

    public static String handleWordEmphasized(String word) {
        if (!word.startsWith("*") || word.length() < 2)
            return word;
        return word.substring(1);
    }

    public static String handleWordEnergies(String word) {
        for (Map.Entry<String, String> entry : OutputUtils.getValidEnergyTypes().entrySet()) {
            word = word.replaceAll("\\[" + entry.getKey() + "\\]", entry.getValue());
        }
        return word;
    }

    public static String handleWordRelicContext(String word) {
        return word;
    }

    public static String extractMainWord(String word) {
        // This removes all styling tags to allow for propper word parsing.
        if (hasColor(word)) {
            word = word.substring(2);
        }

        // ~ and @ characters for word effects seem to be mutually exclusive so this is kept as per the
        // game behavior
        if (word.length() < 2)
            return word;
        if (word.charAt(0) == '~' && word.charAt(word.length() - 1) == '~') {
            word = word.substring(1, word.length() - 1);
        } else if (word.charAt(0) == '@' && word.charAt(word.length() - 1) == '@') {
            word = word.substring(1, word.length() - 1);
        }
        return word;
    }

    public static boolean hasColor(String word) {
        if (word.length() < 2 || word.charAt(0) != '#')
            return false;
        char c = word.charAt(1);
        return c == 'r' || c == 'g' || c == 'b' || c == 'y' || c == 'p';
    }

    public static Boolean hasOrb(String word) {
        return word.equals("[E]") || word.equals("[R]") || word.equals("[G]") || word.equals("[B]")
                || word.equals("[W]") || word.equals("[C]") || word.equals("[P]") || word.equals("[T]")
                || word.equals("[S]");
    }

    public static String parse(String text) {
        return parse(text, "", (HashMap<String, String>) null);
    }

    public static String parse(String text, String context) {
        return parse(text, context, (HashMap<String, String>) null);
    }

    public static String parse(String text, String context, HashMap<String, String> dynamicVariables) {
        // This is parsing the text as the game does.
        if (text == null)
            return null;
        if (text.length() <= 0)
            return text;
        if (text.equals(""))
            return text;
        StringBuilder sb = new StringBuilder();
        for (String word : text.split(" ")) {
            if (word.equals(""))
                continue;
            word = extractMainWord(word);
            if (word.equals("NL")) {
                sb.append("\n");
                continue;
            } else if (word.equals("TAB")) {
                sb.append("\t");
                continue;
            }
            if (context.equals("card"))
                word = handleWordCardContext(word);
            else if (context.equals("relic"))
                word = handleWordRelicContext(word);
            word = handleWordEnergies(word);
            word = handleWordDynamicVariables(word, dynamicVariables);
            if (hasOrb(word)) {
                continue;
            }
            sb.append(word + " ");
        }
        return sb.toString();
    }

    public static String parse(String text, AbstractCard card) {
        return parse(text, "card", CardUtils.getCardDynamicVariables(card));
    }

    public static String parse(String text, AbstractPotion potion) {
        return parse(text, "potion");
    }

    public static String parse(String text, AbstractRelic relic) {
        return parse(text, "relic");
    }
}
