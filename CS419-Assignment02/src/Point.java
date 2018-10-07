
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
	
	
	public String toString() {
		String s = "(";
		for(int i = 0; i < dimension; i++) {
			s+= (i +1) < dimension ? coordinates[i] +" ": coordinates[i];
			
		}
		return s+")";
	}
			
}
