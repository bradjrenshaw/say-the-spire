package sayTheSpire.utils;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sayTheSpire.TextParser;

public class MonsterUtils {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AbstractMonster");
    public static final String[] TEXT = uiStrings.TEXT;

    public static String getMonsterShort(AbstractMonster monster) {
        return monster.name + ", " + getMonsterIntentShort(monster) + ", block " + monster.currentBlock + ", "
                + monster.currentHealth + "/" + monster.maxHealth;
    }

    public static Boolean getMonsterIsInCombat(AbstractMonster monster) {
        return !monster.isDead && !monster.escaped;
    }

    public static int getMonsterIntentDmg(AbstractMonster monster) {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return 0;
        switch (monster.intent) {
        case ATTACK:
        case ATTACK_BUFF:
        case ATTACK_DEBUFF:
        case ATTACK_DEFEND:
            break;
        case BUFF:
        case DEBUFF:
        case STRONG_DEBUFF:
        case DEBUG:
        case DEFEND:
        case DEFEND_DEBUFF:
        case DEFEND_BUFF:
        case ESCAPE:
        case MAGIC:
        case NONE:
        case SLEEP:
        case STUN:
        default:
            return 0;
        }
        return monster.getIntentDmg();
    }

    public static Boolean getMonsterIsMultiDmg(AbstractMonster monster) {
        return (Boolean) ReflectionHacks.getPrivate(monster, AbstractMonster.class, "isMultiDmg");
    }

    public static int getMonsterIntentMultiAmt(AbstractMonster monster) {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return 0;
        switch (monster.intent) {
        case ATTACK:
        case ATTACK_BUFF:
        case ATTACK_DEBUFF:
        case ATTACK_DEFEND:
            break;
        case BUFF:
        case DEBUFF:
        case STRONG_DEBUFF:
        case DEBUG:
        case DEFEND:
        case DEFEND_DEBUFF:
        case DEFEND_BUFF:
        case ESCAPE:
        case MAGIC:
        case NONE:
        case SLEEP:
        case STUN:
        default:
            return 0;
        }
        return (int) ReflectionHacks.getPrivate(monster, AbstractMonster.class, "intentMultiAmt");
    }

    private static String getMonsterIntentDmgString(AbstractMonster monster) {
        if (getMonsterIsMultiDmg(monster)) {
            return getMonsterIntentDmg(monster) + "x" + getMonsterIntentMultiAmt(monster);
        }
        return Integer.toString(getMonsterIntentDmg(monster));
    }

    public static String getMonsterIntentShort(AbstractMonster monster) {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return "hidden intent";
        switch (monster.intent) {
        case ATTACK:
            return TEXT[0] + " " + getMonsterIntentDmgString(monster);
        case ATTACK_BUFF:
            return TEXT[6] + " " + getMonsterIntentDmgString(monster);
        case ATTACK_DEBUFF:
            return TEXT[10] + " " + getMonsterIntentDmgString(monster);
        case ATTACK_DEFEND:
            return TEXT[0] + " " + getMonsterIntentDmgString(monster);
        case BUFF:
        case DEBUFF:
        case STRONG_DEBUFF:
            return TEXT[10];
        case DEBUG:
        case DEFEND:
        case DEFEND_DEBUFF:
        case DEFEND_BUFF:
            return TEXT[13];
        case ESCAPE:
            return TEXT[14];
        case MAGIC:
            return TEXT[15];
        case NONE:
            return TEXT[16];
        case SLEEP:
            return TEXT[17];
        case STUN:
            return TEXT[18];
        default:
            return "";
        }
    }

    public static String getMonsterIntentLong(AbstractMonster monster) {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return "hidden intent";
        PowerTip tip = (PowerTip) ReflectionHacks.getPrivate(monster, AbstractMonster.class, "intentTip");
        return TextParser.parse(tip.body);
    }
}
