import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 参照知乎帖子实现的
 *
 * @author Jjn
 * @link {<a href="https://www.zhihu.com/question/22625187/answer/85530416">微信红包的随机算法是怎样实现的？ - 沉简的回答 - 知乎</a>}
 * @since 2023/2/11 12:41
 */
public class PocketMoneyImproved {

    static class RedPocket {
        /**
         * 单位为分，规避浮点数的精度问题
         */
        private Integer remainMoney;

        public Integer getRemainMoney() {
            return remainMoney;
        }

        public Integer getRemainSize() {
            return remainSize;
        }

        private Integer remainSize;

        public RedPocket(Integer remainMoney, Integer remainSize) {
            if (remainMoney < remainSize) {
                throw new IllegalArgumentException("total money as cent should be at least the same as total person count.");
            }
            this.remainMoney = remainMoney;
            this.remainSize = remainSize;
        }

        public void setRemainMoney(int remainMoney) {
            this.remainMoney = remainMoney;
        }

        public void setRemainSize(Integer remainSize) {
            this.remainSize = remainSize;
        }
    }

    public static String getRandomMoney(RedPocket redPocket) {
        Integer remainMoney = redPocket.getRemainMoney();
        Integer remainSize = redPocket.getRemainSize();
        if (remainSize <= 1) {
            redPocket.setRemainSize(0);
            return String.format("%.2f", remainMoney / 100.0);
        }
        Random random = new Random();
        int max = remainMoney / remainSize * 2;
        int current = (int) (random.nextDouble() * max);
        current = Math.max(1, current);
        redPocket.setRemainMoney(remainMoney - current);
        remainSize--;
        redPocket.setRemainSize(remainSize);
        return String.format("%.2f", current / 100.0);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RedPocket redPocket = new RedPocket(10000, 10);
            List<String> pockets = new ArrayList<>();
            for (int j = redPocket.getRemainSize(); j > 0; j--) {
                pockets.add(getRandomMoney(redPocket));
            }
            System.out.printf("第%d次,生成的随机序列为:%s%n", i + 1, Arrays.toString(pockets.toArray()));
        }
    }
}
