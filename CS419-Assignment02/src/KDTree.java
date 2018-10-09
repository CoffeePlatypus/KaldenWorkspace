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
			double n1=left.get(0).getIthCoordinate(d);
			double n2=right.get(0).getIthCoordinate(d);
			
			if(n1<n2) {
				data.push(left.pop());
			}else{
				data.push(right.pop());
			}
		}
		while(!left.isEmpty()) {
			data.push(left.pop());
		}
		while(!right.isEmpty()) {
			data.push(right.pop());
		}
		
		return data;
	}
	
	public Node createTree(LinkedList<Point> data, int dim) {
		if(data.size() <= setSize) {
			return new Node(data);
		}
		data = mergeSort(data,dim);
		int half = data.size()/2;
		double median = data.get(half).getIthCoordinate(dim);
		if(data.size() % 2 == 0) {
			half++;
			median = (median + data.get(half).getIthCoordinate(dim)) / 2;
		}
		Node nodey = new Node(median, dim);
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
	
	public double findMedian(HashSet<Point> data, int dim) {
		//TODO find median
		return 0;
	}
	
	private class Node{
		double median;
		int dim; // -1 for leaf
		Point [] boundingBox;
		LinkedList<Point> data;
		Node left;
		Node right;
		
		private Node(double m, int d) {
			median = m;
			dim = d;
			boundingBox = new Point [dimension];
		}
		
		public Node(LinkedList<Point> d) {
			median = 0;
			dim = -1;
			data = d;
			left = null;
			right = null;
			// TODO find bounding box
		}
		
		private void setBoundingBox(Point [] bb) {
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
	}
}
