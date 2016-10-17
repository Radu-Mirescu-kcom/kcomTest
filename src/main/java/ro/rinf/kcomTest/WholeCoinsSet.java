package ro.rinf.kcomTest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static ro.rinf.kcomTest.Coin.FIFTY;
import static ro.rinf.kcomTest.Coin.FIVE;
import static ro.rinf.kcomTest.Coin.HUNDRED;
import static ro.rinf.kcomTest.Coin.ONE;
import static ro.rinf.kcomTest.Coin.TEN;
import static ro.rinf.kcomTest.Coin.TWENTY;
import static ro.rinf.kcomTest.Coin.TWO;

public class WholeCoinsSet {
    private final List<SameCoinSet> coinSets;

    private final Map<String,Coin> denomination2coinMap = new HashMap<>();

    public WholeCoinsSet() {
        coinSets = new ArrayList<>();
        denomination2coinMap.put("100",HUNDRED);
        denomination2coinMap.put("50",FIFTY);
        denomination2coinMap.put("20",TWENTY);
        denomination2coinMap.put("10",TEN);
        denomination2coinMap.put("5",FIVE);
        denomination2coinMap.put("2",TWO);
        denomination2coinMap.put("1",ONE);
    }

    public WholeCoinsSet(Properties properties) {
        this();
        Enumeration pNames = properties.propertyNames();
        while(pNames.hasMoreElements()) {
            String pName = pNames.nextElement().toString();
            Coin coin = denomination2coinMap.get(pName);
            Integer count = Integer.parseInt(properties.getProperty(pName));
            coinSets.add(new SameCoinSet(coin,count));
        }
    }
}