package sayTheSpire.UIPatches.credits;

import java.util.Comparator;
import com.megacrit.cardcrawl.credits.CreditLine;
import basemod.ReflectionHacks;

public class CreditLineComparator implements Comparator<CreditLine> {

    public int compare(CreditLine l1, CreditLine l2) {
        float y1 = (float) ReflectionHacks.getPrivate(l1, CreditLine.class, "y");
        float y2 = (float) ReflectionHacks.getPrivate(l2, CreditLine.class, "y");
        return (int) (y1 - y2);
    }
}
