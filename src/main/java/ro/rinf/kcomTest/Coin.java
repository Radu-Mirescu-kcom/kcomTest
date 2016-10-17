package ro.rinf.kcomTest;

public enum Coin {
	HUNDRED(100), FIFTY(50), TWENTY(20), TEN(10), FIVE(5), TWO(2), ONE(1);

	private final int denomination;

	Coin(int denomination) {
		this.denomination = denomination;
	}

	public int getDenomination() {
		return denomination;
	}
}
