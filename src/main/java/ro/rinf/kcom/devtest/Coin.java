package ro.rinf.kcom.devtest;

import java.util.Arrays;
import java.util.Optional;

public enum Coin {
	HUNDRED(100), FIFTY(50), TWENTY(20), TEN(10), NINE(9,false), EIGHT(8,false), FIVE(5), TWO(2), ONE(1);

	private final int denomination;

	private final boolean standard;

	Coin(int denomination) {
		this.denomination = denomination;
		standard = true;
	}

	Coin(int denomination,boolean standard) {
		this.denomination = denomination;
		this.standard = standard;
	}

	public boolean isStandard() {
		return standard;
	}

	public int getDenomination() {
		return denomination;
	}

	public boolean fitsIn(int amount) {
		return denomination <= amount;
	}

	public static Coin forName(String name) {
		Optional<Coin> toReturn = Arrays.asList(values()).stream()
			.filter( c -> String.format("%d",c.denomination).equals(name))
			.findFirst();
		if( !toReturn.isPresent() ) {
			throw new UnexpectedException(String.format("Unable to find the coin with denomination %s!",name));
		}
		return toReturn.get();
	}
}
