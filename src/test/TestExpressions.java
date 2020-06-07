package test;

import static functions.model.MathFactory.addition;
import static functions.model.MathFactory.constant;
import static functions.model.MathFactory.cos;
import static functions.model.MathFactory.product;
import static functions.model.MathFactory.sin;
import static functions.model.MathFactory.sqrt;
import static functions.model.MathFactory.tan;
import static functions.model.MathFactory.variable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import functions.interpreter.Parser;
import functions.model.Expression;
import functions.model.MultivariateExpression;

@SuppressWarnings("unused")
public class TestExpressions {
	public static Function<Expression, String> converter = e -> e.getToken();
	
	public static void testExpressions() {
		String x = "x";
		Expression f = addition(product(constant(2), variable(x)), 
				addition(sqrt(product(constant(3), variable(x))), constant(6)));
		Expression derivative = f.derivative(x);
		// testing f(x) = 2x + sqrt(3x) + 6
		System.out.println("f(x)    = "+f);
		// f'(x) = 2 + 3/(2*sqrt(3x))
		System.out.println("f'(x)   = "+derivative);
		// f(0) = 6
		System.out.println("f(0)    = "+f.of(0));
		// f'(1/3) = 3.5
		double value = 1/3d;
		System.out.println("f'(1/3) = "+derivative.of(value));
		Function<Double, Double> formula = i -> 2+3/(2*Math.sqrt(3*i));
		System.out.println(derivative.of(value) == formula.apply(value));
	}
	
	public static void testMultivariateExpressions() {
		String x = "x", y = "y", z = "z";
		// f(x,y,z) = (xy+sin(z)y+sqrt(x)yz, zy+cos(x)z+sqrt(z)xy, xz+xtan(y)+sqrt(y)xz)
		Expression[] fs = {
				addition(product(variable(x), variable(y)), 
						addition(product(sin(variable(z)), variable(y)), 
								product(sqrt(variable(x)), product(variable(y), variable(z))))),
				addition(product(variable(z), variable(y)), 
						addition(product(cos(variable(x)), variable(z)), 
								product(sqrt(variable(z)), product(variable(x), variable(y))))),
				addition(product(variable(x), variable(z)), 
						addition(product(variable(x), tan(variable(y))), 
								product(sqrt(variable(y)), product(variable(x), variable(z))))),
		};
		MultivariateExpression f = new MultivariateExpression(fs);
		List<Expression> gradient = f.gradient(z);	// gradient with-respect-to x
		gradient.forEach(e -> System.out.println(e));
		System.out.println(f.getInputs());
		System.out.println(f.getOutputs());
		System.out.println(Arrays.toString(f.getVariables().toArray()));
	}
	
	public static void testTree() {
		String x = "x";
		// testing f(x) = 2x + sqrt(3x) + 6
		Expression f = addition(product(constant(2), variable(x)), 
				addition(sqrt(product(constant(3), variable(x))), constant(6)));
		// f'(x) = 2 + 3/(2*sqrt(3x))
		Expression derivative = f.derivative(x);
		Function<Expression, String> converter = e -> e.getToken();
		f.asTree().show("Visualisation of f(x)", converter);
		derivative.asTree().show("Visualisation of f'(x)", converter);
	}
	
	public static void testMultipleVars() {
		String x = "x", y = "y";
		// testing f(x,y) = 2x + sqrt(3y) + 6
		Expression f = addition(product(constant(2), variable(x)), 
				addition(sqrt(product(constant(3), variable(y))), constant(6)));
		Expression dx = f.derivative(x), dy = f.derivative(y);
		f.asTree().show("Visualisation of f(x,y)", converter);
		dx.asTree().show("Visualisation of f'(x,y)/dx", converter);
		dy.asTree().show("Visualisation of f'(x,y)/dy", converter);
		System.out.println(Arrays.toString(f.getVariables()));
		System.out.println("f(0, 1/3)    = "+f.of(0, 1/3d));
	}
	
	public static void testParser() {
		// testing f(x,y) = 2x + sqrt(3y) + 6
		String input = "x+y*cos(exp(ln(z))), sin(y)*exp(x)+ln(z)";
		MultivariateExpression function = MultivariateExpression.parse(input);
		String vars = function.getVariablesAsString();
		function.forEachIndexed((index, expression) -> expression.asTree().show("f"+index+vars, 500, 40, converter));
		function.forEachIndexed((i, e) -> 
			e.derivative("x").asTree().show("f'"+vars, 700, 40, converter));
		
	}
	
	public static void main(String[] args) {
//		testExpressions();
//		testMultivariateExpressions();
//		testTree();
//		testMultipleVars();
		testParser();
	}
}
