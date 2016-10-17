package ro.rinf.kcomTest;

import java.util.Iterator;
import java.util.List;

public class InventoryIterator implements Iterator<Coin> {
    int idx;
    int sz;
    private final Inventory inventory;

    public InventoryIterator(Inventory inventory) {
        this.inventory = inventory;
        idx = -1;
        sz = inventory.getCoinSets().size();
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
            if( !inventory.getCoinSets().get(idx).isEmpty() ) {
                return inventory.getCoinSets().get(idx).getCoin();
            }
        }
        return null;
    }

    public Coin resetToNext(Coin lastCoin) {
        List<SameCoinSet> coinSets = inventory.getCoinSets();
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
        List<SameCoinSet> coinSets = inventory.getCoinSets();
        coinSets.set(idx,coinSets.get(idx).decrement());
    }
}
