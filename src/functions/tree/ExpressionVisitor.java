package functions.tree;

import functions.model.BinaryOperator;
import functions.model.Constant;
import functions.model.Expression;
import functions.model.UnaryOperator;
import functions.model.Variable;

public interface ExpressionVisitor<C> {	
	default void visit(BinaryOperator op, C context) {
		visit(op.left, context);
		visit(op.right, context);
	}
	
	default void visit(UnaryOperator op, C context) {
		visit(op.expression, context);
	}
	
	default void visit(Variable v, C context) {}
	default void visit(Constant v, C context) {}
	
	default void visit(Expression e, C context) {
		if (e instanceof BinaryOperator) visit((BinaryOperator) e, context);
		else if (e instanceof UnaryOperator) visit((UnaryOperator) e, context);
		else if (e instanceof Variable) visit((Variable) e, context);
		else if (e instanceof Constant) visit((Constant) e, context);
		else throw new IllegalArgumentException("Visitor not implemented for"+e.getClass());
	}
}
