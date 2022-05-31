package sayTheSpire.buffers;

import java.util.ListIterator;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import sayTheSpire.utils.BlightUtils;
import sayTheSpire.TextParser;

public class BlightBuffer extends Buffer {

    private AbstractBlight blight;

    public BlightBuffer(String name) {
        super("blight", name);
        this.blight = null;
    }

    public Object getObject() {
        return this.blight;
    }

    public void setObject(Object object) {
        this.blight = (AbstractBlight) object;
    }

    public void update() {
        this.clear();
        if (this.blight == null) {
            this.addLocalized("noObj");
            return;
        }
        this.add(this.blight.name);
        this.context.put("counter", this.blight.counter);
        if (this.blight.counter >= 0) {
            this.addLocalized("counter");
        }
        this.add(BlightUtils.getBlightDescription(this.blight));
        ListIterator iter = this.blight.tips.listIterator(1);
        while (iter.hasNext()) {
            PowerTip tip = (PowerTip) iter.next();
            this.add(tip.header + "\n" + TextParser.parse(tip.body, this.blight));
        }
    }
}
