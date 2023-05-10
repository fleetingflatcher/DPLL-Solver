package xyz.Engine;


enum BooleanValue {
	FALSE,
	TRUE,
	VAR
}

public class Variable {
	static final int ALPHA_LEN = 'z' - 'a';
	public Variable(int designation) {
		this.designation = designation;
		value = BooleanValue.VAR;
	}
	public final int designation;
	BooleanValue value;


	public String toString() {
		if (designation < ALPHA_LEN)
			return String.valueOf((char)('a' + designation - 1));
		int mod = (designation - 1) % ALPHA_LEN;
		int div = (designation - 1) / ALPHA_LEN;
		return String.valueOf((char)('a' + mod)) + String.valueOf(div);
	}
}
