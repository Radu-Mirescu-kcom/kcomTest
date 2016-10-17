package ro.rinf.kcomTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicCoinLimitTest {
    private VendingMachine vendingMachine;

    @Before
    public void setup() {
        vendingMachine = new VendingMachine(WithCoinLimitOptimalChangeContext::new);
    }

    @Test
    public void test00() {
        assertEquals(vendingMachine.getOptimalChangeFor(0).size(),0);
    }
}

