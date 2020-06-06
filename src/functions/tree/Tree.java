package functions.tree;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JFrame;


/* Doubly-linked tree (can go upwards and downwards)
 */
public class Tree<K> {
	private Tree<K> parent;
	private List<Tree<K>> children;
	private K data;
	
	public Tree(K data) {	
		this.data = data;
		children = new ArrayList<>();
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public boolean isLeaf() {
		return children.size() == 0;
	}
	
	public boolean isDisconnected() {
		return isRoot() && isLeaf();
	}
	
	public int size() {
		if (isLeaf()) 
			return 1;
		int sum = 0;
		for (Tree<K> child : children) 
			sum += child.size();
		return sum+1;
	}
	
	public int height() {
		if (isLeaf())
			return 0;
		int max = 0, size;
		for (Tree<K> child : children)
			if ((size = child.height()) > max)
				max = size;
		return max + 1;
	}
	
	public Tree<K> setParent(Tree<K> parent) {
		this.parent = parent;
		return this;
	}
	
	public Tree<K> getParent() {
		return parent;
	}
	
	public Tree<K> addChild(Tree<K> child) {
		child.setParent(this);
		children.add(child);
		return this;
	}
	
	public List<Tree<K>> getChildren() {
		return children;
	}
	
	public Tree<K> setData(K data) {
		this.data = data;
		return this;
	}
	
	public K getData() {
		return data;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tree))
			return false;
		Tree<?> tree = (Tree<? >) o;
		if (tree.children.size() != children.size())
			return false;
		return tree.data.equals(data);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
	
	public TreePanel<K> show() {
		return show("Tree Visualiser");
	}
	
	public TreePanel<K> show(String title) {
		return show(title, k -> k.toString());
	}
	
	public TreePanel<K> show(String title, Function<K, String> converter) {
		return show(title, Toolkit.getDefaultToolkit().getScreenSize().width/3, 50, converter); 
	}
	
	public TreePanel<K> show(String title, int totalWidth, int dy, Function<K, String> converter) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		TreePanel<K> panel = new TreePanel<>(totalWidth, dy, this, converter);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		return panel;
	}
	
	public static void main(String[] args) {
		int total = 10;
		List<Tree<Integer>> nodes = new ArrayList<>();
		for (int i=0; i<total; i++) nodes.add(new Tree<>(i));
		nodes.get(0).addChild(nodes.get(1));
		nodes.get(1).addChild(nodes.get(2));
		nodes.get(1).addChild(nodes.get(3));
		System.out.println(nodes.get(1).height());
		System.out.println(nodes.get(0).isRoot());
		System.out.println(nodes.get(5).isDisconnected());
		System.out.println(nodes.get(3).isDisconnected());
		
	}
}
