import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class KDTree {
	private int dimension;
	private int setSize;
	private Node root;
	
	public KDTree(int d, int s) {
		dimension = d;
		setSize = s;
		root = null;
	}
	
	public void createTree(LinkedList<Point> data) {
		root = createTree(data, 0);
		System.out.println(recursivePrint(root,""));
	}
	
	/* 
	 * 
	 */
	private Node createTree(LinkedList<Point> data, int dim) {
		if(data.size() <= setSize) {
			return new Node(data, findBoundingBox(data));
		}
		mergeSort(data,dim);
		int half = data.size()/2;
		double median = data.get(half).getIthCoordinate(dim);
		System.out.println(data);
		System.out.println("med-"+median);
		if(data.size() % 2 == 0) {
			median = (median + data.get(half + 1).getIthCoordinate(dim)) / 2;
		}
		Node nodey = new Node(median, dim, findBoundingBox(data));
		System.out.println(nodey);
		LinkedList<Point> dataPlus = new LinkedList<Point>();
		LinkedList<Point> dataMinus = new LinkedList<Point>();
		System.out.println(data);
		while(!data.isEmpty() && data.peek().coordinates[dim] < median) {
			dataMinus.push(data.pop());
		}
		while(!data.isEmpty()) {
			dataPlus.push(data.pop());
		}
		nodey.setLeft(createTree(dataMinus,dim));
		nodey.setRight(createTree(dataPlus,dim));
		return nodey;
	}
	
	/* Finds the boundingBox for a LinkedList of points
	 * @param data - LinkedList of points
	 * @returns Point[] - array of size two which describes a some dimensional shape which contains all the points in data
	 */
	private Point [] findBoundingBox(LinkedList<Point> data){
		double [] maxes = new double[dimension];
		double [] mins = new double[dimension];
		
		Iterator<Point> datarator = data.iterator();
		// TODO error check empty data
		Point temp = datarator.next();
		for(int i = 0; i<dimension; i++) {
			maxes[i] = temp.getIthCoordinate(i);
			mins[i] = temp.getIthCoordinate(i);
		}
		while(datarator.hasNext()) {
			temp = datarator.next();
			for(int i = 0; i<dimension; i++) {
				if(maxes[i] < temp.getIthCoordinate(i)) {
					maxes[i] = temp.getIthCoordinate(i);
				}else if (mins[i] > temp.getIthCoordinate(i)) {
					mins[i] = temp.getIthCoordinate(i);
				}
			}
		}
		return new Point[]{new Point(mins), new Point(maxes)};
	}
	
	/* Sorts a LinkedList of points based on dimension d of the points
	 * @param data - the list to sort
	 * @param d - the dimension to sort on
	 */
	public void mergeSort(LinkedList<Point> data, int d){
		if(data.size() <= 1) return;
		LinkedList<Point> left = new LinkedList<Point>();
		LinkedList<Point> right = new LinkedList<Point>();
		int half=data.size()/2;
		
		for(int i=0;i<half;i++) {
			left.push(data.pop());
		}
		
		while(!data.isEmpty()) {
			right.push(data.pop());
		}
		mergeSort(left, d);
		mergeSort(right, d);
		merge(left, right, data, d);
	}
	
	
	/* Merges a two sorted LinkedLists of points into one sorted LinkedList - the lists are sorted by dimension d of the points
	 * @param left - sorted LinkedList of points
	 * @param right - sorted LinkedList of points
	 * @param data - the LinkedList that left and right are merged into
	 */
	public void merge(LinkedList<Point> left, LinkedList<Point> right, LinkedList<Point> data, int d) {
		while(!left.isEmpty() && !right.isEmpty()) {
			double n1=left.getFirst().getIthCoordinate(d);
			double n2=right.getFirst().getIthCoordinate(d);
			
			if(n1<n2) {
				data.addLast(left.pop());
			}else {
				data.addLast(right.pop());
			}
		}
		while(!left.isEmpty()) {
			data.addLast(left.pop());
		}
		while(!right.isEmpty()) {
			data.addLast(right.pop());
		}
	}
	
	public String toString() {
		return recursivePrint(root,"");		
	}
	
	private String recursivePrint(Node n, String path) {
		if(n.isLeaf()) {
			return path+": " +n+"\n";
		}
		return recursivePrint(n.left, path+"L") + recursivePrint(n.right, path+"R");
	}
	
	private class Node{
		double median;
		int dim; // -1 for leaf
		Point [] boundingBox;
		LinkedList<Point> data;
		Node left;
		Node right;
		
		private Node(double m, int d, Point[] bb) {
			median = m;
			dim = d;
			boundingBox = bb;
		}
		
		public Node(LinkedList<Point> d, Point[] bb) {
			median = 0;
			dim = -1;
			data = d;
			left = null;
			right = null;
			boundingBox = bb;
		}
		
		private void addData(Point p) {
			data.add(p);
		}
		
		private void setLeft(Node l) {
			left = l;
		}
		
		private void setRight(Node r) {
			right = r;
		}
		
		public boolean isLeaf() {
			return dim == -1;
		}
		
		public String toString() {
			String s = "Bounding Box: ["+boundingBox[0]+", "+boundingBox[1]+"]\n";
			if(isLeaf()) {
				s+= "Leaf: "+data+"\n";
			}else {
				s+= "Dim: "+dim+"\n";
				s+= "Median: "+median+"\n";
			}
			return s;
		}
	}
}
