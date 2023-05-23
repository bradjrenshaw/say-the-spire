import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.screens.mainMenu.PatchNotesScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.Output;
import sayTheSpire.buffers.Buffer;

public class PatchNotesScreenPatches {

    private static ArrayList<String> patchNotes = null;

    private static void setupPatchNoteList() {
        if (patchNotes != null)
            return;
        patchNotes = new ArrayList();
        String text = (String) ReflectionHacks.getPrivateStatic(PatchNotesScreen.class, "text");
        for (String line : text.split(System.lineSeparator())) {
            patchNotes.add(line);
        }
        Collections.reverse(patchNotes);
    }

    @SpirePatch(clz = PatchNotesScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(PatchNotesScreen __instance) {
            setupPatchNoteList();
            Output.setupUIBuffer(patchNotes);
            Buffer buffer = Output.buffers.getBuffer("UI");
            buffer.setPosition(buffer.size() - 1);
        }
    }
}
