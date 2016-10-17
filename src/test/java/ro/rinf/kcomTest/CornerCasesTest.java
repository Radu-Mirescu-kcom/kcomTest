package ro.rinf.kcomTest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static ro.rinf.kcomTest.Coin.FIVE;
import static ro.rinf.kcomTest.Coin.TWO;

public class CornerCasesTest {
    private VendingMachine mainVendingMachine;

    @Before
    public void setup() {
        Properties properties01 = new Properties();
        properties01.put("10","1");
        properties01.put("5","2");
        properties01.put("2","10");
        properties01.put("1","0");
        Inventory inventory = new Inventory(properties01);
        mainVendingMachine = new VendingMachine();
        mainVendingMachine.setInventory(inventory);
    }

    @Test
    public void test11() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(11);
        assertEquals(result.size(),4);
        Iterator<Coin> it = result.iterator();
        assertEquals(it.next(),FIVE);
        assertEquals(it.next(),TWO);
        assertEquals(it.next(),TWO);
        assertEquals(it.next(),TWO);
    }
}
