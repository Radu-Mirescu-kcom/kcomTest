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
}