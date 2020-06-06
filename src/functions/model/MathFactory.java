package functions.model;

import functions.ast.Abs;
import functions.ast.Acos;
import functions.ast.Acosh;
import functions.ast.Acoth;
import functions.ast.Acsch;
import functions.ast.Addition;
import functions.ast.Asech;
import functions.ast.Asin;
import functions.ast.Asinh;
import functions.ast.Atan;
import functions.ast.Atanh;
import functions.ast.Cos;
import functions.ast.Cosh;
import functions.ast.Cot;
import functions.ast.Coth;
import functions.ast.Csc;
import functions.ast.Csch;
import functions.ast.Division;
import functions.ast.E;
import functions.ast.Exponential;
import functions.ast.Logarithm;
import functions.ast.Negate;
import functions.ast.PI;
import functions.ast.Power;
import functions.ast.Product;
import functions.ast.Sec;
import functions.ast.Sech;
import functions.ast.Sin;
import functions.ast.Sinh;
import functions.ast.Sqrt;
import functions.ast.Substract;
import functions.ast.Tan;
import functions.ast.Tanh;

// Centralises Expressions creation while respecting mathematical simplification rules
public class MathFactory {
	public static Constant PI = new PI(), 
			E = new E();
	
	// 0 is the neutral element for addition
	public static Expression addition(Expression first, Expression second) {
		if (Constant.class.isInstance(first) && Constant.class.cast(first).value == 0) 
			return second;	// 0+k = 0
		else if (Constant.class.isInstance(second) && Constant.class.cast(second).value == 0)
			return first; 	// k+0 = 0
		else 
			return new Addition(first, second);
	}
	
	// 0 is semi neutral for substract
	public static Expression substract(Expression first, Expression second) {
		if (Constant.class.isInstance(first) && Constant.class.cast(first).value == 0) 
			return negate(second);	// 0-k = -k
		else if (Constant.class.isInstance(second) && Constant.class.cast(second).value == 0)
			return first;			// k-0 = k
		else 
			return new Substract(first, second);
	}
	
	// 0 is absorbant and 1 is neutral for product
	public static Expression product(Expression first, Expression second) {
		if ((Constant.class.isInstance(first) && Constant.class.cast(first).value == 0)
			|| (Constant.class.isInstance(second) && Constant.class.cast(second).value == 0))
			return constant(0);	// 0*k = 0 or k*0 = 0
		else if (Constant.class.isInstance(first) && Constant.class.cast(first).value == 1) 
			return second;		// 1*k = k
		else if (Constant.class.isInstance(second) && Constant.class.cast(second).value == 1)
			return first;		// k*1 = k
		else 
			return new Product(first, second);
	}
	
	// 0 is semi absorbant and 1 is semi neutral for division
	public static Expression division(Expression first, Expression second) {
		if (Constant.class.isInstance(first) && Constant.class.cast(first).value == 0) 
			return constant(0);	// 0/k = 0
		else if (Constant.class.isInstance(second) && Constant.class.cast(second).value == 1) 
			return first;		// k/1 = k
		else if (first.equals(second))	
			return constant(1); // x/x = 1
		else if (Sin.class.isInstance(first) && Cos.class.isInstance(second) 
				&& Sin.class.cast(first).expression.equals(Cos.class.cast(second).expression))
			return tan(Sin.class.cast(first).expression);	// tan(x) = sin(x)/cos(x)
		else if (Cos.class.isInstance(first) && Sin.class.isInstance(second) 
				&& Cos.class.cast(first).expression.equals(Sin.class.cast(second).expression))
			return cot(Cos.class.cast(first).expression);	// cot(x) = cos(x)/sin(x)
		return new Division(first, second);
	}
	
	public static Expression power(Expression first, Expression second) {
		if (Constant.class.isInstance(first) && Constant.class.cast(first).value == 0) 
			return constant(0);	// 0^k = 0
		else if (Constant.class.isInstance(first) && Constant.class.cast(first).value == 1)
			return constant(1); // 1^k = 1
		else if (Constant.class.isInstance(second) && Constant.class.cast(second).value == 0) 
			return constant(1); // k^0 = 1
		else if (Constant.class.isInstance(second) && Constant.class.cast(second).value == 1)
			return first;		// k^1 = k
		else
			return new Power(first, second);
	}
	
	public static Expression sqrt(Expression e) {
		if (Power.class.isInstance(e)) {
			Power p = Power.class.cast(e);
			if (Constant.class.isInstance(p.right) && Constant.class.cast(p.right).value == 2)
				return p.left;
		}
		return new Sqrt(e);
	}
	
	public static Expression abs(Expression e) {
		if (Abs.class.isInstance(e))
			return e;	// due to idempotence : ||a|| = |a| 
		if (Negate.class.isInstance(e))
			return Negate.class.cast(e).expression; 	// due to evenness |-a| = |a|
		return new Abs(e);
	}
	
	public static Expression exp(Expression e) {
		if (Logarithm.class.isInstance(e)) 
			return Logarithm.class.cast(e).expression;
		return new Exponential(e);
	}
	
