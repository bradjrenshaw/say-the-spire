package sayTheSpire.ui.mod;

import java.util.ArrayList;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import sayTheSpire.ui.dynamic.contexts.ModMenuContext;
import sayTheSpire.ui.elements.MonsterElement;
import sayTheSpire.Output;
import sayTheSpire.utils.MapUtils;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.ui.elements.MonsterElement;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.InfoControls;

public class GameContext extends Context {

    public GameContext() {
        this.shouldForceControllerMode = true;
    }

    private void readPlayerAttribute(String name) {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null) {
            Output.textLocalized("errors.not in run", false);
            return;
        }
        switch (name) {
        case "read act boss":
            Output.text(MapUtils.getLocalizedBossName(), false);
            return;
        case "read player block":
            if (OutputUtils.isInCombat()) {
                Output.textLocalized("ui.misc info.playerBlock", false, "block", player.currentBlock);
            } else {
                Output.textLocalized("errors.not in combat", false);
            }
            return;
        case "read player energy":
            if (OutputUtils.isInCombat()) {
                Output.textLocalized("ui.misc info.playerEnergy", false, "energy", EnergyPanel.totalCount);
            } else {
                Output.textLocalized("errors.not in combat", false);
            }
            return;
        case "read player gold":
            Output.textLocalized("ui.misc info.playerGold", false, "gold", player.gold);
            return;
        case "read player hp":
            Output.textLocalized("ui.misc info.playerHealth", false, "hp", player.currentHealth, "hpMax",
                    player.maxHealth);
            return;
        case "read player powers":
            if (OutputUtils.isInCombat()) {
                String powers = OutputUtils.getCreaturePowersString(OutputUtils.getPlayer());
                Output.text(powers, false);
            } else {
                Output.textLocalized("errors.not in combat", false);
            }
        }
    }

    public void onClearJust() {
        // clear here
    }

    public Boolean onJustPress(InputAction action) {
        switch (action.getName()) {
        case "mod menu":
            Output.uiManager.pushContext(new ModMenuContext());
            return true;
        case "read act boss":
        case "read player block":
        case "read player energy":
        case "read player gold":
        case "read player hp":
        case "read player powers":
            this.readPlayerAttribute(action.getName());
            return true;
        case "read summarized intents":
            this.readSummarizedIntents();
            return true;
        case "read detailed intents":
            this.readDetailedIntents();
            return true;
        case "inspect up":
            InfoControls.infoControls(InfoControls.Direction.UP);
            break;
        case "inspect down":
            InfoControls.infoControls(InfoControls.Direction.DOWN);
            break;
        case "inspect left":
            InfoControls.infoControls(InfoControls.Direction.LEFT);
            break;
        case "inspect right":
            InfoControls.infoControls(InfoControls.Direction.RIGHT);
            break;
        }
        CInputAction gameAction = action.getGameControllerAction();
        if (gameAction != null) {
            action.setGameControllerActionPressed(true);
            action.setGameControllerActionJustPressed(true);
        }
        return true;
    }

    public Boolean onPress(InputAction action) {
        action.setGameControllerActionJustPressed(false);
        action.setGameControllerActionPressed(true);
        return true;
    }

    public Boolean onJustRelease(InputAction action) {
        CInputAction gameAction = action.getGameControllerAction();
        if (gameAction != null) {
            action.setGameControllerActionJustReleased(true);
            action.setGameControllerActionPressed(false);
            action.setGameControllerActionJustPressed(false);
        }
        return true;
    }

    private void readSummarizedIntents() {
        if (!OutputUtils.isInCombat()) {
            Output.textLocalized("errors.not in combat", false);
            return;
        }
        if (OutputUtils.playerHasRelic("Runic Dome")) {
            Output.textLocalized("ui.misc info.hiddenIntents", false);
            return;
        }
        int totalDmg = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            MonsterElement monster = new MonsterElement(m);
            if (!monster.isInCombat())
                continue;
            if (monster.isMultiDmg()) {
                totalDmg += monster.getIntentDmg() * monster.getIntentMultiAmt();
            } else {
                totalDmg += monster.getIntentDmg();
            }
        }
        Output.textLocalized("ui.misc info.incomingDamage", false, "damage", totalDmg);
    }

    private void readDetailedIntents() {
        if (!OutputUtils.isInCombat()) {
            Output.textLocalized("errors.not in combat", false);
            return;
        }
        if (OutputUtils.playerHasRelic("Runic Dome")) {
            Output.textLocalized("ui.misc info.hiddenIntents", false);
            return;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList<MonsterElement> aliveMonsters = new ArrayList();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            MonsterElement monster = new MonsterElement(m);
            if (monster.isInCombat())
                aliveMonsters.add(monster);
        }
        int monsterCount = aliveMonsters.size();
        if (monsterCount == 0) {
            Output.textLocalized("ui.misc info.noMonsters", false);
            return;
        }
        for (int c = 0; c < monsterCount; c++) {
            MonsterElement monster = aliveMonsters.get(c);
            sb.append(monster.getName() + " " + monster.getIntentShort());
            if (c < monsterCount - 1)
                sb.append(", ");
        }
        Output.text(sb.toString(), false);
    }
}
