package functions.tree;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;

public class TreePanel<K> extends JPanel {
	private static final long serialVersionUID = 2315957851667986843L;
	private static final Font font = new Font("Verdana", Font.BOLD, 30);
	public final Tree<K> root;
	private final Dimension size;
	private final int dy;
	private Function<K, String> converter;
	
	public TreePanel(int totalWidth, int dy, Tree<K> tree, Function<K, String> converter) {
		this.dy = dy;
		this.converter = converter;
		size = new Dimension(totalWidth, ((root = tree).height()+1)*dy);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		draw(g, 0, 0, root, size.width);
	}
	
	private void draw(Graphics g, int x, int y, Tree<K> node, int width) {
		Rectangle rect = new Rectangle(x, y, width, dy);
		drawCenteredString(g, converter.apply(node.getData()), rect);
		if (node.isLeaf())
			return;
		int parentX = x + width/2, parentY = y+dy/2;
		List<Tree<K>> children = node.getChildren();
		int count = children.size(),
			childWidth = width/count,
			childX;
		for (int i=0; i<count; i++) {
			childX = x+i*childWidth;
			draw(g, childX, y+dy, children.get(i), childWidth);
			g.drawLine(parentX, parentY, childX+childWidth/2, y+dy+dy/2);
		}
	}
	
	// https://stackoverflow.com/a/27740330/3225638
	public void drawCenteredString(Graphics g, String text, Rectangle rect) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
}
