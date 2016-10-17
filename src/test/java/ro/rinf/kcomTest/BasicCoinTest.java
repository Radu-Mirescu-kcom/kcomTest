package ro.rinf.kcomTest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class BasicCoinTest {
	private VendingMachine vendingMachine;

	@Before
	void setup() {
		vendingMachine = new VendingMachine();
	}

	@Test
	void testFor00() {
		assertEquals(vendingMachine.getOptimalChangeFor(0).size(),0);
	}

	@Test
	void testFor01() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(1);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.ONE);
	}

	@Test
	void testFor02() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(2);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.TWO);
	}

	@Test
	void testFor03() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(3);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.ONE);
	}

	@Test
	void testFor04() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(4);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.TWO);
	}

	@Test
	void testFor05() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(5);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.FIVE);
	}

	@Test
	void testFor06() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(6);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.ONE);
	}

	@Test
	void testFor07() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(7);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.TWO);
	}

	@Test
	void testFor08() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(8);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.ONE);
	}

	@Test
	void testFor09() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(9);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.TWO);
	}

	@Test
	void testFor10() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(10);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.TEN);
	}

	@Test
	void testFor20() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(20);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.TWENTY);
	}

	@Test
	void testFor30() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(30);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TEN);
	}

	@Test
	void testFor40() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(40);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TWENTY);
	}

	@Test
	void testFor50() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(50);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.FIFTY);
	}

	@Test
	void testFor60() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(60);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TEN);
	}

	@Test
	void testFor70() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(70);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TWENTY);
	}

	@Test
	void testFor80() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(80);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TEN);
	}

	@Test
	void testFor90() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(90);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TWENTY);
	}

	@Test
	void testFor100() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(100);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.HUNDRED);
	}
}
