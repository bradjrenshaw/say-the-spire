package sayTheSpire.utils;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import java.util.ArrayList;
import sayTheSpire.TextParser;

public class CampfireUtils {

  public static String getOptionLabel(AbstractCampfireOption option) {
    return (String) ReflectionHacks.getPrivate(option, AbstractCampfireOption.class, "label");
  }

  public static String getOptionDescription(AbstractCampfireOption option) {
    return TextParser.parse(
        (String) ReflectionHacks.getPrivate(option, AbstractCampfireOption.class, "description"));
  }

  public static ArrayList<String> getOptionUIBuffer(AbstractCampfireOption option) {
    ArrayList<String> contents = new ArrayList();
    contents.add(getOptionLabel(option));
    contents.add(getOptionDescription(option));
    return contents;
  }
}
