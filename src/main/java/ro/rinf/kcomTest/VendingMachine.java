package ro.rinf.kcomTest;

import java.util.Collection;

public class VendingMachine {
	public Collection<Coin> getOptimalChangeFor(int pence) {
		return new SimpleOptimalChangeContext(pence).getOptimalChangeFor();
	}
}