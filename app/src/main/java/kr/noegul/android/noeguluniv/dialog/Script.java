package kr.noegul.android.noeguluniv.dialog;

import java.util.ArrayList;
import java.util.List;

public class Script {
    private final List<Speech> speeches;
    private final Runnable dialogEndHandler;

    public Script(List<Speech> speeches, Runnable dialogEndHandler) {
        this.speeches = speeches;
        this.dialogEndHandler = dialogEndHandler;
    }

    public Speech getSpeech(int index) {
        return speeches.get(index);
    }

    public int getNumSpeech() {
        return speeches.size();
    }

    public Runnable getDialogEndHandler() {
        return dialogEndHandler;
    }

    public static class Speech {
        private final String speaker;
        private final String message;

        public Speech(String speaker, String message) {
            this.speaker = speaker;
            this.message = message;
        }

        public String getSpeaker() {
            return speaker;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Builder {
        private List<Speech> speeches = new ArrayList<>();
        private Runnable dialogEndHandler;

        public Builder putSpeech(Speech speech) {
            speeches.add(speech);
            return this;
        }

        public Builder putSpeech(String speaker, String message) {
            return putSpeech(new Speech(speaker, message));
        }

        public Builder setOnDialogEndHandler(Runnable dialogEndHandler) {
            this.dialogEndHandler = dialogEndHandler;
            return this;
        }

        public Script build() {
            return new Script(speeches, dialogEndHandler);
        }
    }
}
