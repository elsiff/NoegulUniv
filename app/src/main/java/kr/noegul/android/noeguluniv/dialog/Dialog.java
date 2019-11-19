package kr.noegul.android.noeguluniv.dialog;

public class Dialog {
    private final Script script;
    private int currentIndex;

    public Dialog(Script script) {
        this.script = script;
        this.currentIndex = 0;
    }

    public Script getScript() {
        return script;
    }

    public Script.Speech nextSpeech() {
        currentIndex++;
        return script.getSpeech(currentIndex);
    }

    public boolean hasEnded() {
        return currentIndex >= (script.getNumSpeech() - 1);
    }
}
