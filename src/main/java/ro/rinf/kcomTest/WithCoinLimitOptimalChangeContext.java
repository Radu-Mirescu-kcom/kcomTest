package ro.rinf.kcomTest;

import java.util.Collection;
import java.util.Iterator;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    private Inventory inventory;
    private Iterator<SameCoinSet> coinsSetsIterator;
    private SameCoinSet currentCoinSet;

    public WithCoinLimitOptimalChangeContext(Inventory wcs, int amount) {
        super(amount);
        inventory = wcs;
    }

    @Override
    protected void initIterator() {
        coinsSetsIterator = inventory.iterator();
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
        } else {
            throw new InsufficientCoinageException();
        }
    }

    @Override
    protected void addCoin(Coin coin) {
        super.addCoin(coin);
    }

    @Override
    public Collection<Coin> getChangeFor() {
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
