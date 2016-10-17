package ro.rinf.kcomTest;

import java.util.Collection;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    private InventoryIterator inventoryIterator;
    private SameCoinSet currentCoinSet;

    public WithCoinLimitOptimalChangeContext(Inventory inventory, int amount) {
        super(amount);
        inventoryIterator = new InventoryIterator(inventory);
    }

    @Override
    protected void getNextCoin() {
        coin = inventoryIterator.next();
    }

    @Override
    protected void addCoin(Coin coin) {
        super.addCoin(coin);
        inventoryIterator.takeOne();
    }

    @Override
    public Collection<Coin> getChangeFor() {
        getNextCoin();
        while(needsCoin()) {
            if( coin.fitsIn(amount) ) {
                addCoin(coin);
            } else {
                getNextCoin();
                if( coin == null ) {
                    if( toReturn.isEmpty() ) {
                        throw new InsufficientCoinageException();
                    }
                    Coin lastCoin = toReturn.remove(toReturn.size()-1);
                    amount += lastCoin.getDenomination();
                    coin = inventoryIterator.resetToNext(lastCoin);
                }
            }
        }
        return toReturn;
    }
}
