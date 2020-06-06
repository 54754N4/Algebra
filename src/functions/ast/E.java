package functions.ast;

import functions.model.Constant;

public class E extends Constant {

	public E() {
		super(Math.E);
	}
	
	@Override
	public String toString() {
		return "E";
	}
}
