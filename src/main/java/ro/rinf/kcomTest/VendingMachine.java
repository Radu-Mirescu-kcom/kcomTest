package ro.rinf.kcomTest;

import java.util.Collection;
import java.util.function.Function;

public class VendingMachine {
	private Function<Integer,OptimalChangeContext> factoryMethod = NoCoinLimitOptimalChangeContext::new;

    public VendingMachine(Function<Integer,OptimalChangeContext> factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

	public Collection<Coin> getOptimalChangeFor(int pence) {
		return factoryMethod.apply(pence).getOptimalChangeFor();
	}
}