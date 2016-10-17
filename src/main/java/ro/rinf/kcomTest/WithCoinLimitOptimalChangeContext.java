package ro.rinf.kcomTest;

import java.util.Collection;
import java.util.Iterator;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    private WholeCoinsSet wholeCoinsSet;
    private Iterator<SameCoinSet> coinsSetsIterator;
    private SameCoinSet currentCoinSet;

    public WithCoinLimitOptimalChangeContext(WholeCoinsSet wcs, int amount) {
        super(amount);
        wholeCoinsSet = wcs;
    }

    @Override
    protected void initIterator() {
        coinsSetsIterator = wholeCoinsSet.iterator();
    }

    @Override
    protected void getNextCoin() {
        if( coinsSetsIterator.hasNext() ) {
            currentCoinSet = coinsSetsIterator.next();
            if( currentCoinSet.isEmpty() ) {
                getNextCoin();
            } else {
                coin = currentCoinSet.getCoin();
            }
        }
    }

    @Override
    protected void addCoin(Coin coin) {
        super.addCoin(coin);
    }

    @Override
    public Collection<Coin> getOptimalChangeFor() {
        initIterator();
        getNextCoin();
        while(needsCoin()) {
            if( coin.fitsIn(amount) ) {
                addCoin(coin);
            } else {
                getNextCoin();
            }
        }
        return toReturn;
    }

}
