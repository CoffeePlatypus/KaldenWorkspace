import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class KDTree {
	private int dimension;
	private int setSize;
	private Node root;
	private int printIndex;
	
	public KDTree(int d, int s) {
		dimension = d;
		setSize = s;
		root = null;
		printIndex = 0;
	}
	
	public void createTree(LinkedList<Point> data) {
		root = createTree(data, 0);
		
	}
	
	public void testPoint(Point p) {
//		System.out.println(p);
		Node n = findNeighNode(root, p);
		LinkedList<Point> data = n.getData();
		if(data.size() == 0 ) {
			System.out.println(p+" has no nearest neighbor in empty set");
			return;
		}
		System.out.println(p +" in set "+data);
		double minDistance = p.distance(data.get(0));
		int mindex = 0;
		for(int i = 0; i<data.size(); i++) {
			double temp = p.distance(data.get(i));
			if(temp < minDistance) {
				minDistance = temp;
				mindex = i;
			}
		}
		System.out.println("Nearest neighbour: "+data.get(mindex)+ " - Distance: "+minDistance+"\n");
	}
	
	/* Find the node which contains the point's nearest neighbor
	 * TODO consider empty left or right
	 */
	public Node findNeighNode(Node n, Point p) {
//		System.out.println(n.boundingBox[0]+" "+n.boundingBox[1]);
//		System.out.println("Med: "+n.getMedian() );
//		System.out.println("dim "+ n.getDim());
//		if(!n.isLeaf())System.out.println(p.getIthCoordinate(n.getDim())+" < "+ n.getMedian());
		if(n.isLeaf()) {
			return n;
		}else if(p.getIthCoordinate(n.getDim()) < n.getMedian()) {
			return findNeighNode(n.getLeft(), p);
		}else {
			return findNeighNode(n.getRight(), p);
		}
	}
	
	/* Creates tree from magic algorytims 
	 * 
	 */
	private Node createTree(LinkedList<Point> data, int dim) {
		if(data.size() <= setSize) {
			return new Node(data, findBoundingBox(data));
		}
		mergeSort(data,dim);
		int half = data.size()/2;
		double median = data.get(half).getIthCoordinate(dim);
//		System.out.println(data);
//		System.out.println("med-"+median);
		Node nodey = new Node(median, dim, findBoundingBox(data));
//		System.out.println(nodey);
		LinkedList<Point> dataPlus = new LinkedList<Point>();
		LinkedList<Point> dataMinus = new LinkedList<Point>();
//		System.out.println("Sorted on dim: "+ dim);
//		System.out.println(data);
		while(!data.isEmpty() && data.peek().coordinates[dim] < median) {
			dataMinus.push(data.pop());
		}
		while(!data.isEmpty()) {
			dataPlus.push(data.pop());
		}
		nodey.setLeft(createTree(dataMinus,(dim + 1) % dimension));
		nodey.setRight(createTree(dataPlus,(dim + 1) % dimension));
		return nodey;
	}
	
	/* Finds the boundingBox for a LinkedList of points
	 * @param data - LinkedList of points
	 * @returns Point[] - array of size two which describes a some dimensional shape which contains all the points in data
	 */
	private Point [] findBoundingBox(LinkedList<Point> data){
		double [] maxes = new double[dimension];
		double [] mins = new double[dimension];
		if (data.isEmpty()) return new Point[]{new Point(mins), new Point(maxes)};
		
		Iterator<Point> datarator = data.iterator();
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
		printIndex = 0;
		return recursivePrint(root, root.isLeaf()?"ROOT":"");		
	}
	
	/* Recursively generates string representation of leaves
	 * 
	 */
	private String recursivePrint(Node n, String path) {
		if(n.isLeaf()) {
			return (printIndex++) +" : "+path+" : " +n;
		}
		
		return recursivePrint(n.left, path+"L") + recursivePrint(n.right, path+"R");
	}
	
	private class Node{
		private double median;
		private int dim; // -1 for leaf
		private Point [] boundingBox;
		private LinkedList<Point> data;
		private Node left;
		private Node right;
		
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
		
		private void setLeft(Node l) {
			left = l;
		}
		
		private void setRight(Node r) {
			right = r;
		}
		
		public LinkedList<Point> getData() {
			return data;
		}
		
		public boolean isLeaf() {
			return dim == -1;
		}
		
		public double getMedian() {
			return median;
		}
		
		public int getDim() {
			return dim;
		}
		
		public Node getLeft() {
			return left;
		}
		
		public Node getRight() {
			return right;
		}
		
		public String toString() {
			if (data.isEmpty()) return "\n";
			String s = "Bounding Box: ["+boundingBox[0]+", "+boundingBox[1]+"]\n";
//			s += "Py: "+ boundingBox[0].getIthCoordinate(0)+" "+boundingBox[0].getIthCoordinate(1) + " "+ (boundingBox[1].getIthCoordinate(0) - boundingBox[0].getIthCoordinate(0)) +" " + (boundingBox[1].getIthCoordinate(1) - boundingBox[0].getIthCoordinate(1))+"\n";
			
			if(isLeaf()) {
				s+= "\t Leaf Data: "+data+"\n";
			}else {
				s+= "Dim: "+dim+"\n";
				s+= "Median: "+median+"\n";
			}
			return s;
		}
	}
}
