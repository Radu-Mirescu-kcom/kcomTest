package ro.rinf.kcomTest;

import java.util.Collection;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    private WholeCoinsSet wholeCoinsSet;

    public WithCoinLimitOptimalChangeContext(WholeCoinsSet wcs, int amount) {
        super(amount);
        wholeCoinsSet = wcs;
    }

    @Override
    public Collection<Coin> getOptimalChangeFor() {
        return super.getOptimalChangeFor();
    }
}
