package sayTheSpire.buffers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sayTheSpire.TextParser;
import sayTheSpire.utils.OutputUtils;

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

        Boolean isCollector = false;

        try {
            Class<?> enumPatch = Class.forName("collector.CollectorChar$Enums");
            if (AbstractDungeon.player.chosenClass == (AbstractPlayer.PlayerClass) ReflectionHacks
                    .getPrivateStatic(enumPatch, "THE_COLLECTOR")) {
                isCollector = true;
            }
        } catch (Throwable ignored) {
        }

        this.clear();
        if (!OutputUtils.canGetPlayer()) {
            this.addLocalized("noObj");
            return;
        }
        AbstractPlayer player = AbstractDungeon.player;

        int tempHp = 0;

        try {
            Class<?> cls = Class
                    .forName("com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField");
            Object spireField = cls.getField("tempHp").get(null);
            tempHp = (int) spireField.getClass().getMethod("get", Object.class).invoke(spireField, player);
        } catch (Throwable t) {
        }

        this.context.put("hp", player.currentHealth);
        this.context.put("tempHp", tempHp);
        this.context.put("hpMax", player.maxHealth);
        this.context.put("gold", player.gold);
        this.add(CardCrawlGame.playerName);
        if (tempHp > 0) {
            this.addLocalized("content.hpAndTempHp");
        } else {
            this.addLocalized("content.hp");
        }
        if (OutputUtils.isInCombat()) {
            int reserves = 0;
            try {
                Class<?> newReserves = Class.forName("collector.util.NewReserves");
                reserves = ReflectionHacks.privateStaticMethod(newReserves, "reserveCount").invoke();
            } catch (Throwable ignored) {
            }

            this.context.put("energy", EnergyPanel.totalCount + reserves);
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
        if (isCollector) {
            int essences = 0;

            try {
                essences = ReflectionHacks
                        .privateStaticMethod(Class.forName("collector.util.EssenceSystem"), "essenceCount").invoke();
            } catch (Throwable ignored) {
            }

            this.context.put("essences", essences);
            this.addLocalized("content.essences");
        }
    }
}
