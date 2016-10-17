package ro.rinf.kcom.devtest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HundredTest {
    private VendingMachine vendingMachine;

    @Before
    public void setup() {
        vendingMachine = new VendingMachine();
    }

    Coin[][] ones = {
        {},
        {Coin.ONE},
        {Coin.TWO},
        {Coin.TWO, Coin.ONE},
        {Coin.TWO, Coin.TWO},
        {Coin.FIVE},
        {Coin.FIVE, Coin.ONE},
        {Coin.FIVE, Coin.TWO},
        {Coin.FIVE, Coin.TWO, Coin.ONE},
        {Coin.FIVE, Coin.TWO, Coin.TWO}
    };

    Coin [][] dozens = {
        {},
        {Coin.TEN},
        {Coin.TWENTY},
        {Coin.TWENTY, Coin.TEN},
        {Coin.TWENTY, Coin.TWENTY},
        {Coin.FIFTY},
        {Coin.FIFTY, Coin.TEN},
        {Coin.FIFTY, Coin.TWENTY},
        {Coin.FIFTY, Coin.TWENTY, Coin.TEN},
        {Coin.FIFTY, Coin.TWENTY, Coin.TWENTY}
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
