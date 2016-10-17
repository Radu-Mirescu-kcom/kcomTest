package ro.rinf.kcomTest;

import java.util.Iterator;
import java.util.List;

public class InventoryIterator implements Iterator<Coin> {
    int idx;
    int sz;
    private final List<SameCoinSet> coinSets;

    public InventoryIterator(Inventory inventory) {
        this.coinSets = inventory.getCoinSets();
        idx = -1;
        sz = coinSets.size();
    }

    @Override
    public boolean hasNext() {
        return idx < sz;
    }

    @Override
    public Coin next() {
        while(true) {
            idx++;
            if( idx == sz ) break;
            if( !coinSets.get(idx).isEmpty() ) {
                return coinSets.get(idx).getCoin();
            }
        }
        return null;
    }

    public Coin resetToNext(Coin lastCoin) {
        for(int i=0;i<coinSets.size();i++) {
            if( coinSets.get(i).getCoin() == lastCoin ) {
                coinSets.set(i,coinSets.get(i).increment());
                idx = i+1;
                if( idx == coinSets.size() ) throw new InsufficientCoinageException();
                return coinSets.get(idx).getCoin();
            }
        }
        throw new InsufficientCoinageException();
    }

    public void takeOne() {
        coinSets.set(idx,coinSets.get(idx).decrement());
    }
}
