package kr.noegul.android.noeguluniv.course.countblocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CountBlocks {
    private static final Random random = new Random();
    private Quiz currentQuiz;
    private int numSolved = 0;
    private int numFailed = 0;

    public CountBlocks() {
        this.currentQuiz = generateQuiz();
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
        int numBlocks = 5 + numSolved / 2 + random.nextInt(numSolved / 3 + 1);

        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0, 0));

        int x = 0;
        int y = 0;
        int z = 0;
        for (int i = 0; i < numBlocks; i++) {
            Block block;
            do {
                double rand = Math.random();
                if (rand <= 0.5) {
                    block = new Block(Math.min(x + (random.nextBoolean() ? 1 : -1), 4), y, z);
                } else if (rand <= 0.9) {
                    block = new Block(x, Math.min(y + 1, 4), z);
                } else {
                    block = new Block(x, y, Math.min(z + 1, 4));
                }

                while (block.getY() > 0 &&
                        !blocks.contains(new Block(block.getX(), block.getY() - 1, block.getZ()))) {
                    block = new Block(block.getX(), block.getY() - 1, block.getZ());
                }
            } while (blocks.contains(block));

            blocks.add(block);
            x = block.getX();
            y = block.getY();
            z = block.getZ();
        }

        Collections.sort(blocks, new Comparator<Block>() {
            @Override
            public int compare(Block a, Block b) {
                int comp = Integer.compare(a.getZ(), b.getZ());
                if (comp == 0) {
                    comp = Integer.compare(a.getY(), b.getY());
                }
                if (comp == 0) {
                    comp = Integer.compare(a.getX(), b.getX());
                }
                return comp;
            }
        });

        return new Quiz(blocks);
    }

    public class Quiz {
        private final List<Block> blocks;

        public Quiz(List<Block> blocks) {
            this.blocks = blocks;
        }

        public List<Block> getBlocks() {
            return blocks;
        }

        public int getAnswer() {
            return blocks.size();
        }
    }

    public class Block {
        private final int x;
        private final int y;
        private final int z;

        public Block(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Block block = (Block) o;
            return x == block.x &&
                    y == block.y &&
                    z == block.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
