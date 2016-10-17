package ro.rinf.kcomTest;

import java.util.Collection;

public class VendingMachine {
    private Inventory inventory;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

	public Collection<Coin> getOptimalChangeFor(int pence) {
		return new NoCoinLimitOptimalChangeContext(pence).getChangeFor();
	}

    public Collection<Coin> getChangeFor(int pence) {
        return new WithCoinLimitOptimalChangeContext(inventory,pence).getChangeFor();
    }
}