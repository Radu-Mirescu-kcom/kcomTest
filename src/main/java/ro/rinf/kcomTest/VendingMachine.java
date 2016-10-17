package ro.rinf.kcomTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class VendingMachine {
	public Collection<Coin> getOptimalChangeFor(int pence) {
		List<Coin> toReturn = new ArrayList<>();
		Iterator<Coin> it = Arrays.asList(Coin.values()).iterator();
		Coin coin = it.next();
		while(pence > 0) {
			if( coin.fitsIn(pence) ) {
				toReturn.add(coin);
				pence -= coin.getDenomination();
			} else {
				coin = it.next();
			}
		}
		return toReturn;
	}
}