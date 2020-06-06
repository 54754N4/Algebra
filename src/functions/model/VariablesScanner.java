package functions.model;

import java.util.Collection;

import functions.tree.ExpressionVisitor;

public class VariablesScanner implements ExpressionVisitor<Collection<String>> {
	
	public static void scan(Expression e, Collection<String> storage) {
		new VariablesScanner().visit(e, storage); 
	}
	
	@Override
	public void visit(Variable v, Collection<String> variables) {
		variables.add(v.getToken());
	}
}