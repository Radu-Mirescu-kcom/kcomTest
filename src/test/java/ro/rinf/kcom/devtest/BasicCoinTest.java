package ro.rinf.kcom.devtest;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class BasicCoinTest {
	private VendingMachineNG vendingMachine;

	@Before
	public void setup() {
		vendingMachine = new VendingMachineNG(true);
	}

	@Test
	public void testFor00() {
		assertEquals(vendingMachine.getOptimalChangeFor(0).size(),0);
	}

	@Test
	public void testFor01() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(1);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.ONE);
	}

	@Test
	public void testFor02() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(2);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.TWO);
	}

	@Test
	public void testFor03() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(3);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.ONE);
	}

	@Test
	public void testFor04() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(4);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.TWO);
	}

	@Test
	public void testFor05() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(5);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.FIVE);
	}

	@Test
	public void testFor06() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(6);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.ONE);
	}

	@Test
	public void testFor07() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(7);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.TWO);
	}

	@Test
	public void testFor08() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(8);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.ONE);
	}

	@Test
	public void testFor09() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(9);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIVE);
		assertEquals(it.next(),Coin.TWO);
		assertEquals(it.next(),Coin.TWO);
	}

	@Test
	public void testFor10() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(10);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.TEN);
	}

	@Test
	public void testFor20() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(20);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.TWENTY);
	}

	@Test
	public void testFor30() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(30);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TEN);
	}

	@Test
	public void testFor40() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(40);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TWENTY);
	}

	@Test
	public void testFor50() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(50);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.FIFTY);
	}

	@Test
	public void testFor60() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(60);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TEN);
	}

	@Test
	public void testFor70() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(70);
		assertEquals(changeAmount.size(),2);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TWENTY);
	}

	@Test
	public void testFor80() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(80);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TEN);
	}

	@Test
	public void testFor90() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(90);
		assertEquals(changeAmount.size(),3);
		Iterator<Coin> it = changeAmount.iterator();
		assertEquals(it.next(),Coin.FIFTY);
		assertEquals(it.next(),Coin.TWENTY);
		assertEquals(it.next(),Coin.TWENTY);
	}

	@Test
	public void testFor100() {
		Collection changeAmount = vendingMachine.getOptimalChangeFor(100);
		assertEquals(changeAmount.size(),1);
		assertEquals(changeAmount.iterator().next(),Coin.HUNDRED);
	}
}
