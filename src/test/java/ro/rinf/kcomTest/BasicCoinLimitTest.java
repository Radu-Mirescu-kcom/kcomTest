package ro.rinf.kcomTest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static ro.rinf.kcomTest.Coin.FIFTY;
import static ro.rinf.kcomTest.Coin.FIVE;
import static ro.rinf.kcomTest.Coin.HUNDRED;
import static ro.rinf.kcomTest.Coin.ONE;
import static ro.rinf.kcomTest.Coin.TEN;
import static ro.rinf.kcomTest.Coin.TWO;

public class BasicCoinLimitTest {
    private VendingMachine mainVendingMachine;
    private WithCoinLimitBuilder mainContextBuilder;

    @Before
    public void setup() {
        Properties mainProperties = new Properties();
        mainProperties.put("100","11");
        mainProperties.put("50","24");
        mainProperties.put("20","0");
        mainProperties.put("10","99");
        mainProperties.put("5","200");
        mainProperties.put("2","11");
        mainProperties.put("1","23");
        mainContextBuilder = new WithCoinLimitBuilder(mainProperties);
        mainVendingMachine = new VendingMachine(mainContextBuilder::makeContext);
    }

    @Test
    public void test00() {
        assertEquals(mainVendingMachine.getOptimalChangeFor(0).size(),0);
    }

    @Test
    public void test01() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(1);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),ONE);
    }

    @Test
    public void test02() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(2);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),TWO);
    }

    @Test
    public void test05() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(5);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),FIVE);
    }

    @Test
    public void test10() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(10);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),TEN);
    }

    @Test
    public void test20() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(20);
        assertEquals(result.size(),2);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),TEN);
        assertEquals(it.next(),TEN);
    }

    @Test
    public void test50() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(50);
        assertEquals(result.size(),1);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),FIFTY);
    }

    @Test
    public void test100() {
        Collection<Coin> result = mainVendingMachine.getOptimalChangeFor(100);
        assertEquals(result.size(),1);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),HUNDRED);
    }
}

