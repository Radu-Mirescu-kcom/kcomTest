package ro.rinf.kcomTest;

import java.util.Collection;
import java.util.function.Function;

public class VendingMachine {
	private final Function<Integer,OptimalChangeContext> factoryMethod;

    public VendingMachine() {
        factoryMethod = NoCoinLimitOptimalChangeContext::new;
    }

    public VendingMachine(Function<Integer,OptimalChangeContext> factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

	public Collection<Coin> getOptimalChangeFor(int pence) {
		return factoryMethod.apply(pence).getOptimalChangeFor();
	}
}