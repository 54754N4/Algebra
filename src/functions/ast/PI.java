package functions.ast;

import functions.model.Constant;

public class PI extends Constant {

	public PI() {
		super(Math.PI);
	}

	@Override
	public String toString() {
		return "PI";
	}
}
