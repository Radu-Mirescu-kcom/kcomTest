package ro.rinf.kcomTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static ro.rinf.kcomTest.Coin.FIFTY;
import static ro.rinf.kcomTest.Coin.FIVE;
import static ro.rinf.kcomTest.Coin.ONE;
import static ro.rinf.kcomTest.Coin.TEN;
import static ro.rinf.kcomTest.Coin.TWENTY;
import static ro.rinf.kcomTest.Coin.TWO;

public class HundredTest {
    private VendingMachine vendingMachine;

    @Before
    public void setup() {
        vendingMachine = new VendingMachine();
    }

    Coin[][] ones = {
        {},
        {ONE},
        {TWO},
        {TWO,ONE},
        {TWO,TWO},
        {FIVE},
        {FIVE,ONE},
        {FIVE,TWO},
        {FIVE,TWO,ONE},
        {FIVE,TWO,TWO}
    };

    Coin [][] dozens = {
        {},
        {TEN},
        {TWENTY},
        {TWENTY,TEN},
        {TWENTY,TWENTY},
        {FIFTY},
        {FIFTY,TEN},
        {FIFTY,TWENTY},
        {FIFTY,TWENTY,TEN},
        {FIFTY,TWENTY,TWENTY}
    };

    private boolean collectionsAreEquals(Iterator<Coin> it1,Iterator<Coin> it2) {
        if( it1.hasNext() ) {
            if( it2.hasNext() ) {
                if( it1.next() != it2.next() ) {
                    return false;
                } else {
                    return collectionsAreEquals(it1,it2);
                }
            } else {
                return false;
            }
        } else {
            return !it2.hasNext();
        }
    }

    @Test
    public void testOneHundred() {
        for(int p=0;p<100;p++) {
            ArrayList<Coin> dozenCollection = new ArrayList<>(Arrays.asList(dozens[(p/10)%10]));
            List<Coin> onesCollection = Arrays.asList(ones[p%10]);
            dozenCollection.addAll(onesCollection);
            Collection<Coin> asInOrdinaryLifeNb = dozenCollection;
            Collection<Coin> computedCollection = vendingMachine.getOptimalChangeFor(p);
            assertTrue( collectionsAreEquals(asInOrdinaryLifeNb.iterator(), computedCollection.iterator()));
        }
    }
}
