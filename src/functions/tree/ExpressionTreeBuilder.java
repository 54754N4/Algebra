package functions.tree;

import functions.model.BinaryOperator;
import functions.model.Expression;
import functions.model.UnaryOperator;

public class ExpressionTreeBuilder implements ExpressionVisitor<Tree<Expression>>{
	private static final ExpressionTreeBuilder BUILDER = new ExpressionTreeBuilder();
	
	public static ExpressionTreeBuilder getTreeBuilder() {
		return BUILDER;
	}
	
	public Tree<Expression> build(Expression e) {
		Tree<Expression> root = new Tree<>(e);	// create only root node first
		visit(e, root);	// delegate responsibility of node creation to visitor methods
		return root;
	}

	@Override
	public void visit(BinaryOperator op, Tree<Expression> parent) {
		Tree<Expression> left = new Tree<>(op.left), 
				right = new Tree<>(op.right);
		parent.addChild(left).addChild(right);
		visit(op.left, left);
		visit(op.right, right);
	}

	@Override
	public void visit(UnaryOperator op, Tree<Expression> parent) {
		Tree<Expression> node = new Tree<>(op.expression);
		parent.addChild(node);
		visit(op.expression, node);
	}
}
