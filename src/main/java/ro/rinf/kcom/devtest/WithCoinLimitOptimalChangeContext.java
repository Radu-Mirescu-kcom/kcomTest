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

    private int bestTrialSoFar() {
        return candidates.stream().map( c -> c.size()).reduce(Integer::min).orElse(Integer.MAX_VALUE);
    }

    private boolean tryOtherPath() {
        if( toReturn.isEmpty() ) {
            return false;
        }
        lastCoin = toReturn.remove(toReturn.size()-1);
        amount += lastCoin.getDenomination();
        optionalCoin = inventoryIterator.resetToNext(lastCoin);
        if( !optionalCoin.isPresent() ) {
            return false;
        }
        if( !candidates.isEmpty()) {
            int bestExpectedSize = toReturn.size() + amount/optionalCoin.get().getDenomination();
            if( amount % amount/optionalCoin.get().getDenomination() > 0 ) {
                bestExpectedSize++;
            }
            return bestExpectedSize <= bestTrialSoFar();
        } else {
            return true;
        }
    }

    public Collection<Coin> bestCandidate() {
        int bestLength = bestTrialSoFar();
        return candidates.stream().filter( coins -> coins.size() == bestLength)
            .findFirst().orElse(new ArrayList<>());
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
                            working = tryOtherPath();
                        }
                    }
                }
            }
            candidates.add(new ArrayList<>(toReturn));
            working = tryOtherPath();
        }
        return bestCandidate();
    }
}