package ro.rinf.kcomTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NoCoinLimitOptimalChangeContext {
    private int amount;
    private List<Coin> toReturn = new ArrayList<>();
    Iterator<Coin> it = Arrays.asList(Coin.values()).iterator();
    Coin coin;

    public NoCoinLimitOptimalChangeContext(int amount) {
        this.amount = amount;
    }

    private boolean needsCoin() {
        return amount > 0;
    }

    private void addCoin(Coin coin) {
        toReturn.add(coin);
        amount -= coin.getDenomination();
    }

    private void getNextCoin() {
        coin = it.next();
    }

    public Collection<Coin> getOptimalChangeFor() {
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
