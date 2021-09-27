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
        super(name);
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
        if (!OutputUtils.canGetPlayer())
            return null;
        return AbstractDungeon.player;
    }

    public void update() {
        this.clear();
        if (!OutputUtils.canGetPlayer())
            return;
        AbstractPlayer player = AbstractDungeon.player;
        this.add(CardCrawlGame.playerName);
        this.add(player.currentHealth + "/" + player.maxHealth + " hp");
        if (OutputUtils.isInCombat()) {
            this.add(EnergyPanel.totalCount + " energy");
            this.add(player.currentBlock + " block");
            this.add(OutputUtils.getCreaturePowersString(player));
            if (!(player.stance instanceof NeutralStance)) {
                this.add(player.stance.name + "\n" + TextParser.parse(player.stance.description, "stance"));
            }
            for (AbstractPower p : player.powers) {
                this.add(p.name + "\n" + TextParser.parse(p.description, "power"));
            }
        }
        this.add(player.gold + " gold");
    }
}
