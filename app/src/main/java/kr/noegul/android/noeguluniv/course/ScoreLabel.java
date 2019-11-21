package kr.noegul.android.noeguluniv.course;

public enum ScoreLabel {
    A_PLUS("A+", 4.5),
    A("A", 4.0),
    B_PLUS("B+", 3.5),
    B("B", 3.0),
    C_PLUS("C+", 2.5),
    C("C", 2.0),
    D_PLUS("D+", 1.5),
    D("D", 1.0),
    F("F", 0.0);

    private String text;
    private double minScore;

    ScoreLabel(String text, double minScore) {
        this.text = text;
        this.minScore = minScore;
    }

    public String getText() {
        return text;
    }

    public double getMinScore() {
        return minScore;
    }

    public static ScoreLabel of(double score) {
        for (ScoreLabel label : values()) {
            if (score >= label.minScore)
                return label;
        }
        throw new IllegalArgumentException("Invalid score value");
    }
}
