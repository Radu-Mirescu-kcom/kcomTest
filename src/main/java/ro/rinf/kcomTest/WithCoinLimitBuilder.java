package ro.rinf.kcomTest;

import java.util.Properties;

public class WithCoinLimitBuilder {
    private WholeCoinsSet wholeCoinsSet;

    public WithCoinLimitBuilder(Properties properties) {
        wholeCoinsSet = new WholeCoinsSet(properties);
    }

    public OptimalChangeContext makeContext(int amount) {
        return new WithCoinLimitOptimalChangeContext(wholeCoinsSet,amount);
    }
}
