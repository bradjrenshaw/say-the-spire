package sayTheSpire.events;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CombatMonsterEvent extends Event {

    public enum EffectType {
        DEFEATED
    };

    private AbstractMonster monster;
    private EffectType effectType;

    public CombatMonsterEvent(AbstractMonster monster, EffectType effectType) {
        super("combatMonster");
        this.monster = monster;
        this.effectType = effectType;
        this.context.put("target", this.monster.name);
    }

    public String getText() {
        switch (this.effectType) {
        case DEFEATED:
            return this.context.localize("defeated");
        }
        return null;
    }

}
