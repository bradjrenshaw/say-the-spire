package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.Position;

public class AchievementElement extends UIElement {

    private AchievementItem achievement;

    public AchievementElement(AchievementItem achievement) {
        this(achievement, null);
    }

    public AchievementElement(AchievementItem achievement, Position position) {
        super("achievement", position);
        this.achievement = achievement;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.getTitle(), this.getDescription());
        return null;
    }

    private String getDescription() {
        return (String) ReflectionHacks.getPrivate(this.achievement, AchievementItem.class, "desc");
    }

    private String getTitle() {
        return (String) ReflectionHacks.getPrivate(this.achievement, AchievementItem.class, "title");
    }

    public String getStatusString() {
        if (this.achievement.isUnlocked) {
            return this.localization.localize(".ui.status.unlocked");
        }
        return this.localization.localize(".ui.status.locked");
    }

    public String getLabel() {
        return this.getTitle();
    }
}
