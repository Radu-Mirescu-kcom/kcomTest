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
        mainVendingMachine = new VendingMachine(mainProperties,"");
    }

    @Test
    public void test00() {
        assertEquals(mainVendingMachine.getChangeFor(0).size(),0);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test01() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(1);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),ONE);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=200\n2=11\n1=22\n");
    }

    @Test
    public void test02() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(2);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),TWO);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=200\n2=10\n1=23\n");
    }

    @Test
    public void test05() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(5);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),FIVE);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=199\n2=11\n1=23\n");
    }

    @Test
    public void test10() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(10);
        assertEquals(result.size(),1);
        assertEquals(result.iterator().next(),TEN);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=98\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test20() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(20);
        assertEquals(result.size(),2);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),TEN);
        assertEquals(it.next(),TEN);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=97\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test50() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(50);
        assertEquals(result.size(),1);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),FIFTY);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=23\n20=0\n10=99\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test100() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(100);
        assertEquals(result.size(),1);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),HUNDRED);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=10\n50=24\n20=0\n10=99\n5=200\n2=11\n1=23\n");
    }


    @Test
    public void test198() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(198);
        assertEquals(result.size(),9);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),HUNDRED);
        assertEquals(it.next(),FIFTY);
        assertEquals(it.next(),TEN);
        assertEquals(it.next(),TEN);
        assertEquals(it.next(),TEN);
        assertEquals(it.next(),TEN);
        assertEquals(it.next(),FIVE);
        assertEquals(it.next(),TWO);
        assertEquals(it.next(),ONE);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=10\n50=23\n20=0\n10=95\n5=199\n2=10\n1=22\n");
    }
}

