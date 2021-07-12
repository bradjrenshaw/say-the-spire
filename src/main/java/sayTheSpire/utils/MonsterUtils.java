package sayTheSpire.utils;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sayTheSpire.TextParser;

public class MonsterUtils {
  private static final UIStrings uiStrings =
      CardCrawlGame.languagePack.getUIString("AbstractMonster");
  public static final String[] TEXT = uiStrings.TEXT;

  public static String getMonsterShort(AbstractMonster monster) {
    return monster.name
        + ", "
        + getMonsterIntentShort(monster)
        + ", block "
        + monster.currentBlock
        + ", "
        + monster.currentHealth
        + "/"
        + monster.maxHealth;
  }

  public static int getMonsterIntentDmg(AbstractMonster monster) {
    return monster.getIntentDmg();
  }

  public static Boolean getMonsterIsMultiDmg(AbstractMonster monster) {
    return (Boolean) ReflectionHacks.getPrivate(monster, AbstractMonster.class, "isMultiDmg");
  }

  public static int getMonsterIntentMultiAmt(AbstractMonster monster) {
    return (int) ReflectionHacks.getPrivate(monster, AbstractMonster.class, "intentMultiAmt");
  }

  public static String getMonsterIntentShort(AbstractMonster monster) {
    if (OutputUtils.playerHasRelic("Runic Dome")) return "hidden intent";
    switch (monster.intent) {
      case ATTACK:
        if (getMonsterIsMultiDmg(monster)) {
          return TEXT[0]
              + " "
              + getMonsterIntentDmg(monster)
              + "x"
              + getMonsterIntentMultiAmt(monster);
        } else {
          return TEXT[0] + " " + getMonsterIntentDmg(monster);
        }
      case ATTACK_BUFF:
        if (getMonsterIsMultiDmg(monster)) {
          return TEXT[6]
              + " "
              + getMonsterIntentDmg(monster)
              + "x"
              + getMonsterIntentMultiAmt(monster);
        } else {
          return TEXT[6] + " " + getMonsterIntentDmg(monster);
        }
      case ATTACK_DEBUFF:
        if (getMonsterIsMultiDmg(monster)) {
          return TEXT[10]
              + " "
              + getMonsterIntentDmg(monster)
              + "x"
              + getMonsterIntentMultiAmt(monster);
        } else {
          return TEXT[10] + " " + getMonsterIntentDmg(monster);
        }
      case ATTACK_DEFEND:
        if (getMonsterIsMultiDmg(monster)) {
          return TEXT[0]
              + " "
              + getMonsterIntentDmg(monster)
              + "x"
              + getMonsterIntentMultiAmt(monster);
        } else {
          return TEXT[0] + " " + getMonsterIntentDmg(monster);
        }
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
    if (OutputUtils.playerHasRelic("Runic Dome")) return "hidden intent";
    PowerTip tip =
        (PowerTip) ReflectionHacks.getPrivate(monster, AbstractMonster.class, "intentTip");
    return TextParser.parse(tip.body);
  }
}
