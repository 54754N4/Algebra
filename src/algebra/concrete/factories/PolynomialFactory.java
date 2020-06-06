package algebra.concrete.factories;

import java.util.ArrayList;
import java.util.List;

import algebra.concrete.Polynomial;
import algebra.concrete.Polynomial.Monomial;
import algebra.concrete.Real;
import algebra.factory.UnitaryRingFactory;
import algebra.fields.DivisionElement;

public class PolynomialFactory<K extends DivisionElement<K>> implements UnitaryRingFactory<Polynomial<K>> {
	private final Polynomial<K> ZERO, ONE;
	private final UnitaryRingFactory<K> factory;
	
	public PolynomialFactory(UnitaryRingFactory<K> factory) {
		this.factory = factory;
		List<Monomial<K>> zero = new ArrayList<>(),
				one = new ArrayList<>();
		zero.add(new Monomial<>(factory.getAdditiveIdentity(), 0));
		ZERO = new Polynomial<>(zero, this, factory);
		one.add(new Monomial<>(factory.getMultiplicativeIdentity(), 0));
		ONE = new Polynomial<>(one, this, factory);
	}
	
	@Override
	public Polynomial<K> getAdditiveIdentity() {
		return ZERO;
	}

	@Override
	public Polynomial<K> getMultiplicativeIdentity() {
		return ONE;
	}
	
	@Override
	public Polynomial<K> parse(String input) {
		String[] monoms = input.split("(?=(\\+|-))");
		List<Monomial<K>> monomials = new ArrayList<>();
		for (String monom : monoms)
			monomials.add(parseMonom(monom));
		return new Polynomial<>(monomials, this, factory);
	}
	
	public Monomial<K> parseMonom(String monom) {
		if (isInt(monom))
			return new Monomial<>(factory.parse(monom), 0);
		K  coefficient, sign = factory.getMultiplicativeIdentity();
		boolean negative = monom.startsWith("-");
		if (negative) {
			monom = monom.substring(1);
			sign = sign.times(-1);
		}
		if (Character.isDigit(monom.charAt(0)))
			coefficient = factory.parse(monom.substring(0, coefficientEndIndex(monom)));
		else 
			coefficient = factory.getMultiplicativeIdentity();
		coefficient = coefficient.times(sign);
		if (!monom.contains("^"))
			return new Monomial<>(coefficient, 1);
		return new Monomial<>(
				coefficient, 
				Integer.parseInt(monom.substring(monom.indexOf('^')+1)));
	}
	
	public int coefficientEndIndex(String monom) {
		int index = 0;
		char c;
		while (index < monom.length() 
				&& (Character.isDigit(c = monom.charAt(index)) || c == '.'))
			index++;
		return index;
	}
	
	public boolean isInt(String string) {
		try { Integer.parseInt(string); }
		catch (Exception e) { return false; }
		return true;
	}
	
	public static PolynomialFactory<Real> ofReals() {
		return new PolynomialFactory<>(RealFactory.getInstance());
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		PolynomialFactory<Real> p = PolynomialFactory.ofReals();
		Polynomial<Real> p0,p1,p2;
//		System.out.println(p.ZERO);
		System.out.println(p.parseMonom("-9.9x"));
		System.out.println(p0 = p.parse("0"));
		System.out.println(p1 = p.parse("1+x"));
		System.out.println(p2 = p.parse("0+x-9.9x^2"));
		System.out.println(p2.compose(p1));
	}
}
