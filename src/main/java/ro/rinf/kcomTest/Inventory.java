package ro.rinf.kcomTest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import static ro.rinf.kcomTest.Coin.EIGHT;
import static ro.rinf.kcomTest.Coin.FIFTY;
import static ro.rinf.kcomTest.Coin.FIVE;
import static ro.rinf.kcomTest.Coin.HUNDRED;
//import static ro.rinf.kcomTest.Coin.NINE;
import static ro.rinf.kcomTest.Coin.ONE;
import static ro.rinf.kcomTest.Coin.TEN;
import static ro.rinf.kcomTest.Coin.TWENTY;
import static ro.rinf.kcomTest.Coin.TWO;

public class Inventory {
    private final List<SameCoinSet> coinSets;

    private final static Map<String,Coin> denomination2coinMap = new HashMap<>();

    static {
        denomination2coinMap.put("100",HUNDRED);
        denomination2coinMap.put("50",FIFTY);
        denomination2coinMap.put("20",TWENTY);
        denomination2coinMap.put("10",TEN);
        //denomination2coinMap.put("9",NINE);
        //denomination2coinMap.put("8",EIGHT);
        denomination2coinMap.put("5",FIVE);
        denomination2coinMap.put("2",TWO);
        denomination2coinMap.put("1",ONE);
    }

    public Inventory() {
        coinSets = new ArrayList<>();
    }

    public List<SameCoinSet> getCoinSets() {
        return coinSets;
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(SameCoinSet coinSet:coinSets) {
            stringBuilder.append(String.format("%d=%d\n",
                coinSet.getCoin().getDenomination(),coinSet.getAmount())
            );
        }

        return stringBuilder.toString();
    }

    public Inventory clone() {
        Inventory toReturn = new Inventory();
        toReturn.getCoinSets().addAll(coinSets);
        return toReturn;
    }
}