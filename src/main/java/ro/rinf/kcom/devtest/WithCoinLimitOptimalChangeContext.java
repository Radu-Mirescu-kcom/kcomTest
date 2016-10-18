package ro.rinf.kcom.devtest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class WithCoinLimitOptimalChangeContext extends NoCoinLimitOptimalChangeContext {
    private InventoryIterator inventoryIterator;
    private Optional<Coin> optionalCoin;
    private List<Collection<Coin>> candidates = new ArrayList<>();
    private Coin lastCoin;

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

    private void tryOtherPath() {
        lastCoin = toReturn.remove(toReturn.size()-1);
        amount += lastCoin.getDenomination();
        optionalCoin = inventoryIterator.resetToNext(lastCoin);
    }

    @Override
    public Collection<Coin> getChangeFor() {
        boolean working = true;
        getNextCoin();
        while(working) {
            while(working && needsCoin()) {
                if (optionalCoin.get().fitsIn(amount)) {
                    addCoin(optionalCoin.get());
                } else {
                    getNextCoin();
                    while (!optionalCoin.isPresent()) {
                        if (toReturn.isEmpty()) {
                            if (candidates.isEmpty()) {
                                throw new InsufficientCoinageException();
                            } else {
                                working = false;
                                break;
                            }
                        } else {
                            tryOtherPath();
                        }
                    }
                }
            }
            candidates.add(toReturn);
            working = false;
        }
        return toReturn;
    }
}