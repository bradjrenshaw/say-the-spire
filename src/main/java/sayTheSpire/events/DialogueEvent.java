package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class DialogueEvent extends Event {

    private String message;
    private String source;
    private String type;

    public DialogueEvent(String type, com.megacrit.cardcrawl.core.AbstractCreature source, String message) {
        super("dialogue");
        this.message = message;
        this.source = source.name;
        this.type = type;
        this.context.put("message", message);
        this.context.put("source", this.source);
        this.context.put("dialogueType", this.context.localize(".text.dialogueTypes." + type));
    }

    public DialogueEvent(String type, String source, String message) {
        super("dialogue");
        this.message = message;
        this.source = source;
        this.type = type;
        this.context.put("message", message);
        this.context.put("source", this.context.localize(".text.npcNames." + source));
        this.context.put("dialogueType", this.context.localize(".text.dialogueTypes." + type));
    }

    public Boolean isComplete() {
        return this.source != null && this.message != null;
    }
}
