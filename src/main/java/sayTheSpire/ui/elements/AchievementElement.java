package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import basemod.ReflectionHacks;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.ui.positions.AbstractPosition;

public class AchievementElement extends UIElement {

    private AchievementItem achievement;

    public AchievementElement(AchievementItem achievement) {
        this(achievement, null);
    }

    public AchievementElement(AchievementItem achievement, AbstractPosition position) {
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
            return "unlocked";
        }
        return "locked";
    }

    public String getLabel() {
        return this.getTitle();
    }
}
