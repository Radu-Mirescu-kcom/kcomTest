package ro.rinf.kcomTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NoCoinLimitOptimalChangeContext implements ChangeContext {
    protected int amount;
    protected List<Coin> toReturn = new ArrayList<>();
    private Iterator<Coin> it;
    protected Coin coin;

    protected void initIterator() {
        it = Arrays.asList(Coin.values()).iterator();
    }

    public NoCoinLimitOptimalChangeContext(int amount) {
        this.amount = amount;
    }

    protected boolean needsCoin() {
        return amount > 0;
    }

    protected void addCoin(Coin coin) {
        toReturn.add(coin);
        amount -= coin.getDenomination();
    }

    protected void getNextCoin() {
        coin = it.next();
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
