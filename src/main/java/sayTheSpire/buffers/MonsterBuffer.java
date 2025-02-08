package sayTheSpire.buffers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.stances.NeutralStance;
import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.AbstractEnemyStance;
import charbosses.ui.EnemyEnergyPanel;
import downfall.patches.EvilModeCharacterSelect;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.TextParser;
import sayTheSpire.ui.elements.MonsterElement;

public class MonsterBuffer extends Buffer {

    private MonsterElement monster;

    public MonsterBuffer(String name) {
        super("monster", name);
        this.monster = null;
    }

    public Object getObject() {
        return this.monster;
    }

    public void setObject(Object object) {
        this.monster = (MonsterElement) object;
    }

    public void update() {
        this.clear();
        if (this.monster == null) {
            this.addLocalized("noObj");
            return;
        }
        this.context.put("hp", this.monster.getCurrentHealth());
        this.context.put("hpMax", this.monster.getMaxHealth());
        this.context.put("block", this.monster.getCurrentBlock());
        this.add("xxx" + this.monster.getName() + "neve:");
        this.addLocalized("content.hp");
        this.addLocalized("content.block");
        this.add(this.monster.getPowersString());

        for (PowerTip tip : monster.getTips()) {
            this.add(TextParser.parse(tip.header + " NL " + tip.body));
        }

        // Downfall mod támogatás
        if (Loader.isModLoaded("downfall") && EvilModeCharacterSelect.evilMode
                && this.monster.getMonster() instanceof AbstractCharBoss) {
            AbstractCharBoss boss = (AbstractCharBoss) this.monster.getMonster();
            this.add("Hand:");
            for (AbstractCard card : boss.hand.group) {
                this.add(" - " + card.name);
            }
            this.add("Energy: " + EnemyEnergyPanel.totalCount);

            if (!boss.orbs.isEmpty()) {
                this.add("Orbs:");
                for (AbstractOrb orb : boss.orbs) {
                    if (orb instanceof Dark) {
                        this.add(" - Dark Orb (" + orb.evokeAmount + ")");
                    } else {
                        this.add(" - " + orb.name);
                    }
                }
            }

            if (boss.stance instanceof AbstractEnemyStance) {
                this.add("Stance: " + ((AbstractEnemyStance) boss.stance).ID);
            }

            if (!boss.relics.isEmpty()) {
                this.add("Relics:");
                for (AbstractRelic relic : boss.relics) {
                    if (relic.counter >= 0) {
                        this.add(" - " + relic.name + " (" + relic.counter + ")");
                    } else {
                        this.add(" - " + relic.name);
                    }
                }
            }
        }
    }
}
