package sayTheSpire.utils;

import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;

public class InputUtils {

    public static CInputAction[] getCInputActions() {
        CInputAction actions[] = {CInputActionSet.select, CInputActionSet.cancel, CInputActionSet.topPanel, CInputActionSet.proceed, CInputActionSet.peek, CInputActionSet.pageLeftViewDeck, CInputActionSet.pageRightViewExhaust, CInputActionSet.map, CInputActionSet.settings, CInputActionSet.drawPile, CInputActionSet.discardPile, CInputActionSet.up, CInputActionSet.down, CInputActionSet.left, CInputActionSet.right, CInputActionSet.inspectUp, CInputActionSet.inspectDown, CInputActionSet.inspectLeft, CInputActionSet.inspectRight, CInputActionSet.altUp, CInputActionSet.altDown, CInputActionSet.altLeft, CInputActionSet.altRight};
        return actions;
    }
}
