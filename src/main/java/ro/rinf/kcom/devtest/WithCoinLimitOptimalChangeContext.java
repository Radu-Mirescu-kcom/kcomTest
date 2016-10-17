package ro.rinf.kcom.devtest;

import java.util.Collection;
import java.util.Optional;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    private InventoryIterator inventoryIterator;
    private Optional<Coin> optionalCoin;

    public WithCoinLimitOptimalChangeContext(Inventory inventory, int amount) {
        super(amount);
        inventoryIterator = new InventoryIterator(inventory);
    }

    @Override
    protected void getNextCoin() {
        optionalCoin = inventoryIterator.next();
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
            if( optionalCoin.get().fitsIn(amount) ) {
                addCoin(optionalCoin.get());
            } else {
                getNextCoin();
                while( !optionalCoin.isPresent() ) {
                    if( toReturn.isEmpty() ) {
                        throw new InsufficientCoinageException();
                    }
                    Coin lastCoin = toReturn.remove(toReturn.size()-1);
                    amount += lastCoin.getDenomination();
                    optionalCoin = inventoryIterator.resetToNext(lastCoin);
                }
            }
        }
        return toReturn;
    }
}