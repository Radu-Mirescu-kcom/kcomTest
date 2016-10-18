package ro.rinf.kcom.devtest;

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

	public int getDenomination() {
		return denomination;
	}

	public boolean fitsIn(int amount) {
		return denomination <= amount;
	}

    public boolean isStandard() {
        return standard;
    }
}
