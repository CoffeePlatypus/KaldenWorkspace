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
	}
	
	private Node createTree(LinkedList<Point> data, int dim) {
		if(data.size() <= setSize) {
			return new Node(data, findBoundingBox(data));
		}
		System.out.println("Pre Sort "+dim+"---");
		System.out.println(data);
		data = mergeSort(data,dim);
		System.out.println("After ---\n"+ data);
		int half = data.size()/2;
		double median = data.get(half).getIthCoordinate(dim);
		if(data.size() % 2 == 0) {
			half++;
			median = (median + data.get(half).getIthCoordinate(dim)) / 2;
		}
		Node nodey = new Node(median, dim, findBoundingBox(data));
		System.out.println(nodey);
		LinkedList<Point> dataPlus = new LinkedList<Point>();
		LinkedList<Point> dataMinus = new LinkedList<Point>();
		for(int i = 0; i<data.size(); i++) {
			if(i < half) {
				dataMinus.push(data.get(i));
			}else {
				dataPlus.push(data.get(i));
			}
		}
		nodey.setLeft(createTree(dataMinus,dim));
		nodey.setRight(createTree(dataPlus,dim));
		return nodey;
	}
	
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
	
	public LinkedList<Point> mergeSort(LinkedList<Point> data, int d){
		if(data.size() <= 1) return data;
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
		return merge(left, right, data, d);
	}
	
	public LinkedList<Point> merge(LinkedList<Point> left, LinkedList<Point> right, LinkedList<Point> data, int d) {
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
		return data;
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
			return dim < 0;
		}
		
		public String toString() {
			String s = "Bounding Box: ["+boundingBox[0]+", "+boundingBox[1]+"]\n";
			if(isLeaf()) {
				s+= "Leaf: "+data+"\n";
			}else {
				s+= "Median: "+median+"\n";
			}
			return s;
		}
	}
}
