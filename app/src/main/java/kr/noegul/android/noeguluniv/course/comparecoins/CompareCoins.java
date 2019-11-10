package kr.noegul.android.noeguluniv.course.comparecoins;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CompareCoins {
    private static final int[] COIN_VALUES = {10, 50, 100, 500};
    private static final Random random = new Random();
    private Quiz currentQuiz;
    private int numSolved;
    private int numFailed;

    public CompareCoins() {
        currentQuiz = generateQuiz();
        numSolved = 0;
        numFailed = 0;
    }

    public void solveCurrentQuiz() {
        numSolved++;
        currentQuiz = generateQuiz();
    }

    public void failCurrentQuiz() {
        numFailed++;
        currentQuiz = generateQuiz();
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public int getNumSolved() {
        return numSolved;
    }

    public int getNumFailed() {
        return numFailed;
    }

    private Quiz generateQuiz() {
        Choice choiceA = generateChoice(numSolved);

        Choice choiceB;
        do {
            choiceB = generateChoice(numSolved);
        } while (choiceB.getTotalValue() == choiceA.getTotalValue());

        Map<ChoiceLabel, Choice> choices = new EnumMap<>(ChoiceLabel.class);
        choices.put(ChoiceLabel.A, choiceA);
        choices.put(ChoiceLabel.B, choiceB);

        return new Quiz(choices);
    }

    private Choice generateChoice(int numSolved) {
        List<Coin> coins = new ArrayList<>();

        int numCoins = 2 + (Math.min(numSolved, 18) / 2) + (random.nextInt(3) - 1); // 2~12
        for (int i = 0; i < numCoins; i++) {
            int coinValue = COIN_VALUES[random.nextInt(COIN_VALUES.length)];
            boolean flipped = false;

            if (numSolved >= 10 && Math.random() <= 0.3)
                flipped = true;

            Coin coin = new Coin(coinValue, flipped);
            coins.add(coin);
        }
        return new Choice(coins);
    }

    public class Quiz {
        private final Map<ChoiceLabel, Choice> choices;
        private final ChoiceLabel answer;

        public Quiz(Map<ChoiceLabel, Choice> choices) {
            this.choices = choices;

            ChoiceLabel bestChoiceLabel = null;
            Choice bestChoice = null;
            for (Map.Entry<ChoiceLabel, Choice> entry : choices.entrySet()) {
                ChoiceLabel label = entry.getKey();
                Choice choice = entry.getValue();

                if (bestChoice == null || choice.getTotalValue() > bestChoice.getTotalValue()) {
                    bestChoiceLabel = label;
                    bestChoice = choice;
                }
            }
            this.answer = bestChoiceLabel;
        }

        public Choice getChoice(ChoiceLabel label) {
            return choices.get(label);
        }

        public Choice getChoiceA() {
            return getChoice(ChoiceLabel.A);
        }

        public Choice getChoiceB() {
            return getChoice(ChoiceLabel.B);
        }

        public ChoiceLabel getAnswer() {
            return answer;
        }
    }

    public class Choice {
        private List<Coin> coins;

        public Choice(List<Coin> coins) {
            this.coins = coins;
        }

        public List<Coin> getCoins() {
            return coins;
        }

        public int getTotalValue() {
            int sum = 0;
            for (Coin coin : coins) {
                sum += coin.getValue();
            }
            return sum;
        }
    }

    public class Coin {
        private final int value;
        private final boolean flipped;

        public Coin(int value, boolean flipped) {
            this.value = value;
            this.flipped = flipped;
        }

        public int getValue() {
            return value;
        }

        public boolean isFlipped() {
            return flipped;
        }
    }

    public enum ChoiceLabel {
        A, B
    }
}
