
public class Point {
	double [] coordinates;
	
	public Point(int d) {
		coordinates = new double[d];
	}
	
	public Point(double[] c) {
		coordinates = c;
	}
	
	public double getIthCoordinate(int i) {
		return coordinates[i];
	}
	
	public double distance(Point p) {
		double dis = 0;
		for(int i = 0; i < coordinates.length; i++) {
			dis += Math.pow(coordinates[i] - p.coordinates[i], 2);
		}
		return Math.sqrt(dis);
	}
	
	public String toString() {
		String s = "(";
		for(int i = 0; i < coordinates.length; i++) {
			s+= (i +1) < coordinates.length ? coordinates[i] +" ": coordinates[i];
			
		}
		return s+")";
	}		
}
