package algebra.concrete.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algebra.concrete.MultivariatePolynomial;
import algebra.concrete.MultivariatePolynomial.MultivariateMonomial;
import algebra.concrete.MultivariatePolynomial.Variable;
import algebra.concrete.Real;
import algebra.factory.UnitaryRingFactory;
import algebra.fields.DivisionElement;

/**
 * Not done.
 * Errors:
 * - if u parse a multivariate monomial as "xy^23", the code will badly check the power of x and y. U need
 * to design a full blown interpreter i guess to keep track of tokens and state.
 *
 * @param <K>
 */
public class MultivariatePolynomialFactory<K extends DivisionElement<K>> implements UnitaryRingFactory<MultivariatePolynomial<K>> {
	private final MultivariatePolynomial<K> ZERO, ONE;
	private final UnitaryRingFactory<K> factory;
	
	public MultivariatePolynomialFactory(UnitaryRingFactory<K> factory) {
		this.factory = factory;
		List<MultivariateMonomial<K>> zero = new ArrayList<>(),
				one = new ArrayList<>();
		List<Variable> vars = new ArrayList<>();
		Variable x = new Variable("x", 0);
		vars.add(x);
		zero.add(new MultivariateMonomial<>(factory.getAdditiveIdentity(), vars));
		ZERO = new MultivariatePolynomial<>(zero);
		one.add(new MultivariateMonomial<>(factory.getMultiplicativeIdentity(), vars));
		ONE = new MultivariatePolynomial<>(one);
	}
	
	@Override
	public MultivariatePolynomial<K> getAdditiveIdentity() {
		return ZERO;
	}

	@Override
	public MultivariatePolynomial<K> getMultiplicativeIdentity() {
		return ONE;
	}
	
	@Override
	public MultivariatePolynomial<K> parse(String input) {
		String[] monoms = input.split("(\\+|-)"); 
		System.out.println(Arrays.toString(monoms));
		List<MultivariateMonomial<K>> monomials = new ArrayList<>();
		for (String monom : monoms)
			monomials.add(parseMonom(monom));
		return new MultivariatePolynomial<>(monomials);
	}
	
	public MultivariateMonomial<K> parseMonom(String monom) {
		if (isInt(monom))
			return new MultivariateMonomial<>(factory.parse(monom), Arrays.asList(new Variable("x", 0)));
		K coefficient;
		int start = 0;
		if (Character.isDigit(monom.charAt(0)))
			coefficient = factory.parse(monom.substring(0, start = coefficientEndIndex(monom)));
		else 
			coefficient = factory.getMultiplicativeIdentity();
		List<Variable> variables = parseVars(monom.substring(start));
		return new MultivariateMonomial<>(coefficient, variables);
	}
	
	public boolean isInt(String string) {
		try { Integer.parseInt(string); }
		catch (Exception e) { return false; }
		return true;
	}
	
	public List<Variable> parseVars(String vars) {
		if (!vars.contains("^"))
			return Arrays.asList(new Variable(vars, 1));
		List<Variable> variables = new ArrayList<>();
		String name = "", power = "";
		int index = 0;
		char c;
		while (index < vars.length()) {
			c = vars.charAt(index);
			if (Character.isAlphabetic(c)) { 
				name = Character.toString(c);
				index++;
				continue;
			}
			if (c != '^')
				throw new IllegalArgumentException("Expected a ^");
			index++;	// skip power symbol
			while (index < vars.length() && Character.isDigit(c = vars.charAt(index))) { 
				power += c;
				index++;
			}
			variables.add(new Variable(name, Integer.parseInt(power)));
			name = "";
			power = "";
		}
		return variables;
	}
	
	public int coefficientEndIndex(String monom) {
		int index = 0;
		char c;
		while (index < monom.length() 
				&& (Character.isDigit(c = monom.charAt(index)) || c == '.'))
			index++;
		return index;
	}

	public static final class Type {
		
		public static MultivariatePolynomialFactory<Real> ofReals() {
			return new MultivariatePolynomialFactory<>(RealFactory.getInstance());
		}
	}
	
	public static void main(String[] args) {
//		RealFactory rf = RealFactory.getInstance();
		MultivariatePolynomialFactory<Real> p = MultivariatePolynomialFactory.Type.ofReals();
//		System.out.println(p.ZERO);
		System.out.println(p.parse("0"));
		System.out.println(p.parse("1+x"));
		System.out.println(p.parse("0+x-9.9x^2"));
	}
}
