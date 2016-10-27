package ro.rinf.kcom.devtest;

import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class VendingMachineNG {
    private boolean standardCoins = false;

    @Setter
    private Optional<Consumer<Properties>> saveListener = Optional.empty();

    private AtomicLong versionCounter = new AtomicLong(1);
    private TreeMap<Coin, Integer> inventory = new TreeMap<>(
        (c1, c2) -> c1.getDenomination() - c2.getDenomination()
    );

    public VendingMachineNG(boolean standardCoins) {
        this.standardCoins = standardCoins;
    }

    public VendingMachineNG(Properties properties) {
        Enumeration enumNames = properties.propertyNames();
        while (enumNames.hasMoreElements()) {
            String sKey = enumNames.nextElement().toString();
            Coin key = Coin.forName(sKey);
            inventory.put(key, Integer.parseInt(properties.getProperty(sKey)));
        }
    }

    class ChangeContext {
        int amount;
        TreeMap<Coin, Integer> inventory;
        List<Coin> currentSet = new ArrayList<>();
        Optional<List<Coin>> currentSolution = Optional.empty();
        Optional<Coin> coin = Optional.empty();
        boolean takeTheSameCoin = true;
        boolean mustTryFallbackAgain = false;
        long version = versionCounter.get();
        int forbiddenQuantity = Integer.MAX_VALUE;

        private ChangeContext(int amount, TreeMap<Coin, Integer> inventory) {
            this.amount = amount;
            this.inventory = inventory;
        }

        private Collection<Coin> getOptimalChange() {
            boolean searchForOptimal = true;

            coin = Optional.of(inventory.lastKey());
            while (searchForOptimal) {
                while (stillWorkToDo()) {
                    setCoinCandidate();
                    if (noCoin() && currentSet.isEmpty()) {
                        searchForOptimal = false;
                        break;
                    } else if (noCoin()) {
                        fallBack();
                    } else {
                        tryToAddCoinToChange();
                    }
                }
                if (searchForOptimal) {
                    collectSolution();
                    if (currentSet.isEmpty()) {
                        break;
                    } else {
                        fallBack();
                    }
                }
            }
            return getOptimalSolution();
        }

        boolean noChangeMeantime() {
            return version == versionCounter.get();
        }

        private boolean stillWorkToDo() {
            return amount > 0;
        }

        private void setCoinCandidate() {
            if (mustTryFallbackAgain) {
                mustTryFallbackAgain = false;
                coin = Optional.empty();
            } else if (takeTheSameCoin) {
                takeTheSameCoin = false;
            } else {
                coin = Optional.ofNullable(inventory.lowerKey(coin.get()));
            }
        }

        private boolean noCoin() {
            return !coin.isPresent();
        }

        private Collection<Coin> getOptimalSolution() {
            if (currentSolution.isPresent()) {
                return currentSolution.get();
            }
            throw new InsufficientCoinageException();
        }

        private void collectSolution() {
            if (!currentSolution.isPresent() || currentSet.size() < currentSolution.get().size()) {
                currentSolution = Optional.of(new ArrayList<>(currentSet));
            }
        }

        private void fallBack() {
            mustTryFallbackAgain = shouldFallBackAgain(doFallBack());
        }

        private Coin doFallBack() {
            Coin c = currentSet.remove(currentSet.size() - 1);
            amount += c.getDenomination();
            forbiddenQuantity = c.getDenomination();
            inventory.put(c, inventory.get(c) + 1);
            coin = Optional.of(c);
            takeTheSameCoin = false;
            return c;
        }

        private boolean shouldFallBackAgain(Coin c) {
            Coin nextCoin = inventory.lowerKey(c);
            if (nextCoin == null ) {
                return true;
            } else {
                return noHopeForABetterSolution(nextCoin);
            }
        }

        private int expectedMinSize(Coin nextCoin) {
            int expectedMinSize = currentSet.size() + (amount / nextCoin.getDenomination());
            if (amount % nextCoin.getDenomination() > 0) {
                expectedMinSize++;
            }
            return expectedMinSize;
        }

        private boolean noHopeForABetterSolution(Coin nextCoin) {
            if (!currentSolution.isPresent()) {
                return false;
            }

            return expectedMinSize(nextCoin) >= currentSolution.get().size();
        }

        private boolean stillCoinInInventory() {
            return inventory.get(coin.get()) > 0;
        }

        private boolean coinFitsInAmount() {
            return coin.get().fitsIn(amount);
        }

        private void takeOneCoin() {
            Coin c = coin.get();
            amount -= c.getDenomination();
            forbiddenQuantity -= c.getDenomination();
            inventory.put(c, inventory.get(c) - 1);
            currentSet.add(c);
        }

        private void tryToAddCoinToChange() {
            if (stillCoinInInventory() && coinFitsInAmount() && coinIsNotForbidden()) {
                takeOneCoin();
                takeTheSameCoin = true;
            } else {
                takeTheSameCoin = false;
            }
        }

        private boolean coinIsNotForbidden() {
            return coin.get().getDenomination() != forbiddenQuantity;
        }
    }

    private Properties coinsToProperties(TreeMap<Coin, Integer> coinInventory) {
        Properties toReturn = new Properties();
        NavigableSet<Coin> coinInterator = coinInventory.descendingKeySet();
        coinInterator.forEach(c -> {
            if (coinInventory.get(c) > 0) {
                toReturn.put(String.format("%d", c.getDenomination()), coinInventory.get(c));
            }
        });
        return toReturn;
    }

    public Collection<Coin> getChangeFor(int pence) {
        ChangeContext cc = new ChangeContext(pence, new TreeMap<>(inventory));
        Collection<Coin> trial = cc.getOptimalChange();
        boolean tryAgain = false;
        if (cc.noChangeMeantime()) {
            synchronized (inventory) {
                if (cc.noChangeMeantime()) {
                    trial.forEach(c -> inventory.put(c, inventory.get(c) - 1));
                    versionCounter.incrementAndGet();
                    if (saveListener.isPresent()) {
                        saveListener.get().accept(coinsToProperties(inventory));
                    }
                } else {
                    tryAgain = true;
                }
            }
            if (tryAgain) {
                return getChangeFor(pence);
            } else {
                return trial;
            }
        } else {
            return getChangeFor(pence);
        }
    }

    public Collection<Coin> getOptimalChangeFor(int pence) {
        TreeMap<Coin, Integer> noLimitInventory = new TreeMap<>(inventory.comparator());
        Arrays.asList(Coin.values()).forEach(
            c -> {
                if (!standardCoins || c.isStandard()) {
                    noLimitInventory.put(c, Integer.MAX_VALUE);
                }
            }
        );
        ChangeContext cc = new ChangeContext(pence, noLimitInventory);
        return cc.getOptimalChange();
    }
}