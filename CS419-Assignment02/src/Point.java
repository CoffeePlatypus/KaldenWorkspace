
public class Point {
	int dimension;
	double [] coordinates;
	
	public Point(int d) {
		dimension = d;
		coordinates = new double[d];
	}
	
	public Point(int d, double[] c) {
		dimension = d;
		coordinates = c;
	}
	
	public double getIthCoordinate(int i) {
		return coordinates[i];
	}
	
	public double distance(Point p) {
		double dis = 0;
		for(int i = 0; i < dimension; i++) {
			dis += Math.pow(coordinates[i] + p.coordinates[i], 2);
		}
		return Math.sqrt(dis);
	}
	
	public String toString() {
		String s = "(";
		for(int i = 0; i < dimension; i++) {
			s+= (i +1) < dimension ? coordinates[i] +" ": coordinates[i];
			
		}
		return s+")";
	}
			
}
