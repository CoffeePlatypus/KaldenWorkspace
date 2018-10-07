import java.util.ArrayList;
import java.util.HashSet;

public class KDTree {
	int dimension;
	Node root;
	
	public KDTree(int d) {
		dimension = d;
		root = null;
	}
	
	public Node createTree(HashSet<Point> data) {
		
		return null;
	}
	
	
	
	private class Node{
		int median; // -1 for leaf?
		Point [] boundingBox;
		HashSet<Point> data;
		Node left;
		Node right;
		
		private Node(int m) {
			median = m;
			boundingBox = new Point [dimension];
			if(median < 0) {
				data = new HashSet<Point>();
				left = null;
				right = null;
			}else {
				data = null;
			}
		}
		
		private void setLeft(Node l) {
			left = l;
		}
		
		private void setRight(Node r) {
			right = r;
		}
	}
}
