package ro.rinf.kcomTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SimpleOptimalChangeContext {
    private int amount;
    private List<Coin> toReturn = new ArrayList<>();

    public SimpleOptimalChangeContext(int amount) {
        this.amount = amount;
    }

    private boolean needsCoin() {
        return amount > 0;
    }

    private void addCoin(Coin coin) {
        toReturn.add(coin);
        amount -= coin.getDenomination();
    }

    public Collection<Coin> getOptimalChangeFor() {
        Iterator<Coin> it = Arrays.asList(Coin.values()).iterator();
        Coin coin = it.next();
        while(needsCoin()) {
            if( coin.fitsIn(amount) ) {
                addCoin(coin);
            } else {
                coin = it.next();
            }
        }
        return toReturn;
    }
}
