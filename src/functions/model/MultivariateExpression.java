package functions.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import functions.interpreter.Parser;

public class MultivariateExpression {
	private List<Expression> expressions;
	private Set<String> variables;
	
	public MultivariateExpression(Expression...expressions) {
		this(Arrays.asList(expressions));
	}
	
	public MultivariateExpression(Collection<Expression> expressions) {
		this.expressions = new ArrayList<>(expressions);
		variables = new TreeSet<>();
		for (Expression expression : expressions)
			variables.addAll(expression.scanForVariables());
	} 
	
	public List<Expression> gradient(String wrt) {
		return expressions.stream()
				.map(e -> e.derivative(wrt))
				.collect(Collectors.toList());
	}
	
	public List<Double> of(double...x) {
		if (x.length != variables.size())
			throw new IllegalArgumentException(
					"I need " + variables.size()
					+ " inputs for each " + Arrays.toString(variables.toArray())
					+ " variable respectively."); 
		return expressions.stream()
				.map(e -> e.forward(variables, x))
				.collect(Collectors.toList());
	}
	
	public Set<String> getVariables() {
		return variables;
	}
	
	public String getVariablesAsString() {
		return Arrays.toString(variables.toArray());
	}
	
	public int getInputs() {
		return variables.size();
	}
	
	public int getOutputs() {
		return expressions.size();
	}
	
	public Expression asExpression() {
		if (getOutputs() != 1) // if is not single dimensional
			throw new MultidimensionalExpressionException();
		return expressions.get(0);
	}
	
	public MultivariateExpression forEach(Consumer<Expression> consumer) {
		expressions.forEach(consumer);
		return this;
	}
	
	public MultivariateExpression forEachIndexed(IndexConsumer<Expression> consumer) {
		for (int i=0; i<expressions.size(); i++)
			consumer.accept(i, expressions.get(i));
		return this;
	}
	
	public static MultivariateExpression parse(String string) {
		return new Parser(string)
			.parse();
	}
	
	@FunctionalInterface
	public static interface IndexConsumer<K> {
		void accept(int index, K data);
	}
	
	public static class MultidimensionalExpressionException extends RuntimeException {
		private static final long serialVersionUID = -2515717154617796529L;

		public MultidimensionalExpressionException() {
			super("Expression is multi-dimensional");
		}
	}
}
