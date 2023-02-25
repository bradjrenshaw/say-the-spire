package sayTheSpire.ui.elements;

import java.util.ArrayList;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import basemod.ReflectionHacks;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.positions.ListPosition;
import sayTheSpire.utils.MonsterUtils;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.TextParser;

public class MonsterElement extends UIElement {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AbstractMonster");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractMonster monster;

    public MonsterElement(AbstractMonster monster) {
        this(monster, null);
    }

    public MonsterElement(AbstractMonster monster, Position position) {
        super("monster", position);
        this.monster = monster;
    }

    public String handleBuffers(BufferManager buffers) {
        buffers.getBuffer("monster").setObject(this);
        buffers.enableBuffer("monster", true);
        return "monster";
    }

    public int getCurrentBlock() {
        return this.monster.currentBlock;
    }

    public int getCurrentHealth() {
        return this.monster.currentHealth;
    }

    public int getMaxHealth() {
        return this.monster.maxHealth;
    }

    public String getIntentDmgString() {
        if (this.isMultiDmg()) {
            return this.getIntentDmg() + "x" + this.getIntentMultiAmt();
        }
        return Integer.toString(this.getIntentDmg());
    }

    public int getIntentDmg() {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return 0;
        switch (this.monster.intent) {
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
        return this.monster.getIntentDmg();
    }

    public String getIntentLong() {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return this.localization.localize("hidden intent");
        PowerTip tip = (PowerTip) ReflectionHacks.getPrivate(this.monster, AbstractMonster.class, "intentTip");
        return TextParser.parse(tip.body);
    }

    public int getIntentMultiAmt() {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return 0;
        switch (this.monster.intent) {
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
        return (int) ReflectionHacks.getPrivate(this.monster, AbstractMonster.class, "intentMultiAmt");
    }

    public String getIntentShort() {
        if (OutputUtils.playerHasRelic("Runic Dome"))
            return this.localization.localize("hidden intent");
        switch (monster.intent) {
        case ATTACK:
            return TEXT[0] + " " + this.getIntentDmgString();
        case ATTACK_BUFF:
            return TEXT[6] + " " + this.getIntentDmgString();
        case ATTACK_DEBUFF:
            return TEXT[10] + " " + this.getIntentDmgString();
        case ATTACK_DEFEND:
            return TEXT[0] + " " + this.getIntentDmgString();
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

    public String getLabel() {
        this.localization.put("monster", this.getName());
        this.localization.put("intent", this.getIntentShort());
        this.localization.put("block", this.monster.currentBlock);
        this.localization.put("currentHealth", this.monster.currentHealth);
        this.localization.put("maxHealth", this.monster.maxHealth);
        return this.localization.localize("shortString");
    }

    public AbstractMonster getMonster() {
        return this.monster;
    }

    public String getName() {
        return MonsterUtils.getName(this.monster);
    }

    public Position getPosition() {
        MonsterGroup group = AbstractDungeon.getCurrRoom().monsters;
        if (group == null)
            return null;
        int index = group.monsters.indexOf(this.monster);
        if (index < 0)
            return null;
        return new ListPosition(index, group.monsters.size());
    }

    public Boolean isInCombat() {
        return !this.monster.isDead && !this.monster.escaped;
    }

    public Boolean isMultiDmg() {
        return (Boolean) ReflectionHacks.getPrivate(this.monster, AbstractMonster.class, "isMultiDmg");
    }

    public String getPowersString() {
        return OutputUtils.getCreaturePowersString(this.getMonster());
    }

    public ArrayList<PowerTip> getTips() {
        return (ArrayList<PowerTip>) ReflectionHacks.getPrivate(this.monster, AbstractCreature.class, "tips");
    }

    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (!(other instanceof MonsterElement))
            return false;
        return this.getMonster() == ((MonsterElement) other).getMonster();
    }
}
