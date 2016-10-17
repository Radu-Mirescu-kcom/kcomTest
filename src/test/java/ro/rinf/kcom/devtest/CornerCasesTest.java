package ro.rinf.kcom.devtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static ro.rinf.kcomTest.Coin.EIGHT;
//import static ro.rinf.kcomTest.Coin.NINE;
//import static ro.rinf.kcomTest.Coin.TEN;


public class CornerCasesTest {
    private VendingMachine mainVendingMachine;
    private VendingMachine mainVendingMachine2;
    //private VendingMachine mainVendingMachine3;

    @Before
    public void setup() {
        //Properties properties03 = new Properties();
        //properties03.put("10","10");
        //properties03.put("9","10");
        //properties03.put("8","10");
        Properties properties02 = new Properties();
        properties02.put("100","1");
        Properties properties01 = new Properties();
        properties01.put("10","1");
        properties01.put("5","2");
        properties01.put("2","10");
        properties01.put("1","0");
        mainVendingMachine = new VendingMachine(properties01,"");
        mainVendingMachine2 = new VendingMachine(properties02,"");
        //Inventory inventory3 = new Inventory(properties03);
        //mainVendingMachine3 = new VendingMachine();
        //mainVendingMachine3.setInventory(inventory3);
    }

    @Test
    public void test11() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(11);
        assertEquals(result.size(),4);
        Iterator<Coin> it = result.iterator();
        Assert.assertEquals(Coin.FIVE, it.next());
        Assert.assertEquals(Coin.TWO, it.next());
        Assert.assertEquals(Coin.TWO, it.next());
        Assert.assertEquals(Coin.TWO, it.next());
    }

    @Test
    public void test90() {
        try {
            Collection<Coin> result = mainVendingMachine2.getChangeFor(90);
            assertTrue(false);
        } catch( InsufficientCoinageException ice ) {
            assertTrue(true);
        }
    }

    /*
    @Test
    public void test51() {
        Collection<Coin> result = mainVendingMachine3.getChangeFor(51);
        assertEquals(result.size(),6);
        Iterator<Coin> it = result.iterator();
        assertEquals(TEN, it.next());
        assertEquals(NINE, it.next());
        assertEquals(EIGHT, it.next());
        assertEquals(EIGHT, it.next());
        assertEquals(EIGHT, it.next());
        assertEquals(EIGHT, it.next());
    }*/
}
