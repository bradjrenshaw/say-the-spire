package sayTheSpire.buffers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.TextParser;

public class PlayerBuffer extends Buffer {

    public PlayerBuffer(String name) {
        super("player", name);
    }

    public PlayerBuffer() {
        this("player");
    }

    public Boolean getEnabled() {
        return OutputUtils.canGetPlayer();
    }

    public void setEnabled(Boolean enabled) {
        return; // does and should do nothing
    }

    public Object getObject() {
        return OutputUtils.getPlayer();
    }

    public void update() {
        this.clear();
        if (!OutputUtils.canGetPlayer()) {
            this.addLocalized("noObj");
            return;
        }
        AbstractPlayer player = AbstractDungeon.player;
        this.context.put("hp", player.currentHealth);
        this.context.put("hpMax", player.maxHealth);
        this.context.put("gold", player.gold);
        this.add(CardCrawlGame.playerName);
        this.addLocalized("content.hp");
        if (OutputUtils.isInCombat()) {
            this.context.put("energy", EnergyPanel.totalCount);
            this.context.put("block", player.currentBlock);
            this.addLocalized("content.energy");
            this.addLocalized("content.block");
            this.add(OutputUtils.getCreaturePowersString(player));
            if (!(player.stance instanceof NeutralStance)) {
                this.add(player.stance.name + "\n" + TextParser.parse(player.stance.description, "stance"));
            }
            for (AbstractPower p : player.powers) {
                this.add(p.name + "\n" + TextParser.parse(p.description, "power"));
            }
        }
        this.addLocalized("content.gold");
    }
}