	public static Expression ln(Expression e) {
		if (Exponential.class.isInstance(e)) 
			return Exponential.class.cast(e).expression;
		else if (E.class.isInstance(e))
			return constant(1);
		return new Logarithm(e);
	}
	
	public static Expression negate(Expression e) {
		if (Constant.class.isInstance(e) && Constant.class.cast(e).value == 0)	// -0 = 0
			return e;
		else if (Negate.class.isInstance(e))	// --c = c
			return Negate.class.cast(e).expression;
		return new Negate(e);
	}
	
	public static Expression cos(Expression e) {
		if (Negate.class.isInstance(e))
			return Negate.class.cast(e).expression;	 // cos(-x) = cos(x)
		return new Cos(e);
	}
	
	public static Expression sin(Expression e) {
		if (Negate.class.isInstance(e))
			return negate(sin(Negate.class.cast(e).expression)); // sin(-x) = -sin(x)
		return new Sin(e);
	}
	
	public static Expression tan(Expression e) {
		if (Negate.class.isInstance(e))
			return negate(tan(Negate.class.cast(e).expression)); // tan(-x) = -tan(x)
		return new Tan(e);
	}
	
	public static Expression cot(Expression e) {
		if (Negate.class.isInstance(e))
			return negate(cot(Negate.class.cast(e).expression)); // cot(-x) = -cot(x)
		return new Cot(e);
	}
	
	public static Expression sec(Expression e) {
		if (Negate.class.isInstance(e))
			return Negate.class.cast(e).expression; // sec(-x) = sec(x)
		return new Sec(e);
	}
	
	public static Expression csc(Expression e) {
		if (Negate.class.isInstance(e))
			return negate(csc(Negate.class.cast(e).expression)); // csc(-x) = -csc(x)
		return new Csc(e);
	}
	
	public static Expression cosh(Expression e) {
		return new Cosh(e);
	}
	
	public static Expression sinh(Expression e) {
		return new Sinh(e);
	}
	
	public static Expression tanh(Expression e) {
		return new Tanh(e);
	}
	
	public static Expression coth(Expression e) {
		return new Coth(e);
	}
	
	public static Expression sech(Expression e) {
		return new Sech(e);
	}
	
	public static Expression csch(Expression e) {
		return new Csch(e);
	}
	
	public static Expression acos(Expression e) {
		return new Acos(e);
	}
	
	public static Expression asin(Expression e) {
		return new Asin(e);
	}
	
	public static Expression atan(Expression e) {
		return new Atan(e);
	}
	
	public static Expression acot(Expression e) {
		return null;	// TODO
	}
	
	public static Expression asec(Expression e) {
		return null;	// TODO
	}
	
	public static Expression acsc(Expression e) {
		return null;	// TODO
	}
	
	public static Expression acosh(Expression e) {
		return new Acosh(e);
	}
	
	public static Expression asinh(Expression e) {
		return new Asinh(e);
	}
	
	public static Expression atanh(Expression e) {
		return new Atanh(e);
	}
	
	public static Expression acoth(Expression e) {
		return new Acoth(e);
	}
	
	public static Expression asech(Expression e) {
		return new Asech(e);
	}
	
	public static Expression acsch(Expression e) {
		return new Acsch(e);
	}
	
	public static Constant constant(double i) {
		return new Constant(i);
	}
	
	public static Variable variable(String name) {
		return new Variable(name);
	}
	
//	ARC_COSECANT_HYPERBOLIC, ARC_SECANT_HYPERBOLIC, ARC_COTANGENT_HYPERBOLIC,
//	ARC_COS_HYPERBOLIC, ARC_SIN_HYPERBOLIC, ARC_TAN_HYPERBOLIC,
	public static Expression function(String name, Expression child) {
		switch (name) {
			case "abs": return abs(child);
			case "exp": return exp(child);
			case "ln": return ln(child);
			case "sqrt": return sqrt(child);
			case "cos": return cos(child);
			case "sin": return sin(child);
			case "tan": return tan(child);
			case "cot": return cot(child);
			case "sec": return sec(child);
			case "csc": return csc(child);
			case "cosh": return cosh(child);
			case "sinh": return sinh(child);
			case "tanh": return tanh(child);
			case "coth": return coth(child);
			case "sech": return sech(child);
			case "csch": return csch(child);
			case "acos": return acos(child);
			case "asin": return asin(child);
			case "atan": return atan(child);
			case "acot": return acot(child);
			case "asec": return asec(child);
			case "acsc": return acsc(child);
			case "acosh": return acosh(child);
			case "asinh": return asinh(child);
			case "atanh": return atanh(child);
			case "acoth": return acoth(child);
			case "asech": return asech(child);
			case "acsch": return acsch(child);
			default: throw new IllegalArgumentException("Unrecognised function: "+name);
		}
	}

	public static String[] getFunctionNames() {
			return new String[] {
					"abs", "exp", "ln", "sqrt",
					"acosh", "asinh", "atanh",
					"acoth", "asech", "acsch",
					"acos", "asin", "atan",
		            "acot", "asec", "acsc",
					"cosh", "sinh", "tanh",
					"coth", "sech", "csch",
		            "cos", "sin", "tan",
		            "cot", "sec", "csc"
			};
		}
}