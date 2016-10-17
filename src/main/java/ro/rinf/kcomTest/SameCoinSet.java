package ro.rinf.kcomTest;

public class SameCoinSet {
    private final Coin coin;
    private final int amount;

    public SameCoinSet(Coin coin, int amount) {
        this.coin = coin;
        this.amount = amount;
    }

    public boolean isEmpty() {
        return amount == 0;
    }

    public Coin getCoin() {
        return coin;
    }

    public SameCoinSet increment() {
        return new SameCoinSet(coin,amount+1);
    }

    public SameCoinSet decrement() {
        return new SameCoinSet(coin,amount-1);
    }

    public int getAmount() {
        return amount;
    }
}