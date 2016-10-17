package ro.rinf.kcomTest;

import java.util.Collection;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    public WithCoinLimitOptimalChangeContext(int amount) {
        super(amount);
    }

    @Override
    public Collection<Coin> getOptimalChangeFor() {
        return super.getOptimalChangeFor();
    }
}
