package sayTheSpire.utils;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.shop.StorePotion;

public class PotionUtils {

    public static String getPotionShort(AbstractPotion potion) {
        return potion.name;
    }

    public static String getPotionShort(StorePotion potion) {
        return getPotionShort(potion.potion) + " price: " + potion.price;
    }
}
