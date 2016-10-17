package ro.rinf.kcom.devtest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Inventory {
    private final List<SameCoinSet> coinSets;

    private static final Map<String,Coin> denomination2coinMap = new HashMap<>();

    static {
        denomination2coinMap.put("100", Coin.HUNDRED);
        denomination2coinMap.put("50", Coin.FIFTY);
        denomination2coinMap.put("20", Coin.TWENTY);
        denomination2coinMap.put("10", Coin.TEN);
        denomination2coinMap.put("5", Coin.FIVE);
        denomination2coinMap.put("2", Coin.TWO);
        denomination2coinMap.put("1", Coin.ONE);
    }

    public Inventory() {
        coinSets = new ArrayList<>();
    }

    public Inventory(Inventory source) {
        this();
        coinSets.addAll(source.coinSets);
    }

    public Inventory(Properties properties) {
        this();
        Enumeration pNames = properties.propertyNames();
        while(pNames.hasMoreElements()) {
            String pName = pNames.nextElement().toString();
            Coin coin = denomination2coinMap.get(pName);
            Integer count = Integer.parseInt(properties.getProperty(pName));
            coinSets.add(new SameCoinSet(coin,count));
        }
        coinSets.sort( (c1,c2) -> c2.getCoin().getDenomination() - c1.getCoin().getDenomination() );
    }

    public List<SameCoinSet> getCoinSets() {
        return coinSets;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(SameCoinSet coinSet:coinSets) {
            stringBuilder.append(String.format("%d=%d\n",
                coinSet.getCoin().getDenomination(),coinSet.getAmount())
            );
        }

        return stringBuilder.toString();
    }
}