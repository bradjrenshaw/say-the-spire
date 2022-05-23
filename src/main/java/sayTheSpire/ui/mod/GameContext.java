package sayTheSpire.ui.mod;

import java.util.ArrayList;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sayTheSpire.Output;
import sayTheSpire.utils.MapUtils;
import sayTheSpire.utils.MonsterUtils;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.ui.input.InputAction;

public class GameContext extends Context {

    public GameContext() {
        this.shouldForceControllerMode = true;
    }

    private void readPlayerAttribute(String name) {
        AbstractPlayer player = OutputUtils.getPlayer();
        if (player == null) {
            Output.text("Not currently in a run.", false);
            return;
        }
        switch (name) {
        case "read act boss":
            Output.text(MapUtils.getLocalizedBossName(), false);
            return;
        case "read player block":
            if (OutputUtils.isInCombat()) {
                Output.text(player.currentBlock + " block", false);
            } else {
                Output.text("Not currently in combat.", false);
            }
            return;
        case "read player energy":
            if (OutputUtils.isInCombat()) {
                Output.text(EnergyPanel.totalCount + " energy", false);
            } else {
                Output.text("Not currently in combat.", false);
            }
            return;
        case "read player gold":
            Output.text(player.gold + " gold", false);
            return;
        case "read player hp":
            Output.text(player.currentHealth + "/" + player.maxHealth + " hp", false);
            return;
        }
    }

    public void onClearJust() {
        // clear here
    }

    public Boolean onJustPress(InputAction action) {
        switch (action.getName()) {
        case "read act boss":
        case "read player block":
        case "read player energy":
        case "read player gold":
        case "read player hp":
            this.readPlayerAttribute(action.getName());
            return true;
        case "read summarized intents":
            this.readSummarizedIntents();
            return true;
        case "read detailed intents":
            this.readDetailedIntents();
            return true;
        case "inspect up":
            Output.infoControls(Output.Direction.UP);
            break;
        case "inspect down":
            Output.infoControls(Output.Direction.DOWN);
            break;
        case "inspect left":
            Output.infoControls(Output.Direction.LEFT);
            break;
        case "inspect right":
            Output.infoControls(Output.Direction.RIGHT);
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
            Output.text("Not currently in combat.", false);
            return;
        }
        if (OutputUtils.playerHasRelic("Runic Dome")) {
            Output.text("hidden intents", false);
            return;
        }
        int totalDmg = 0;
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!MonsterUtils.getMonsterIsInCombat(monster))
                continue;
            if (MonsterUtils.getMonsterIsMultiDmg(monster)) {
                totalDmg += MonsterUtils.getMonsterIntentDmg(monster) * MonsterUtils.getMonsterIntentMultiAmt(monster);
            } else {
                totalDmg += MonsterUtils.getMonsterIntentDmg(monster);
            }
        }
        Output.text(totalDmg + " incoming damage", false);
    }

    private void readDetailedIntents() {
        if (!OutputUtils.isInCombat()) {
            Output.text("Not currently in combat.", false);
            return;
        }
        if (OutputUtils.playerHasRelic("Runic Dome")) {
            Output.text("hidden intents", false);
            return;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList<AbstractMonster> aliveMonsters = new ArrayList();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (MonsterUtils.getMonsterIsInCombat(monster))
                aliveMonsters.add(monster);
        }
        int monsterCount = aliveMonsters.size();
        if (monsterCount == 0) {
            Output.text("No monsters", false);
            return;
        }
        for (int c = 0; c < monsterCount; c++) {
            AbstractMonster monster = aliveMonsters.get(c);
            sb.append(monster.name + " " + MonsterUtils.getMonsterIntentShort(monster));
            if (c < monsterCount - 1)
                sb.append(", ");
        }
        Output.text(sb.toString(), false);
    }
}
